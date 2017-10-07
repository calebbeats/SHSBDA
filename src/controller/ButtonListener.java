package controller;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.MainWindow;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import view.ShopWindow;
import view.HighScore;

public class ButtonListener implements ActionListener {

    public Image backGround;
    private Graphics2D g2;
    public static Boolean choosedButton = false;
    //public static int choosedButton = 0;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == MainWindow.startGame) {
            try {
                //MainWindow.get
                Main.animator.running = true;
                MainWindow.startGame.setEnabled(false);
                Main.gamePanel.remove(Main.posterScreen);
                new Thread(Main.animator).start();
                //choosedButton = 1;
                choosedButton = true;
                audio();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException ex) {
                Logger.getLogger(ButtonListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == MainWindow.highScores) {
            try {
                HighScore.createFrame();
            } catch (IOException ex) {
                Logger.getLogger(ButtonListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == MainWindow.quitButton) {
            //choosedButton = 2;
            choosedButton = false;
            if (Main.animator.running) {
                Main.animator.running = false;
            } else {
                System.exit(0);
            }
        } else if (e.getSource() == MainWindow.shopButton) {
            JFrame shop = new ShopWindow();
            shop.setVisible(true);
            shop.setSize(400, 500);
            shop.setLocation(150, 100);
            shop.setResizable(false); // window size cannot change
            shop.setVisible(true);
        }
    }

    public void render(Graphics2D g) {
        g.drawImage(backGround, 0, 0,
                550, 600, null);
    }

    public void audio() throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {

        AudioInputStream stream = null;
        try {
            //File file = new File("C:/Users/dinhn/Documents/GitHub/SHSBDA/PatakasWorld.wav");
            stream = AudioSystem.getAudioInputStream(getClass().getResource("PatakasWorld.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            clip.start();
            stream.close();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
