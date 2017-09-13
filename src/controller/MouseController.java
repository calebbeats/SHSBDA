package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import model.Shooter;


public class MouseController extends MouseAdapter {

    private int px;
    private int py;


    @Override
    public void mousePressed(MouseEvent me) {
        px = me.getX();
        py = me.getY();

        
                
        if(Main.gameData.inGame2 == true)
        {
            Shooter shooter = (Shooter) Main.gameData.friendFigures.get(0);
            shooter.weapon.shoot(shooter,px,py);
        }
        else
        {
            Main.gameData.startGame();
        }
       
        
        
        

        
    }

}
