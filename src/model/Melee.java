package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import static model.GameFigure.STATE_ALIVE;
import static model.GameFigure.STATE_DYING;

public class Melee extends GameFigure {

    // missile size
    private static final int SIZE = 15;
    private static final int MAX_SWING_SIZE = 4;
    private float dx; // displacement at each frame
    private float dy; // displacement at each frame
    //private int animationCheck = 0;
    private int swordSwing = 0; //1 for right 0 for left

    // public properties for quick access
    public Color color;
    public Point2D.Float target;

    private static final int UNIT_TRAVEL_DISTANCE = 3; // per frame move

    private int swingCounter = 0;
    public int distanceTraveled;
    private Image swordUp;
    private Image swordDown;
    private Image swordRight;
    private Image swordLeft;

    /**
     *
     * @param sx start x of the missile
     * @param sy start y of the missile
     * @param tx target x of the missile
     * @param ty target y of the missile
     * @param color color of the missile
     */
    public Melee(float sx, float sy, float tx, float ty) {
        super(sx, sy);

        this.target = new Point2D.Float(tx, ty);
        this.color = color;

        double angle = Math.atan2(Math.abs(ty - sy), Math.abs(tx - sx));
        dx = (float) (UNIT_TRAVEL_DISTANCE * Math.cos(angle));
        dy = (float) (UNIT_TRAVEL_DISTANCE * Math.sin(angle));

        if (tx > sx && ty < sy) { // target is upper-right side
            dy = -dy; // dx > 0, dx < 0
            swordSwing = 1;
        } else if (tx < sx && ty < sy) { // target is upper-left side
            dx = -dx;
            dy = -dy;
            swordSwing = 0;
        } else if (tx < sx && ty > sy) { // target is lower-left side
            dx = -dx;
            swordSwing = 0;
        } else { // target is lower-right side
            // dx > 0 , dy > 0
            swordSwing = 1;
        }

        //launcherImage = null;
        try {
            swordUp = ImageIO.read(getClass().getResource("swordUp.png"));
            swordDown = ImageIO.read(getClass().getResource("swordDown.png"));
            swordRight = ImageIO.read(getClass().getResource("swordRight.png"));
            swordLeft = ImageIO.read(getClass().getResource("swordLeft.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open image.");
            System.exit(-1);
        }

    }

    @Override
    public void render(Graphics2D g) {
        //hold sword up waiting to attack
        if (state == STATE_ALIVE) {
            //if (animationCheck == 0) {
            //g.drawImage(launcherImage, (int) super.x, (int) super.y,
            //       30, 30, null);
            g.drawImage(swordUp, (int) super.x, (int) super.y,
                    30, 30, null);
            //   animationCheck = 1;
            //} else {
            //g.drawImage(launcherImage2, (int) super.x, (int) super.y,
            // 30, 30, null);
            //  g.drawImage(swordUp, (int) super.x, (int) super.y,
            //    30, 30, null);
            //   animationCheck = 0;
            // }
        }
        //draw animation based on left attack or right attack.
        // WORK IN PROGRESS
        if (state == STATE_DYING) {

            if (swordSwing == 0) { //left sword swing
                if (swingCounter == 0) {
                    g.drawImage(swordLeft, (int) super.x, (int) super.y,
                            30, 30, null);
                }
                if (swingCounter == 1) {
                    g.drawImage(swordDown, (int) super.x, (int) super.y,
                            30, 30, null);
                }
                if (swingCounter == 2) {
                    g.drawImage(swordLeft, (int) super.x, (int) super.y,
                            30, 30, null);
                }
                if (swingCounter == 3) {
                    g.drawImage(swordUp, (int) super.x, (int) super.y,
                            30, 30, null);
                }
            }

            if (swordSwing == 1) { //right sword swing
                if (swingCounter == 0) {
                    g.drawImage(swordRight, (int) super.x, (int) super.y,
                            30, 30, null);
                }
                if (swingCounter == 1) {
                    g.drawImage(swordDown, (int) super.x, (int) super.y,
                            30, 30, null);
                }
                if (swingCounter == 2) {
                    g.drawImage(swordRight, (int) super.x, (int) super.y,
                            30, 30, null);
                }
                if (swingCounter == 3) {
                    g.drawImage(swordUp, (int) super.x, (int) super.y,
                            30, 30, null);
                }
            }

        }
    }

    @Override
    public void update() {
        updateState();
        //if (this.x > 470 || this.x < 20 || this.y > 540 || this.y < 20) {
        //    this.goNextState();
        //}
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
        swingCounter++;

    }

    public void updateState() {
        if (state == STATE_ALIVE) {
            //double distance = target.distance(super.x, super.y);
            //boolean targetReached = distance <= 2.0;
            //if (targetReached) {
            this.goNextState(); // WORK IN PROGRESS
            // }
        } else if (state == STATE_DYING) {
            if (swingCounter >= MAX_SWING_SIZE) {
                this.goNextState();
            }
        }
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(this.x - SIZE, this.y - SIZE, SIZE * 0.9D, SIZE * 0.9D);
    }

    @Override
    public void shoot() {
        System.out.println(" Melee Attack!!");
    }

}
