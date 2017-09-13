package view;

import controller.Main;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;


public class GamePanel extends JPanel {
    
    // size of the canvas - determined at runtime once rendered
    public static int width;
    public static int height;
    
    // off screen rendering
    private Graphics2D g2;
    private final Image background = Toolkit.getDefaultToolkit().createImage("background.jpg");
    private Image dbImage = null; // double buffer image

    public void gameRender() {
        width = getSize().width;
        height = getSize().height;
        if (dbImage == null) {
            // Creates an off-screen drawable image to be used for double buffering
            dbImage = createImage(width, height);
            if (dbImage == null) {
                System.out.println("Critical Error: dbImage is null");
                System.exit(1);
            } else {
                g2 = (Graphics2D) dbImage.getGraphics();
            }
        }
        
      

        
        Main.gameData.strategy.renderGame(g2);
        
    }

    // use active rendering to put the buffered image on-screen
    public void printScreen() {
        Graphics g;
        try {
            
            g = this.getGraphics();
            if ((g != null) && (dbImage != null)) {
                g.drawImage(background, 0, 0, width, height, null);
                g.drawImage(dbImage, 0, 0, null);
            }
            Toolkit.getDefaultToolkit().sync();  // sync the display on some systems
            if (g != null) {
                g.dispose();
            }
        } catch (Exception e) {
            System.out.println("Graphics error: " + e);
        }
    }
}
