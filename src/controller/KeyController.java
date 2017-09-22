package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import model.GameFigure;
import model.Shooter;

public class KeyController implements KeyListener {

    @Override
    public void keyPressed(KeyEvent e) {
        Shooter shooter = (Shooter) Main.gameData.friendFigures.get(0);
        //used to see if shooter will colide with terrain before moving
        Shooter shooterIntededPosition = new Shooter((int)shooter.x,(int)shooter.y);
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                shooterIntededPosition.translate(-10, 0);
                for(GameFigure t: Main.gameData.terrainFigures){
                    if(!shooterIntededPosition.getCollisionBox().intersects(t.getCollisionBox())){
                        shooter.translate(-10, 0);
                    }
                }                
                break;
            case KeyEvent.VK_RIGHT:
                shooterIntededPosition.translate(10, 0);
                for(GameFigure t: Main.gameData.terrainFigures){
                    if(!shooterIntededPosition.getCollisionBox().intersects(t.getCollisionBox())){
                        shooter.translate(10, 0);
                    }
                }         
                break;
            case KeyEvent.VK_UP:
                shooterIntededPosition.translate(0, -10);
                for(GameFigure t: Main.gameData.terrainFigures){
                    if(!shooterIntededPosition.getCollisionBox().intersects(t.getCollisionBox())){
                        shooter.translate(0, -10);
                    }
                }     
                break;
            case KeyEvent.VK_DOWN:
                shooterIntededPosition.translate(0, 10);
                for(GameFigure t: Main.gameData.terrainFigures){
                    if(!shooterIntededPosition.getCollisionBox().intersects(t.getCollisionBox())){
                        shooter.translate(0, 10);
                    }
                }                
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
