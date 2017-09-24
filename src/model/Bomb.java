package model;

import view.GamePanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Bomb extends GameFigure {

    private int radius;
    private final Color color;
    private int dx = 3;
    private int dy = 3;
    private boolean compressing;


    public Bomb(float x, float y, int radius, Color color) {
        super(x, y);
        super.state = GameFigureState.BOMB_STATE_ADDED;
        this.radius = radius;
        this.color = color;
        compressing = false;
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        // Note: use drawOval() to draw outline only
        g.fillOval((int)(x - radius), (int)(y - radius), 
                radius * 2, radius * 2);
    }

    @Override
    public void update() {
        if(super.state == GameFigureState.BOMB_STATE_ADDED)
        {
        // ball bounces on the wall
        super.x += dx;
        super.y += dy;
        
        
            if (super.x + radius > GamePanel.width) {
                dx = -dx;
                super.x = GamePanel.width - radius;
            } else if (super.x - radius < 0) {
                dx = -dx;
                super.x = radius;
            }

            if (super.y + radius > GamePanel.height) {
                dy = -dy;
                super.y = GamePanel.height - radius;
            } else if (super.y - radius < 0) {
                dy = -dy;
                super.y = radius;
            }
        }
        else if(super.state == GameFigureState.BOMB_STATE_DAMAGED)
        {
            if(dx < 0)
                super.x -=1;
            else
                super.x +=1;
            if(dy < 0)
                super.y -=1;
            else
                super.y +=1;
            if(compressing)
            {
                if(radius < 1)
                    super.state = GameFigureState.STATE_DONE;
               radius -= 1;
            }
            else
            {
                if(radius > 18)
                    compressing = true;
                radius += 1;
            }
        }
    }

    @Override
    public Rectangle2D getCollisionBox()
    {
        return new Rectangle2D.Float(super.x, super.y, radius, radius);
    }

    @Override
    public void shoot() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
