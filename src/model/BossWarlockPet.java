/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

public class BossWarlockPet extends GameFigure {

    // missile size
    private static final int SIZE = 40;
    
    //Final Despawn Counter
    //Go-to "render() -> STATE_DYING"
    //------------------------------
    private static final int MAX_DEATH_DESPAWN = 20;
    private static final int MAX_ATTK_TIME = 10;
    private static int deathCounter=0;
    private static int attackCounter=0;
    private static int aniTimer=0;

    
    private float dx; // displacement at each frame
    private float dy; // displacement at each frame
    private int animationCheck=0;
    
    private float ox;
    private float oy;
    private float ty;
    private float tx;

    // public properties for quick access
    public Point2D.Float target;

    private static final int UNIT_TRAVEL_DISTANCE = 1; // per frame move
  
    private Image alive;
    private Image alive2;   
    private Image death;

    /**
     *
     * @param sx start x of the missile
     * @param sy start y of the missile
     * @param tx target x of the missile
     * @param ty target y of the missile
     * @param color color of the missile
     */
    public BossWarlockPet(float sx, float sy) {
        super(sx, sy);
        
        petSwingTimer=0;
        
        tx = Main.gameData.shooter.x + 10;
        ty = Main.gameData.shooter.y + 10;
        this.target = new Point2D.Float(tx, ty);
               
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
        
        alive = null;
        alive2 = null;      
        death = null;
        
        try {
            alive = ImageIO.read(getClass().getResource("/resources/warlockPet1.png"));
            alive2 = ImageIO.read(getClass().getResource("/resources/warlockPet2.png"));
            death = ImageIO.read(getClass().getResource("/resources/warlockPetDead.png"));
      
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
           System.exit(-1);
        }
        
    }

    @Override
    public void render(Graphics2D g) {
        if(state == STATE_ALIVE)    {
            if(aniTimer < 25)
            {
                g.drawImage(alive, (int)super.x, (int)super.y, 
                SIZE, SIZE, null);
            }
            if(aniTimer >= 25 && aniTimer <50)
            {
                g.drawImage(alive2, (int)super.x, (int)super.y, 
                SIZE, SIZE, null);
            }
            if(aniTimer >= 50 && aniTimer < 75)
            {
                g.drawImage(alive, (int)super.x, (int)super.y, 
                SIZE, SIZE, null);
            }
            if(aniTimer >= 75 && aniTimer < 100)
            {
                g.drawImage(alive2, (int)super.x, (int)super.y, 
                SIZE, SIZE, null);
            } else if (aniTimer == 100){
                aniTimer=0;
            }
        }
        if(state == STATE_DYING)    {
            
            //Death Counter = 20
            //Time to show dead sprite
            //------------------------------
            if ((deathCounter & 1)==0)  {
                g.drawImage(death, (int)super.x, (int)super.y, 
                SIZE, SIZE, null); 
            } else 
                g.drawImage(death, (int)super.x, (int)super.y, 
                SIZE, SIZE, null);           
        }
    }

    @Override
    public void update() {
        updateState();
        if (state == STATE_ALIVE) {
            updateLocation();
            updateSwing();
            aniTimer++;            
        } else if (state == STATE_DYING) {
            updateSize();
        }
    }

    public void updateLocation() {
        GameFigure enemyToMove = new BossWarlockPet(super.x + dx, super.y +dy);        
        
        for(GameFigure t : Main.gameData.terrainFigures){
            if(!(enemyToMove.getCollisionBox().intersects(t.getCollisionBox()))){
                super.x += dx;
                super.y += dy;
            }
            else{
                
                tx = Main.gameData.shooter.x + 10;
                ty = Main.gameData.shooter.y + 10;
                System.out.println("Tx Ty" + tx + " " + ty);
                this.target = new Point2D.Float(tx, ty);
        
                double angle = Math.atan2(Math.abs(ty - super.y), Math.abs(tx - super.x));
                dx = (float) (UNIT_TRAVEL_DISTANCE * Math.cos(angle));
                dy = (float) (UNIT_TRAVEL_DISTANCE * Math.sin(angle));
        
                if (tx > super.x && ty < super.y) { // target is upper-right side
                    dy = -dy; // dx > 0, dx < 0
                } else if (tx < super.x && ty < super.y) { // target is upper-left side
                    dx = -dx;
                    dy = -dy;
                } else if (tx < super.x && ty > super.y) { // target is lower-left side
                    dx = -dx;
                } else { // target is lower-right side
                    
                }
                System.out.println("Dx Dy" + dx + " " + dy);
                
                enemyToMove = new BossWarlockPet(super.x + dx, super.y);
                if (!(enemyToMove.getCollisionBox().intersects(t.getCollisionBox()))) {
                    super.x += dx;
                    super.y -= 2*dy;
                }
                else{
                    enemyToMove = new BossWarlockPet(super.x, super.y + dy);
                    if (!(enemyToMove.getCollisionBox().intersects(t.getCollisionBox()))) {
                        super.y += dy;
                        super.x -= 2*dx;
                    }
                }
                
                return;
            }
            enemyToMove = new BossWarlockPet(super.x + dx, super.y +dy);
        }
        enemyToMove = null;        
    }

    public void updateSize() {
        deathCounter++;         
    }
    
    public void updateAttack(){
        attackCounter++;
    }
    
    public void updateSwing(){
        double distance = target.distance(super.x, super.y);
        boolean attackRange = distance <= 10.0;
        if (attackRange) {
            //Attack Speed of Melee
            //-----------------------------------
            if(petSwingTimer < 50)  {
                    petSwingTimer++;
            }
            else    {
                petSwingTimer = 0;
            }
        }
    }

    public void updateState() {
        if (state == STATE_ALIVE) {
            double distance = target.distance(super.x, super.y);
            boolean targetReached = distance <= 10.0;
            if (targetReached) {
     
                ox = tx;
                oy = ty;
               
                tx = Main.gameData.shooter.x + 10;
                ty = Main.gameData.shooter.y + 10;
                System.out.println("Tx Ty" + tx + " " + ty);
                this.target = new Point2D.Float(tx, ty);
        
                double angle = Math.atan2(Math.abs(ty - super.y), Math.abs(tx - super.x));
                dx = (float) (UNIT_TRAVEL_DISTANCE * Math.cos(angle));
                dy = (float) (UNIT_TRAVEL_DISTANCE * Math.sin(angle));
        
                if (tx > super.x && ty < super.y) { // target is upper-right side
                    dy = -dy; // dx > 0, dx < 0
                } else if (tx < super.x && ty < super.y) { // target is upper-left side
                    dx = -dx;
                    dy = -dy;
                } else if (tx < super.x && ty > super.y) { // target is lower-left side
                    dx = -dx;
                } else { // target is lower-right side
                    
                }
                System.out.println("Dx Dy" + dx + " " + dy);
            }
        } 
        //If Dead (Counter = MAX)
        //goNextState
        //------------------------------
        else if (state == STATE_DYING) {
            if(deathCounter >= MAX_DEATH_DESPAWN){
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
       System.out.println("Enemy Missiles Shoots");
    }

}
