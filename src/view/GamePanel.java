package view;

import controller.Main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import model.GameFigure;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;
import controller.ButtonListener;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class GamePanel extends JPanel {

    // size of the canvas - determined at runtime once rendered
    public static int width;
    public static int height;

    // off screen rendering
    public Graphics2D g2;
    private Image dbImage = null; // double buffer image
    public Image backGround;

    public void gameRender() throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
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

        g2.clearRect(0, 0, width, height);

        if (ButtonListener.choosedButton == true) {
//           backGround = ImageIO.read(getClass().getResource("startScreen.png"));
//           g2.drawImage(backGround, 0, 0, width, height, null);
            //audio();
            g2.setBackground(Color.BLACK);
        } else {

            backGround = ImageIO.read(getClass().getResource("gameFinish.png"));
            g2.drawImage(backGround, 0, 0, width, height, null);
        }
        //backGround = ImageIO.read(getClass().getResource("startScreen.png"));
        //g2.drawImage(backGround, 0, 0, width, height, null);
        //g2.setBackground(Color.BLACK);

        if (Main.animator.running) {

            for (GameFigure f : Main.gameData.enemyFigures) {
                f.render(g2);
            }

            for (GameFigure f : Main.gameData.friendFigures) {
                f.render(g2);
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
