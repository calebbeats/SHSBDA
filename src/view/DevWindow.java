/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.SpawnButtonListener;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DevWindow extends JFrame   {
    
    public static JTextField console;
    public static JButton melee;
    public static JButton suicide; 
    public static JButton bmage;
    public static JButton smage;
    public static JButton close;

    public DevWindow() {
        Container c = getContentPane();
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(5,1));
        
        melee = new JButton("Spawn Melee");
        suicide = new JButton("Spawn Suicide");
        bmage = new JButton("Spawn Blink");
        smage = new JButton("Spawn Slow");
        close = new JButton("Close");

        centerPanel.add(melee);
        centerPanel.add(suicide);
        centerPanel.add(bmage);
        centerPanel.add(smage);
        centerPanel.add(close);
       
        c.add(centerPanel, "Center");
        
        SpawnButtonListener buttonListener = new SpawnButtonListener();
        
        melee.addActionListener(buttonListener);
        suicide.addActionListener(buttonListener);
        bmage.addActionListener(buttonListener);
        smage.addActionListener(buttonListener);
        close.addActionListener(buttonListener);

        melee.setFocusable(false);
        suicide.setFocusable(false);
        bmage.setFocusable(false);
        smage.setFocusable(false);    
    }
}