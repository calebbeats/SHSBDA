/*
    State Pattern: concrete State
 */
package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class SatelliteStateCreated implements SatelliteState{
    private Image liteImage;
    
    SatelliteStateCreated(){
        liteImage = null;
        
        try {
            liteImage = ImageIO.read(getClass().getResource("satellite.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
            System.exit(-1);
        }
    }
    
    @Override
    public void render(Graphics2D g, float x, float y){
        g.drawImage(liteImage,(int)(x - 40/2), (int)(y - 10/2), 50, 50, null);
    }

    @Override
    public void nextState(Satellite context) {
        context.setState(new SatelliteStateExplode());
    }
}
