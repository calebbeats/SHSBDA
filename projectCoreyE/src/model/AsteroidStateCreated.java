/*
    State Pattern: ConcreteState
*/
package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class AsteroidStateCreated implements AsteroidState
{
    private Image asteroidImage;
    
    AsteroidStateCreated(){
        asteroidImage = null;        
        
       try {
            asteroidImage = ImageIO.read(getClass().getResource("Small_Asteroid.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
            System.exit(-1);
        }
    }
    
    @Override
    public void nextState(Asteroid context){
        context.setState(new AsteroidStateExplode());
    }
        
    @Override
    public void render(Graphics2D g, int radius, float x, float y){
         g.drawImage(asteroidImage, (int)(x - radius), (int)(y - radius), 
                radius * 2, radius * 2, null);
    }

}
