package controller;

import javax.swing.JFrame;
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

    public static void main(String[] args) {

        animator = new Animator();
        gameData = new GameData();
        gamePanel = new GamePanel();

        isPaused = false; //game doesn't start out paused
        
        JFrame game = new MainWindow();
        game.setTitle("Term Project Caleb Mills");
        game.setSize(WIN_WIDTH, WIN_HEIGHT);
        game.setLocation(100, 0);
        game.setResizable(false); // window size cannot change
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setVisible(true);

        // start animation after start button
        //new Thread(animator).start();

    }
}
