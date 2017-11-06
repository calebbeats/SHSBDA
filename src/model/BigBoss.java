package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import static model.GameFigure.STATE_ALIVE;
import view.GamePanel;

public class BigBoss extends GameFigure {

    private final int WIDTH = 10;
    private final int HEIGHT = 25;
    private final Color color = Color.red;
    private final int UNIT_TRAVEL = 5; // per frame

    //   private final int radius;
    private int dx = 3;
    private int dy = 3;
    private int direction = 1; // +1: move down; -1 move up

    private int directionX = 1; // +1: move down; -1 move up
    private int count = 0;
    private int maxcount = 30;

    private Image launcherImage;

    public BigBoss(int x, int y) {

        super(x, y);
        super.state = STATE_ALIVE;

        launcherImage = null;

        /*   try {
         launcherImage = ImageIO.read(getClass().getResource("alien.png"));
         } catch (IOException ex) {
         JOptionPane.showMessageDialog(null, "Error: Cannot open alien.png");
         System.exit(-1);
         }*/
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        //  g.fill(new Rectangle2D.Double(super.x, super.y, WIDTH, HEIGHT));
        // g.drawImage(launcherImage, (int) super.x, (int) super.y,
        //         60, 40, null);
        // g.fill(new Rectangle2D.Double((double)super.x, (double)super.y, WIDTH + 50, HEIGHT + 10));
        g.fill(new Rectangle2D.Double((double) super.x, (double) super.y, WIDTH, HEIGHT));
               // g.fill(new Rectangle2D.Double((double)super.x, (double)super.y - 4, WIDTH, HEIGHT));
        //  g.fill(new Rectangle2D.Double((double)super.x + 4, (double)super.y - 4, WIDTH, HEIGHT));

    }

    @Override
    public Rectangle2D.Double getCollisionBox() {
        return new Rectangle2D.Double((int) (super.x - WIDTH / 2), (int) (super.y - HEIGHT / 2), WIDTH * 0.9, HEIGHT * 0.9); // to be corrected
    }

    public boolean Collides(GameFigure anotherGF) {
        Rectangle2D.Double thisObj = this.getCollisionBox();
        //anotherObj = anotherGF.
        return thisObj.intersects(anotherGF.getCollisionBox());

    }

    @Override
    public String getGFType() {
        return "Alien";
    }

    /* @Override
     public void update() {
     super.y += 5*(direction);
        
     if(super.y + HEIGHT > GamePanel.height){
     direction = -1;
     }
     if(super.y < 0){
     direction = 1;
     }
        
     if(directionX == 1){// going right
     count ++;
     if(count < maxcount){
     super.x += 5;
     }else {
     directionX =- 1;
     super.x -= 5;
     count = 0;
     }    
     }else if(directionX == -1){ // going left
     count ++;
     if(count < maxcount){
     super.x -= 5;
     }else {
     directionX = 1;
     super.x += 5;
     count = 0;
     }
     }
     } */
    @Override
    public void update() {
        if (direction > 0) {
            // moving to the right
            super.x += UNIT_TRAVEL;
            if (super.x + WIDTH / 2 > GamePanel.width) {
                direction = -1;
            }
        } else {
            // moving to the left
            super.x -= UNIT_TRAVEL;
            if (super.x - WIDTH / 2 <= 0) {
                direction = 1;
            }
        }
    }

    @Override
    public void shoot() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
