package controller;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import model.GameData;
import view.GamePanel;
import view.MainWindow;

//comment

public class Main {

    public static GamePanel gamePanel;
    public static GameData gameData;
    public static Animator animator;
    public static boolean isPaused;//use this to check if we can pause the game

    public static int WIN_WIDTH = 600;
    public static int WIN_HEIGHT = 600;
    public static JLabel posterScreen; 
    
    public static void main(String[] args) {

        animator = new Animator();
        gameData = new GameData();
        gamePanel = new GamePanel();

        isPaused = false; //game doesn't start out paused
        
        JFrame game = new MainWindow();
        game.setTitle("Super Hack N’ Slash Bloody Death Arena 3000");
        game.setSize(WIN_WIDTH, WIN_HEIGHT);
        game.setLocation(100, 0);
        game.setResizable(false); // window size cannot change
        
         try {
            
            URL resource = game.getClass().getResource("logo.png");
            BufferedImage image = ImageIO.read(resource);
            game.setIconImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }   
        ImageIcon posterPic = new ImageIcon("src/controller/background.jpg");
//        ImageIcon posterPic = new ImageIcon("src/controller/groupKnight1.gif");
//        ImageIcon posterPic = new ImageIcon("src/controller/knight.gif");
        posterScreen = new JLabel("Super Hack n' Slash Bloody Death Arena 3000",posterPic, JLabel.CENTER);
        posterScreen.setFont(new Font("Serif", Font.BOLD, 18));
        posterScreen.setVerticalTextPosition(JLabel.BOTTOM);
        posterScreen.setHorizontalTextPosition(JLabel.CENTER);
        gamePanel.add(posterScreen);
         
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setVisible(true);

        // start animation after start button
        //new Thread(animator).start();

    }
}
