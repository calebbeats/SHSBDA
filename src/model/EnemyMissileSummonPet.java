package model;

import controller.Main;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import static model.GameFigure.STATE_ALIVE;
import static model.GameFigure.STATE_DYING;

public class EnemyMissileSummonPet extends GameFigure {

    //Properties
    //------------------------------

        //Private
        private static final int SIZE = 15; //Image Size
        private static final int MAX_SWING_SIZE = 4; //Max Swing Count
        private static final int UNIT_TRAVEL_DISTANCE = 15; //Frame Movement
        private float dx; // displacement at each frame
        private float dy; // displacement at each frame

        //Public
        public Point2D.Float target;
        public static int DAMAGE = 20;

    //Images
    //------------------------------
    private int swingCounter = 0;
    private Image attackU;
    private Image attackD;
    private Image attackR;
    private Image attackL;

    /**
     *
     * @param sx start x of the missile
     * @param sy start y of the missile
     * @param tx target x of the missile
     * @param ty target y of the missile
     * @param color color of the missile
     */
    public EnemyMissileSummonPet(float sx, float sy) {
        
        super(sx, sy);
        
        //Damage
        //Go-To method @ Line 147
        DAMAGE = 20;
        
        float tx = GameData.shooter.x + 10;
        float ty = Main.gameData.shooter.y + 10;
        this.target = new Point2D.Float(tx, ty);
        
        double angle = Math.atan2(Math.abs(ty - sy), Math.abs(tx - sx));
        dx = (float) (UNIT_TRAVEL_DISTANCE * Math.cos(angle));
        dy = (float) (UNIT_TRAVEL_DISTANCE * Math.sin(angle));
        
        if (tx > sx && ty < sy) {       // target is upper-right side
            dy = -dy;                   // dx > 0, dx < 0
        } 
        else if (tx < sx && ty < sy) {  // target is upper-left side
            dx = -dx;
            dy = -dy;
        }
        else if (tx < sx && ty > sy) {  // target is lower-left side
            dx = -dx;
        }
        else {                          // target is lower-right side
                                        // dx > 0 , dy > 0
        }

        try {
            attackU = ImageIO.read(getClass().getResource("/resources/petAttack.png"));
            attackD = ImageIO.read(getClass().getResource("/resources/petAttack.png"));
            attackR = ImageIO.read(getClass().getResource("/resources/petAttack.png"));
            attackL = ImageIO.read(getClass().getResource("/resources/petAttack.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open image.");
            System.exit(-1);
        }

    }

    @Override
    public void render(Graphics2D g) {
        //Initially, sword is in the upright position for first animation
        if (state == STATE_ALIVE) {
            g.drawImage(attackU, (int) super.x, (int) super.y,
                    30, 30, null);
        }
        //draw animation based on direction of mouse click
        if (state == STATE_DYING) {

            if (swingCounter < 1) {
                g.drawImage(attackU, (int) super.x, (int) super.y,
                        30, 30, null);
            }
            if (swingCounter < 2) {
                g.drawImage(attackL, (int) super.x, (int) super.y,
                        30, 30, null);
            }
            if (swingCounter < 3) {
                g.drawImage(attackD, (int) super.x, (int) super.y,
                        30, 30, null);
            }
            if (swingCounter < 4) {
                g.drawImage(attackR, (int) super.x, (int) super.y,
                        30, 30, null);
            }

        }
    }

    @Override
    public void update() {
        if (state == STATE_ALIVE) {
            updateLocation();
        } else if (state == STATE_DYING) {
            updateSize();
        }
        updateState();
    }

    public void updateLocation() {
        super.x += dx;
        super.y += dy;
    }

    public void updateSize() {
        swingCounter++;
    }

    public void updateState() {
        if (state == STATE_ALIVE) {
            this.goNextState();
        } else if (state == STATE_DYING) {
            if (swingCounter >= MAX_SWING_SIZE) {
                this.goNextState();
            }
        }
    }
    
    public static void dealDamage(){
        GameData.shooter.takeDamage(DAMAGE);
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(this.x, this.y, SIZE * 0.9D, SIZE * 0.9D);
    }

    @Override
    public void shoot() {
        System.out.println(" Melee Attack!!");
    }
}