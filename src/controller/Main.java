package controller;

import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import model.GameData;
import view.GamePanel;
import view.MainWindow;

public class Main {

    public static enum GameState {
        Start, Run, Pause, Quit, LevelComplete, GameOver, Shop, Winner
    }

    public static GamePanel gamePanel;
    public static GameData gameData;
    public static Animator animator;
    public static GameState gameState; // the state that game is currently in
    public static Quadtree quatree;

    public static int WIN_WIDTH = 600;
    public static int WIN_HEIGHT = 600;
    public static JLabel posterScreen;
    
    public static int gameLevel = 1;

    public static void main(String[] args) {
        gameInitialize();
        gamePanel = new GamePanel();
        JFrame game = new MainWindow();
        try {
            game.setIconImage(ImageIO.read(game.getClass()
                    .getResource("/resources/Logo.png")));
        } catch (IOException e) {
            Logger.getLogger(Animator.class.getName())
                    .log(Level.SEVERE, null, e);
        }
        game.setTitle("Super Hack N’ Slash Bloody Death Arena 3000");
        game.setSize(WIN_WIDTH, WIN_HEIGHT);
        game.setLocation(100, 0);
        game.setResizable(false); // window size cannot change
        game.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Thread.currentThread().interrupt();
            }
        });
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setVisible(true);

        new Thread(animator).start();
    }

    public static void gameInitialize() {
        animator = new Animator();
        gameData = new GameData();
        gameState = Main.GameState.Start;
        quatree = new Quadtree(0, new Rectangle(0, 0, WIN_WIDTH, WIN_HEIGHT));
    }
}
