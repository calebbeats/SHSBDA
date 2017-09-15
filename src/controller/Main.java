package controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import model.GameData;
import view.GamePanel;
import view.MainWindow;

//comment

public class Main {

    public static GamePanel gamePanel;
    public static GameData gameData;
    public static Animator animator;

    public static int WIN_WIDTH = 500;
    public static int WIN_HEIGHT = 600;

    public static void main(String[] args) {

        animator = new Animator();
        gameData = new GameData();
        gamePanel = new GamePanel();

        
        JFrame game = new MainWindow();
        game.setTitle("Term Project Caleb Mills");
        
        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //game.setBounds(0,0,screenSize.width, screenSize.height);
        
        game.setSize(WIN_WIDTH, WIN_HEIGHT);
        game.setLocation(100, 0);
        game.setResizable(false); // window size cannot change
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setVisible(true);    
        // start animation after start button
        //new Thread(animator).start();

    }
}
