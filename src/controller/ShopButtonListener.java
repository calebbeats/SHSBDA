package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.MainWindow;
import view.ShopWindow;


public class ShopButtonListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        //when you push whatever button, update the text to show coins were taken out to buy the item
        if(e.getSource() == ShopWindow.weakPotionButton){
            if(MainWindow.coins >= 1){
                MainWindow.coins -= 1;
                MainWindow.scoreText.setText("Score: " + MainWindow.score + " || Coins: " + MainWindow.coins);
                ShopWindow.coinText.setText("Coins: " + MainWindow.coins);
            }
        }
        else if(e.getSource() == ShopWindow.mediumPotionButton){
            if(MainWindow.coins >= 2){
                MainWindow.coins -= 2;
                MainWindow.scoreText.setText("Score: " + MainWindow.score + " || Coins: " + MainWindow.coins);
                ShopWindow.coinText.setText("Coins: " + MainWindow.coins);
            }
        }
        if(e.getSource() == ShopWindow.strongPotionButton){
            if(MainWindow.coins >= 3){
                MainWindow.coins -= 3;
                MainWindow.scoreText.setText("Score: " + MainWindow.score + " || Coins: " + MainWindow.coins);
                ShopWindow.coinText.setText("Coins: " + MainWindow.coins);
            }
        }
    }
    
}
