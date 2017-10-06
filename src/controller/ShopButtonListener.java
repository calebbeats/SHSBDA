package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.MediumPotion;
import model.Shooter;
import model.StrongPotion;
import model.WeakPotion;
import view.MainWindow;
import view.ShopWindow;
import static view.ShopWindow.mediumPotionButton;
import static view.ShopWindow.strongPotionButton;
import static view.ShopWindow.weakPotionButton;


public class ShopButtonListener implements ActionListener{

    
    
    private void checkInvo(){
        int invoAmount = 4;
        //get character invo count and disable buttons as needed.
        for(int i = 0; i<4; i++){            
            if(Shooter.inventory[i] == null)
                invoAmount--;
        }
        if(invoAmount == 4){
            weakPotionButton.setEnabled(false);
            mediumPotionButton.setEnabled(false);
            strongPotionButton.setEnabled(false);
        }
        else{
            weakPotionButton.setEnabled(true);
            mediumPotionButton.setEnabled(true);
            strongPotionButton.setEnabled(true);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //check to see if there's enough space to allow buying of powerups
        checkInvo();

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
                for(int i=0;i<4;i++)
                {
                    if(Shooter.inventory[i] == null)
                    {
                        Shooter.inventory[i] = new MediumPotion(2);
                        i = 4;
                    }
                }
            }
        }
        if(e.getSource() == ShopWindow.strongPotionButton){
            if(MainWindow.coins >= 3){
                MainWindow.coins -= 3;
                MainWindow.scoreText.setText("Score: " + MainWindow.score + " || Coins: " + MainWindow.coins);
                ShopWindow.coinText.setText("Coins: " + MainWindow.coins);                
                for(int i=0;i<4;i++)
                {
                    if(Shooter.inventory[i] == null)
                    {
                        Shooter.inventory[i] = new StrongPotion(3);
                        i = 4;
                    }
                }
            }
        }
        //check again to disable buttons if there's no longer any room for power ups
        checkInvo();
    }
    
}
