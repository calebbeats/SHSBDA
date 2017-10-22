package view;

import controller.KeyController;
import controller.Main;
import controller.MouseController;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class MainWindow extends JFrame {

    public static JTextField scoreText;
    public static int score = 0;
    public static JTextField coinText;
    public static int coins = 0;

    public MainWindow() {

        Container c = getContentPane();

        c.add(Main.gamePanel, "Center");

        MouseController mouseController = new MouseController();
        Main.gamePanel.addMouseListener(mouseController);
        Main.gamePanel.addMouseMotionListener(mouseController);

        KeyController keyListener = new KeyController();
        Main.gamePanel.addKeyListener(keyListener);
        Main.gamePanel.setFocusable(true);
        // just have one Component "true", the rest must be "false"

        scoreText = new JTextField("Score: " + MainWindow.score
                + " || Coins: " + MainWindow.coins);
        c.add(scoreText, "North");
        scoreText.setFocusable(false);
        scoreText.setEditable(false);
    }
}
