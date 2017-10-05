package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.MainWindow;
import view.ShopWindow;


public class ShopButtonListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == ShopWindow.weakPotionButton){
            if(MainWindow.coins >= 1){
                System.out.println(MainWindow.coins); 
                MainWindow.coins -= 1;
                MainWindow.scoreText.setText("Score: " + MainWindow.score + " || Coins: " + MainWindow.coins);
            }
        }
        else if(e.getSource() == ShopWindow.mediumPotionButton){
            if(MainWindow.coins >= 2){
                System.out.println(MainWindow.coins);    
                MainWindow.coins -= 2;
                MainWindow.scoreText.setText("Score: " + MainWindow.score + " || Coins: " + MainWindow.coins);
            }
        }
        if(e.getSource() == ShopWindow.strongPotionButton){
            if(MainWindow.coins >= 3){
                System.out.println(MainWindow.coins);
                MainWindow.coins -= 3;
                MainWindow.scoreText.setText("Score: " + MainWindow.score + " || Coins: " + MainWindow.coins);
            }
        }
    }
    
}
