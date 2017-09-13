/*
    State Pattern: Concrete
 */
package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class BossStateExplode implements BossState{
    
    private Image explodeImage;
    
    BossStateExplode(){
        explodeImage = null;

        try {
            explodeImage = ImageIO.read(getClass().getResource("bigExplosion.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
            System.exit(-1);
        }
    }

    @Override
    public void nextState(Boss context) {
        //no next state
    }

    @Override
    public void render(Graphics2D g, float x, float y) {
        g.drawImage(explodeImage, (int)x-400, (int)y-150, 800, 300, null);
    }
}
