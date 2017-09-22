package controller;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import sun.audio.*;
import java.io.*;
import view.MainWindow;
import view.GamePanel;
import model.GameFigure;
import static view.GamePanel.height;
import static view.GamePanel.width;
import java.awt.event.*;
import static view.GamePanel.height;
import static view.GamePanel.width;
import java.awt.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;



public class ButtonListener implements ActionListener {

    public Image backGround;
    private Graphics2D g2;
    public static Boolean choosedButton = false;
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == MainWindow.startGame){
            try {
                //MainWindow.get
                MainWindow.startGame.setEnabled(false);
                new Thread(Main.animator).start();
                choosedButton = true;
                audio();
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(ButtonListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ButtonListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(ButtonListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(ButtonListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (e.getSource() == MainWindow.quitButton) {
            choosedButton = false;
            if (Main.animator.running) {
                
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ButtonListener.class.getName()).log(Level.SEVERE, null, ex);
                }
                Main.animator.running = false;
                System.exit(0);
            } else {
                System.exit(0);
            }
        }
    }
    
    public void render(Graphics2D g) {
        g.drawImage(backGround, 0, 0, 
                550, 600, null);
    }
       
    public void audio() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException{
              
        AudioInputStream stream = null;
        try {
             //File file = new File("C:/Users/dinhn/Documents/GitHub/SHSBDA/PatakasWorld.wav");
             stream = AudioSystem.getAudioInputStream(getClass().getResource("PatakasWorld.wav"));
             Clip clip = AudioSystem.getClip();
             clip.open(stream);
             clip.start();
             stream.close();
          }catch(Exception ex){
              System.out.println(ex.getMessage());
          }
          
      }
}
