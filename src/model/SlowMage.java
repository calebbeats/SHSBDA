package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import static model.GameFigure.STATE_ALIVE;
import static model.GameFigure.STATE_DYING;


public class SlowMage extends GameFigure {
    
    private int timer = 0;
    private int deadTimer = 0;
    private Image slowMage1;
    private Image slowMage2;
    public FigureState eState;
    private int direction = 1; // +1: to the right; -1 to the left
    
    public SlowMage(float x, float y) {
        super(x, y);
        super.state = STATE_ALIVE;
        try {
            slowMage1 = ImageIO.read(getClass().getResource("/resources/slowMage1.png"));
            slowMage2 = ImageIO.read(getClass().getResource("/resources/slowMage2.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
           System.exit(-1);
        }
        slowTimer=0;
        timer=0;
    }

    @Override
    public void render(Graphics2D g) {
        if(state == STATE_ALIVE)
        {
            if(timer < 10)
            {
                g.drawImage(slowMage2, (int)super.x, (int)super.y, 
                30, 30, null);
            }
            if(timer >= 10 && timer <20)
            {
                g.drawImage(slowMage1, (int)super.x, (int)super.y, 
                30, 30, null);
            }
            if(timer >= 20 && timer < 40)
            {
                g.drawImage(slowMage2, (int)super.x, (int)super.y, 
                30, 30, null);
            }
            if(timer >= 40 && timer < 70)
            {
                g.drawImage(slowMage1, (int)super.x, (int)super.y, 
                30, 30, null);
            }
        }
        if(state == STATE_DYING){
            
            //Create Death Animation
            //------------------------------
        }
    }

    @Override
    public void update() {
        if(state == STATE_ALIVE)
        {
            //Attack Speed of Slow Mage
            //-----------------------------------
            if(slowTimer < 50)
            {
                slowTimer++;
            }
            else
            {
                slowTimer = 0;
            }
        }
        
        if(state == STATE_DYING)
        {
            deadTimer++;
            if(deadTimer >15)
            {
                this.goNextState();
            }
        }   
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(this.x, this.y, 20, 25);
    }

    @Override
    public void shoot() {
        System.out.println("Blink Mage Shoots");
    }
}

