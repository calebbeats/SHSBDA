package view;

import controller.ButtonListener;
import controller.KeyController;
import controller.Main;
import controller.MouseController;
import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame {

   
    public static JButton quitButton;
    public static JButton startGame;

    public MainWindow() {

        Container c = getContentPane();

        c.add(Main.gamePanel, "Center");

        JPanel southPanel = new JPanel();
        
        startGame = new JButton("Start Game"); //adds a start button to bottom frame
        southPanel.add(startGame);
        quitButton = new JButton("Quit");
        southPanel.add(quitButton);
        c.add(southPanel, "South");

        ButtonListener buttonListener = new ButtonListener();
        
        quitButton.addActionListener(buttonListener);
        startGame.addActionListener(buttonListener);

        MouseController mouseController = new MouseController();
        Main.gamePanel.addMouseListener(mouseController);

        KeyController keyListener = new KeyController();
        Main.gamePanel.addKeyListener(keyListener);
        Main.gamePanel.setFocusable(true);
        // just have one Component "true", the rest must be "false"
       
        quitButton.setFocusable(false);
    }

}
