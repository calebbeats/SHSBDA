package view;

import controller.KeyController;
import controller.Main;
import controller.MouseController;
import java.awt.Container;
import javax.swing.JFrame;


public class MainWindow extends JFrame {



    public MainWindow() {

        Container c = getContentPane();
        
        c.add(Main.gamePanel, "Center");

        MouseController mouseController = new MouseController();
        Main.gamePanel.addMouseListener(mouseController);

        KeyController keyListener = new KeyController();
        Main.gamePanel.addKeyListener(keyListener);
        Main.gamePanel.setFocusable(true);

    }

}
