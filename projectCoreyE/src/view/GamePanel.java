package view;

import controller.Main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;
import model.GameFigure;

public class GamePanel extends JPanel {
    
    // size of the canvas - determined at runtime once rendered
    public static int width;
    public static int height;

    // off screen rendering
    public Graphics2D g2;
    private Image dbImage = null; // double buffer image
    public boolean gameWon = false;    

    public GamePanel(){
        this.setBackground(Color.black);        
    }
    
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
        
        if(gameWon == false && Main.gameData.levelTwo == false){ //the main background when the game is being played
            g2.clearRect(0, 0, width, height);
            g2.setBackground(Color.BLACK);
        }
        else if(Main.gameData.levelTwo == true && gameWon == false)
        {
            g2.clearRect(0,0,width,height);
            g2.setBackground(Color.DARK_GRAY);
            g2.setColor(Color.RED);
            g2.setFont(new Font("Chiller", Font.BOLD, 45)); 
            g2.drawString("BOSS FIGHT", width/2-90, height/2);
        }
        else if(gameWon == true){ //the background when the game ends and the player wins            
            g2.clearRect(0,0,width,height);
            g2.setBackground(Color.LIGHT_GRAY);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Stencil", Font.BOLD, 45)); 
            g2.drawString("You Won!!", width/2-90, height/2);
        }

        if (Main.animator.running) {                        
            synchronized (Main.gameData.enemyFigures) {                
                for (GameFigure f : Main.gameData.enemyFigures) {
                    f.render(g2);
                }
            }
            synchronized (Main.gameData.friendFigures){                            
                for (GameFigure f : Main.gameData.friendFigures) {
                    f.render(g2);
                }
            }
        }
    }
    
    // use active rendering to put the buffered image on-screen
    public void printScreen() {
        Graphics g;
        try {
            g = this.getGraphics();
            if ((g != null) && (dbImage != null)) {
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
