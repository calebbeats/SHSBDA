/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ShopButtonListener;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.Shooter;


public class ShopWindow extends JFrame{

    public static JTextField coinText;
    public static JButton weakPotionButton;
    public static JButton mediumPotionButton;
    public static JButton strongPotionButton;
    
    public ShopWindow() {
        Container c = getContentPane();
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3,2));
        
        weakPotionButton = new JButton("Weak Potion: 1 Coin");
        mediumPotionButton = new JButton("Medium Potion: 2 Coins");
        strongPotionButton = new JButton("Strong Potion: 3 Coins");

        centerPanel.add(weakPotionButton);
        centerPanel.add(mediumPotionButton);
        centerPanel.add(strongPotionButton);
        
        coinText = new JTextField("Coins: " + MainWindow.coins);
        c.add(centerPanel, "Center");
        c.add(coinText, "North");
        
        ShopButtonListener buttonListener = new ShopButtonListener();
        
        weakPotionButton.addActionListener(buttonListener);
        mediumPotionButton.addActionListener(buttonListener);
        strongPotionButton.addActionListener(buttonListener);
        
        weakPotionButton.setFocusable(false);
        mediumPotionButton.setFocusable(false);
        strongPotionButton.setFocusable(false);
        coinText.setFocusable(false);
        
        coinText.setEditable(false);
        

    }
    
}
