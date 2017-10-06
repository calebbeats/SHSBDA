package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import model.Melee;
import model.Missile;
import model.Shooter;

public class MouseController extends MouseAdapter {

    private int px;
    private int py;

    Shooter shooter = (Shooter) Main.gameData.friendFigures.get(0);

    @Override
    public void mousePressed(MouseEvent me) {
        px = me.getX();
        py = me.getY();

        if (!Main.isPaused) {//if game is paused don't do anything after unpausing
            if (me.getButton() == MouseEvent.BUTTON1 && !shooter.isSprint()) {//Left click detected, initiate melee attack
                //melee attack in the direction the mouse is in regards to the player
                Melee m = new Melee(
                        shooter.getXofMissileShoot(),
                        shooter.getYofMissileShoot(),
                        px, py // direction in which to melee attack
                );
                Main.gameData.friendFigures.add(m);
            }

            if (me.getButton() == MouseEvent.BUTTON3 && !shooter.isSprint()) { //Right click detected, initiate ranged attack
                //shoot a missle at the mouse press location
                Missile m = new Missile(
                        shooter.getXofMissileShoot(),
                        shooter.getYofMissileShoot(),
                        px, py // target location where the missile explodes
                );
                Main.gameData.friendFigures.add(m);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        shooter.setMouseMovedEvent(e);        
    }
}
