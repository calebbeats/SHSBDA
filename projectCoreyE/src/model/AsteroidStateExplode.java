/*
    State pattern: concrete state
*/
package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class AsteroidStateExplode implements AsteroidState
{
    private Image explodeImage;
    
    AsteroidStateExplode(){
        explodeImage = null;

            try {
                explodeImage = ImageIO.read(getClass().getResource("explosion.png"));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
                System.exit(-1);
            }
    }
    
    @Override
    public void nextState(Asteroid context){
//        context.setState(new BombStateDone());
    }

    @Override
    public void render(Graphics2D g, int radius, float x, float y) {        
            g.drawImage(explodeImage, (int)x-20, (int)y-5, 50, 50, null);
     }        
    
}
