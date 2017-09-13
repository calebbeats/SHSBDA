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

    public static JButton addBombButton;
    public static JButton addUFOButton;
    public static JButton quitButton;

    public MainWindow() {

        Container c = getContentPane();

        c.add(Main.gamePanel, "Center");

        JPanel southPanel = new JPanel();
        addBombButton = new JButton("Add 10");
        addUFOButton = new JButton("Add UFO");
        southPanel.add(addBombButton);
        southPanel.add(addUFOButton);
        quitButton = new JButton("Quit");
        southPanel.add(quitButton);
        c.add(southPanel, "South");

        ButtonListener buttonListener = new ButtonListener();
        addBombButton.addActionListener(buttonListener);
        addUFOButton.addActionListener(buttonListener);
        quitButton.addActionListener(buttonListener);

        MouseController mouseController = new MouseController();
        Main.gamePanel.addMouseListener(mouseController);

        KeyController keyListener = new KeyController();
        Main.gamePanel.addKeyListener(keyListener);
        Main.gamePanel.setFocusable(true);
        // just have one Component "true", the rest must be "false"
        addBombButton.setFocusable(false);
        addUFOButton.setFocusable(false);
        quitButton.setFocusable(false);
    }

}
