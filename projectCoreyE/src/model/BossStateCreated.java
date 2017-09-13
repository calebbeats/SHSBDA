/*
    State Pattern: Concrete State
 */
package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class BossStateCreated implements BossState{
    
    private Image bossImage;
    
    BossStateCreated(){
        bossImage = null;
        
        try {
            bossImage = ImageIO.read(getClass().getResource("bossUFO.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
            System.exit(-1);
        }
    }

    @Override
    public void nextState(Boss context) {
        context.setState(new BossStateExplode());
    }

    @Override
    public void render(Graphics2D g, float x, float y) {
         g.drawImage(bossImage,(int)(x - 200/2), 
                (int)(y - 150/2), 200, 150, null);        
    }
}
