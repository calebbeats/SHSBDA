
package model;

import controller.Main;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import static model.GameFigure.STATE_ALIVE;
import static model.GameFigure.STATE_DYING;
import view.GamePanel;

public class RockMage extends GameFigure {
    
    private final int WIDTH = 20;
    private final int UNIT_TRAVEL = 5; // per frame
    private int deadTimer=0;
    
    private Image rockMage;
    private Image deadMage0;
    private Image deadMage1;
    private Image deadMage2;
    
    private int direction = 1; // +1: to the right; -1 to the left
    
    public RockMage(float x, float y) {
        super(x, y);
        super.state = STATE_ALIVE;
        try {
            rockMage = ImageIO.read(getClass().getResource("rockmage.png"));
            
            deadMage0 = ImageIO.read(getClass().getResource("rockmagedeath0.png"));
            deadMage1 = ImageIO.read(getClass().getResource("rockmagedeath1.png"));
            deadMage2 = ImageIO.read(getClass().getResource("rockmagedeath2.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
           System.exit(-1);
        }
        shootTimer=0;
    }

    @Override
    public void render(Graphics2D g) {
            if(state == STATE_ALIVE)
            {
                g.drawImage(rockMage, (int)super.x, (int)super.y, 
                30, 30, null);
        
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
            if (direction > 0) {
                // moving to the right
                super.y += UNIT_TRAVEL;
                if (super.y + WIDTH > GamePanel.height) {
                    direction = -1; 
                }
            } else {
                // moving to the left
                super.y -= UNIT_TRAVEL;
                if (super.y - WIDTH/2 <= 0) {
                    direction = 1;  
                }    
            }
            
        }
        if(shootTimer < 20)
        {
            shootTimer++; 
        }
        else
        {
            shootTimer = 0;
        }
        if(state == STATE_DYING)
        {
            deadTimer++;
        }
        if(deadTimer > 10)
        {
            this.goNextState();
        }
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(this.x, this.y, 20, 25);
    }
    
    public void shoot()
    {
       if(shootTimer > 15)
            {
                
                    Main.gameData.enemyFigures.add(new EnemyMissile(this.x, this.y));
                
                shootTimer = 0;
            }
            shootTimer++; 
    }
}
