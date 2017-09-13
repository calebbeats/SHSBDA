package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.MainWindow;

public class ButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == MainWindow.startGame){
            MainWindow.startGame.setEnabled(false);
            new Thread(Main.animator).start();
        }
        else if (ae.getSource() == MainWindow.quitButton) {
            if (Main.animator.running) {
                Main.animator.running = false;
                System.exit(0);
            } else {
                System.exit(0);
            }
        }        
    }

}
