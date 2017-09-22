package controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import model.Missile;
import model.Shooter;

public class MouseController extends MouseAdapter {

    private int px;
    private int py;

    @Override
    public void mousePressed(MouseEvent me) {
        px = me.getX();
        py = me.getY();

        Shooter shooter = (Shooter) Main.gameData.friendFigures.get(0);

        Missile m = new Missile(
                shooter.getXofMissileShoot(),
                shooter.getYofMissileShoot(),
                px, py, Color.RED // target location where the missile explodes
                );

                synchronized (Main.gameData.friendFigures) {
            Main.gameData.friendFigures.add(m);
        }

    }

}
