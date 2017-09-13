
package model;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import static model.GameFigure.STATE_ALIVE;
import view.GamePanel;

public class Boss extends GameFigure {    
    private BossState thisBoss;
    
    private final int WIDTH = 200;
    private final int HEIGHT = 150;
    private final int UNIT_TRAVEL = 1; // per frame    
    public int hitPoints = 0;    
    private int direction = 1; // +1: to the right; -1 to the left
    
    public Boss(float x, float y) {
        super(x, y);
        thisBoss = new BossStateCreated();
        super.state = STATE_ALIVE;
        hitPoints = 1;
    }
    
    public void setState(BossState state){
        thisBoss = state;
    }
    
    @Override
    public void goNextState()
    {
        thisBoss.nextState(this);
    }
    
    @Override
    public void render(Graphics2D g) {
        thisBoss.render(g,x,y);    
    }

    @Override
    public void update() {
        if (direction > 0) {
            // moving to the right
            super.x += UNIT_TRAVEL;
            if (super.x + WIDTH/2 > GamePanel.width) {
                direction = -1;
                super.y += 50;
            }
        } else {
            // moving to the left
            
            super.x -= UNIT_TRAVEL;
            if (super.x - WIDTH/2 <= 0) {
                direction = 1;
                super.y += 50;
            }
        }
    }

    @Override
    public int getHitPoints() {
        return this.hitPoints;
    }
    
    @Override
    public void addHitPoints() {
        this.hitPoints++;
    }

    
    @Override
    public Rectangle2D.Double getCollisionBox()
    {
        //get collisionbox of ovular Boss
        return new Rectangle2D.Double((int)(super.x -WIDTH/2), (int)(super.y - HEIGHT/2), WIDTH, HEIGHT);
    }



    
}
