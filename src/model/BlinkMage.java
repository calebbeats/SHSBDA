
package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import model.GameFigure;
import static model.GameFigure.STATE_ALIVE;


public class BlinkMage extends GameFigure {
    
    private int timer = 0;
    private int deadTimer = 0;
    private Image blinkMage0;
    private Image blinkMage1;
    private Image blinkMage2;
    private Image blinkMage3;
    private Image deadMage0;
    private Image deadMage1;
    private Image deadMage2;
    public FigureState eState;
    
    private int direction = 1; // +1: to the right; -1 to the left
    
    public BlinkMage(float x, float y) {
        super(x, y);
        super.state = STATE_ALIVE;
        try {
            blinkMage0 = ImageIO.read(getClass().getResource("blinkmage0.png"));
            blinkMage1 = ImageIO.read(getClass().getResource("blinkmage1.png"));
            blinkMage2 = ImageIO.read(getClass().getResource("blinkmage2.png"));
            blinkMage3 = ImageIO.read(getClass().getResource("blinkmage3.png"));
            deadMage0 = ImageIO.read(getClass().getResource("blinkmagedeath0.png"));
            deadMage1 = ImageIO.read(getClass().getResource("blinkmagedeath1.png"));
            deadMage2 = ImageIO.read(getClass().getResource("blinkmagedeath2.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
           System.exit(-1);
        }
        shootTimer=0;
        timer=0;
    }

    @Override
    public void render(Graphics2D g) {
        if(state == STATE_ALIVE)
        {
            if(timer < 10)
            {
                g.drawImage(blinkMage0, (int)super.x, (int)super.y, 
                30, 30, null);
            }
            if(timer >= 10 && timer <20)
            {
                g.drawImage(blinkMage1, (int)super.x, (int)super.y, 
                30, 30, null);
            }
            if(timer >= 20 && timer < 40)
            {
                g.drawImage(blinkMage2, (int)super.x, (int)super.y, 
                30, 30, null);
            }
            if(timer >= 40 && timer < 70)
            {
                g.drawImage(blinkMage3, (int)super.x, (int)super.y, 
                30, 30, null);
            }
        }
        if(state == STATE_DYING)
        {
            if(deadTimer < 5)
            {
            g.drawImage(deadMage0, (int)super.x, (int)super.y, 
            30, 30, null);
            }
            else if(deadTimer < 10)
            {
            g.drawImage(deadMage1, (int)super.x, (int)super.y, 
            30, 30, null);
            }
            else if(deadTimer < 15)
            {
            g.drawImage(deadMage2, (int)super.x, (int)super.y, 
            30, 30, null);
            }
            
        }
    }

    @Override
    public void update() {
        if(state == STATE_ALIVE)
        {
            if (timer < 60 ) 
            {
                timer++;
            }
            else
            {
                super.x = (float) Math.random()*450;
                super.y = (float) Math.random()*520;
                timer = 0;
            }
            if(shootTimer < 20)
            {
                shootTimer++;
            }
            else
            {
                shootTimer = 0;
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

