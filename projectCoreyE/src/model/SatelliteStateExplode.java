/*
    State pattern: Concrete State
 */
package model;
 
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class SatelliteStateExplode implements SatelliteState{
        private Image scrapImage;

        SatelliteStateExplode(){
            scrapImage = null;

            try {
                scrapImage = ImageIO.read(getClass().getResource("satExplode.png"));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
                System.exit(-1);
            }
        }

    @Override
    public void nextState(Satellite context) {
        //no other state
    }

    @Override
    public void render(Graphics2D g, float x, float y) {
        g.drawImage(scrapImage, (int)x-60, (int)y-60, 120, 120, null);
    }
}
