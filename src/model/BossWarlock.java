
package model;

import controller.Main;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import static model.GameFigure.STATE_ALIVE;


public class BossWarlock extends GameFigure {
    
    private static final int SIZE = 50;
   
    private int timer = 0;
    private int deadTimer = 0;
    private Image warlock1;
    private Image warlock2;
    private Image death1;
    private Image death2;
    private Image death3;
    
    public static int summonTime = 0;
    
    public FigureState eState;
    
    private int direction = 1; // +1: to the right; -1 to the left
    public BossWarlock(float x, float y) {
        super(x, y);
        super.state = STATE_ALIVE;
        try {
            warlock1 = ImageIO.read(getClass().getResource("/resources/warlock1.png"));
            warlock2 = ImageIO.read(getClass().getResource("/resources/warlock2.png"));
            death1 = ImageIO.read(getClass().getResource("/resources/fire1.png"));
            death2 = ImageIO.read(getClass().getResource("/resources/fire2.png"));
            death3 = ImageIO.read(getClass().getResource("/resources/fire3.png"));
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
           System.exit(-1);
        }
        bossTimer=0;
        timer=0;
    }

    @Override
    public void render(Graphics2D g) {
        if(state == STATE_ALIVE)
        {
            if(timer < 25)
            {
                g.drawImage(warlock1, (int)super.x, (int)super.y, 
                SIZE, SIZE, null);
            }
            if(timer >= 25 && timer <50)
            {
                g.drawImage(warlock2, (int)super.x, (int)super.y, 
                SIZE, SIZE, null);
            }
            if(timer >= 50 && timer < 75)
            {
                g.drawImage(warlock1, (int)super.x, (int)super.y, 
                SIZE, SIZE, null);
            }
            if(timer >= 75 && timer < 100)
            {
                g.drawImage(warlock2, (int)super.x, (int)super.y, 
                SIZE, SIZE, null);
            }
        }
        if(state == STATE_DYING)
        {
            if(deadTimer < 10)
            {
            g.drawImage(death1, (int)super.x, (int)super.y, 
            SIZE, SIZE, null);
            }
            else if(deadTimer < 20)
            {
            g.drawImage(death2, (int)super.x, (int)super.y, 
            SIZE, SIZE, null);
            }
            else if(deadTimer < 30)
            {
            g.drawImage(death3, (int)super.x, (int)super.y, 
            SIZE, SIZE, null);
            }
            
        }
    }

    @Override
    public void update() {
        if(state == STATE_ALIVE)
        {
            if (timer < 100) 
            {
                timer++;
            }
            else
            {
                float intendedX = (float) Math.random()*450;
                float intendedY = (float) Math.random()*520;
                GameFigure mageToMove = new BasicCollisionBox(intendedX, intendedY, SIZE, SIZE);
                
                for(GameFigure t : Main.gameData.terrainFigures){
                    if(!(mageToMove.getCollisionBox().intersects(t.getCollisionBox()))){
                        super.x = intendedX;
                        super.y = intendedY;
                    }
                    else{
                        intendedX = (float) Math.random()*450;
                        intendedY = (float) Math.random()*520;
                        super.x = intendedX;
                        super.y = intendedY;
                    }
                }
                //super.x = (float) Math.random()*450;
                //super.y = (float) Math.random()*520;
                timer = 0;
            }
            if(bossTimer < 100)
            {
                bossTimer++;
            }
            else
            {
                bossTimer = 0;
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
    
    public static void summon(){
        if(summonTime < 50)  {
            summonTime++;
        }
        else    {
            summonTime = 0;
        }
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(this.x, this.y, SIZE*0.9D, SIZE*0.9D);
    }

    @Override
    public void shoot() {
        System.out.println("Blink Mage Shoots");
    }
}

