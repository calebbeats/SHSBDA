/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import static view.GamePanel.height;
import static view.GamePanel.width;

/**
 *
 * @author caleb
 */
public class StartScreen implements GameViewStrategy{
    public Image Start;

    public StartScreen(){
        try {
            Start = ImageIO.read(getClass().getResource("StartButton.png"));
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
           System.exit(-1);
        }
       
    }

    @Override
    public void renderGame(Graphics2D g2) {
        g2.clearRect(0, 0, width, height);
        g2.setBackground(Color.WHITE);
        g2.drawImage(Start, 130, 100, 
                200, 200, null);
        
    }
    
}
