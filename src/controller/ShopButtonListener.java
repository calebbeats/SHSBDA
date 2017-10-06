package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.MediumPotion;
import model.Shooter;
import static model.Shooter.inventory;
import model.StrongPotion;
import model.WeakPotion;
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
                for(int i=0;i<4;i++)
                {
                    if(Shooter.inventory[i] == null)
                    {
                        Shooter.inventory[i] = new WeakPotion(1);
                        i = 4;
                    }
                }
            }
        }
        else if(e.getSource() == ShopWindow.mediumPotionButton){
            if(MainWindow.coins >= 2){
                MainWindow.coins -= 2;
                MainWindow.scoreText.setText("Score: " + MainWindow.score + " || Coins: " + MainWindow.coins);
                ShopWindow.coinText.setText("Coins: " + MainWindow.coins);
                Shooter.inventory[1] = new MediumPotion(2);
            }
        }
        if(e.getSource() == ShopWindow.strongPotionButton){
            if(MainWindow.coins >= 3){
                MainWindow.coins -= 3;
                MainWindow.scoreText.setText("Score: " + MainWindow.score + " || Coins: " + MainWindow.coins);
                ShopWindow.coinText.setText("Coins: " + MainWindow.coins);
                Shooter.inventory[2] = new StrongPotion(3);
            }
        }
    }
    
}
