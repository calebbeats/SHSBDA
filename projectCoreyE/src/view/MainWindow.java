package view;

import controller.ButtonListener;
import controller.KeyController;
import controller.Main;
import controller.MouseController;
import java.awt.Container;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainWindow extends JFrame {

    public static JButton quitButton;
    public static JButton startGame;
    public static JTextField gameInfo;
    
    public MainWindow() {

        Container c = getContentPane();

        c.add(Main.gamePanel, "Center");

        Font textFont = new Font("TimesRoman", Font.BOLD, 24);
        
        gameInfo = new JTextField();
        gameInfo.setEditable(false);
        gameInfo.setFont(textFont);
        gameInfo.setHorizontalAlignment(JTextField.CENTER);
        
        JPanel southPanel = new JPanel();
        startGame = new JButton("Start Game");
        southPanel.add(startGame);
        quitButton = new JButton("Quit");
        southPanel.add(quitButton);
        
        c.add(southPanel, "South");
        c.add(gameInfo,"North");

        ButtonListener buttonListener = new ButtonListener();
        quitButton.addActionListener(buttonListener);
        startGame.addActionListener(buttonListener);
        
        MouseController mouseController = new MouseController();
        Main.gamePanel.addMouseListener(mouseController);

        KeyController keyListener = new KeyController();
        Main.gamePanel.addKeyListener(keyListener);
        Main.gamePanel.setFocusable(true);
        // just have one Component "true", the rest must be "false"
        startGame.setFocusable(false);
        quitButton.setFocusable(false);
        gameInfo.setFocusable(false);
    }

}
