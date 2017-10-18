/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.BlinkMage;
import model.GameData;
import model.MeleeEnemy;
import model.SlowMage;
import model.SuicideEnemy;
import view.DevWindow;

/**
 *
 * @author Kodo
 */
public class SpawnButtonListener implements ActionListener {
 
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == DevWindow.melee){
            }
            if(e.getSource() == DevWindow.suicide){

            }
            if(e.getSource() == DevWindow.bmage){

            }
            if(e.getSource() == DevWindow.smage){

            }
            if(e.getSource() == DevWindow.close){
                KeyController.dev.dispose();
            }

        }

}
