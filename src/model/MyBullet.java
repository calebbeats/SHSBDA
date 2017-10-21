package model;

import controller.Main;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import static model.GameFigure.STATE_ALIVE;
import static model.GameFigure.STATE_DONE;
import static model.GameFigure.STATE_DYING;

public class MyBullet extends GameFigure {

    // missile size
    private static final int SIZE = 10;
    private static final int MAX_EXPLOSION_SIZE = 50;
    private float dx; // displacement at each frame
    private float dy; // displacement at each frame

    // public properties for quick access
    public Color color;
    public Point2D.Float target;

    private static final int UNIT_TRAVEL_DISTANCE = 4; // per frame move

    private int size = SIZE;
    
    private Image launcherImage;
    
    
    /**
     *
     * @param sx start x of the missile
     * @param sy start y of the missile
     * @param tx target x of the missile
     * @param ty target y of the missile
     * @param color color of the missile
     */
    public MyBullet(float sx, float sy, float tx, float ty, Color color) {
        super(sx, sy);
        super.state = STATE_ALIVE;
        target = new Point2D.Float(tx, ty);
        this.color = color;

    }

    @Override
    public void render(Graphics2D g) {
      /*  try {
            launcherImage = ImageIO.read(getClass().getResource("explosion.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open explosion.png");
            System.exit(-1);
        } */
        g.setColor(color);
       /* g.drawOval((int) (super.x - size / 2),
                (int) (super.y - size / 2),
                size, size); */
        g.fillOval((int) (super.x - size / 2),
                (int) (super.y - size / 2),
                size, size);
        //g.fillRect((int)(super.x-2), (int)super.y, 4,20);
        //g.drawImage(launcherImage, (int) super.x, (int) super.y,
        //        50, 50, null);
        
    }
    
    @Override
    public Rectangle2D.Double getCollisionBox() {
        return new Rectangle2D.Double((int) (super.x - size / 2), (int) (super.y - size / 2), size*0.9, size*0.9); 
    }
    
    public boolean Collides(GameFigure anotherGF){
        Rectangle2D.Double thisObj = this.getCollisionBox();
        return thisObj.intersects(anotherGF.getCollisionBox());
    }
    
    @Override
    public String getGFType(){
        return "MyBullet";
    }
    
    @Override
    public void update() {
        updateState();
        if (state == STATE_ALIVE) {
            updateLocation();
        } else if (state == STATE_DYING) {
            updateSize();
        }
    }

    public void updateLocation() {
        
        super.x += 0;
        super.y += -10;
    }

    public void updateSize() {
        size += 2;
    }

    public void updateState() {
        if (state == STATE_ALIVE) {
            double distance = target.distance(super.x, super.y);
            boolean targetReached = distance <= 2.0;
            if (targetReached || (super.x > Main.WIN_WIDTH || super.y > Main.WIN_HEIGHT)) {
                state = STATE_DYING;
                this.goNextState();
            }
        } else if (state == STATE_DYING) {
            if (size >= MAX_EXPLOSION_SIZE) {
                state = STATE_DONE;
                this.goNextState();
            }
        }
    }

    @Override
    public void shoot() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
