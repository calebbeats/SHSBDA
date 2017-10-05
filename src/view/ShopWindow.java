/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ShopButtonListener;
import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class ShopWindow extends JFrame{

    public static JButton itemButton[];
    public static JButton testButton;
    
    public ShopWindow() {
        Container c = getContentPane();
        
        JPanel centerPanel = new JPanel();
        
//        itemButton[0] = new JButton("Item");
        testButton = new JButton("Item");
//        centerPanel.add(itemButton[0]);
        centerPanel.add(testButton);
        
        c.add(centerPanel, "Center");
        
        ShopButtonListener buttonListener = new ShopButtonListener();
        
        testButton.addActionListener(buttonListener);
//        itemButton[0].setFocusable(false);
        testButton.setFocusable(false);
    }
    
}
