package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.ShopWindow;


public class ShopButtonListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == ShopWindow.testButton){
            System.out.println("Item");
        }
    }
    
}
