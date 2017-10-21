package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import static model.GameFigure.STATE_ALIVE;
import static model.GameFigure.STATE_DYING;

public class Missile extends GameFigure {

    // missile size
    private static final int SIZE = 30;
    private static final int MAX_EXPLOSION_SIZE = 3;
    private static final int WINDOW_WIDTH = controller.Main.WIN_WIDTH;
    private static final int WINDOW_HEIGHT = controller.Main.WIN_HEIGHT;
    private float dx; // displacement at each frame
    private float dy; // displacement at each frame
    private int animationCheck = 0;

    // public properties for quick access
    public Point2D.Float target;

    private static final int UNIT_TRAVEL_DISTANCE = 7; // per frame move

    private int explosionCounter = 0;
    public int distanceTraveled;
    private Image launcherImage;
    private Image launcherImage2;
    private Image explosion1;
    private Image explosion2;
    private Image explosion3;

    /**
     *
     * @param sx start x of the missile
     * @param sy start y of the missile
     * @param tx target x of the missile
     * @param ty target y of the missile
     */
    public Missile(float sx, float sy, float tx, float ty) {
        super(sx, sy);
        super.state = STATE_ALIVE;
        this.target = new Point2D.Float(tx, ty);
        //this.color = color;

        double angle = Math.atan2(Math.abs(ty - sy), Math.abs(tx - sx));
        dx = (float) (UNIT_TRAVEL_DISTANCE * Math.cos(angle));
        dy = (float) (UNIT_TRAVEL_DISTANCE * Math.sin(angle));

        if (tx > sx && ty < sy) { // target is upper-right side
            dy = -dy; // dx > 0, dx < 0
        } else if (tx < sx && ty < sy) { // target is upper-left side
            dx = -dx;
            dy = -dy;
        } else if (tx < sx && ty > sy) { // target is lower-left side
            dx = -dx;
        } else { // target is lower-right side
            // dx > 0 , dy > 0
        }

        launcherImage = null;

        try {
            launcherImage = ImageIO.read(getClass().getResource("/resources/fireball.gif"));
            launcherImage2 = ImageIO.read(getClass().getResource("/resources/fireball2.png"));
            explosion1 = ImageIO.read(getClass().getResource("/resources/explosion0.png"));
            explosion2 = ImageIO.read(getClass().getResource("/resources/explosion1.png"));
            explosion3 = ImageIO.read(getClass().getResource("/resources/explosion2.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
            System.exit(-1);
        }

    }

    @Override
    public void render(Graphics2D g) {
        if (state == STATE_ALIVE) {
            if (animationCheck == 0) {
                g.drawImage(launcherImage, (int) super.x, (int) super.y,
                        SIZE, SIZE, null);
                animationCheck = 1;
            } else {
                g.drawImage(launcherImage2, (int) super.x, (int) super.y,
                        SIZE, SIZE, null);
                animationCheck = 0;
            }
        }
        if (state == STATE_DYING) {
            if (explosionCounter == 0) {
                g.drawImage(explosion1, (int) super.x, (int) super.y,
                        SIZE, SIZE, null);
            }
            if (explosionCounter == 1) {
                g.drawImage(explosion2, (int) super.x, (int) super.y,
                        SIZE, SIZE, null);
            }
            if (explosionCounter == 2) {
                g.drawImage(explosion3, (int) super.x, (int) super.y,
                        SIZE, SIZE, null);
            }
        }
    }

    @Override
    public void update() {
        updateState();
        if (this.x > WINDOW_WIDTH || this.x < 0 || this.y > WINDOW_HEIGHT || this.y < 0) {
            this.goNextState();
        }
        if (state == STATE_ALIVE) {
            updateLocation();
            distanceTraveled++;
        } else if (state == STATE_DYING) {
            updateSize();
        }
    }

    public void updateLocation() {
        //when button clicked this happens
        super.x += dx;
        super.y += dy;
    }

    public void updateSize() {
        explosionCounter++;
    }

    public void updateState() {
        if (state == STATE_ALIVE) {
            double distance = target.distance(super.x, super.y);
            boolean targetReached = distance <= 4.0;
            if (targetReached) {
                this.goNextState();
            }
        } else if (state == STATE_DYING) {
            if (explosionCounter >= MAX_EXPLOSION_SIZE) {
                this.goNextState();
            }
        }
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(this.x, this.y, SIZE * 0.9D, SIZE * 0.9D);
    }

    @Override
    public void shoot() {
        System.out.println("Missile Shoots");
    }
}
