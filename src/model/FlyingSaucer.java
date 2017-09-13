
package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import view.GamePanel;

public class FlyingSaucer extends GameFigure {
    
    private boolean spiral;
    private final int WIDTH = 40;
    private final int HEIGHT = 10;
    private final Color color = Color.yellow;
    private final int UNIT_TRAVEL = 5; // per frame
    private int counter;
    
    private int direction = 1; // +1: to the right; -1 to the left
    
    public FlyingSaucer(float x, float y) {
        super(x, y);
        super.state = GameFigureState.UFO_STATE_APPEARED;
        spiral = true;
        counter = 0;
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        g.fillOval((int)(super.x - WIDTH/2), 
                (int)(super.y - HEIGHT/2), 
                WIDTH, HEIGHT);
    }

    @Override
    public void update() {
        if(super.state == GameFigureState.UFO_STATE_APPEARED)
        {
            if (direction > 0) {
                // moving to the right
                super.x += UNIT_TRAVEL;
                if (super.x + WIDTH/2 > GamePanel.width) {
                    direction = -1;
                }
            } else {
                // moving to the left
                super.x -= UNIT_TRAVEL;
                if (super.x - WIDTH/2 <= 0) {
                    direction = 1;
                }
            }
        }
        else if(super.state == GameFigureState.UFO_STATE_DAMAGED)
        {
            if(super.y + HEIGHT/2 > GamePanel.height)
            {
                super.state = GameFigureState.STATE_DONE;
            }
            super.y += 5;
            if(spiral)
            {
                super.x += 5;
                counter++;
                if(counter > 10)
                {
                    counter = 0;
                    spiral = false;
                }
            }
            else
            {
                super.x -= 5;
                counter++;
                if(counter > 10)
                {
                    counter = 0;
                    spiral = true;
                }
            }
        }
    }

    @Override
    public Rectangle2D getCollisionBox()
    {
       return new Rectangle2D.Float((super.x - WIDTH/2),
               (super.y - HEIGHT/2),
               WIDTH, HEIGHT);
    }

    @Override
    public void shoot() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
