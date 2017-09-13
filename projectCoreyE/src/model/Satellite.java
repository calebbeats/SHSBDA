
package model;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import view.GamePanel;

public class Satellite extends GameFigure {
    
    private SatelliteState thisSatellite;
    
    private final int WIDTH = 40;
    private final int HEIGHT = 10;
    private final int UNIT_TRAVEL = 5; // per frame    
    
    private int direction = 1; // +1: to the right; -1 to the left
    
    public Satellite(float x, float y) {
        super(x, y);
        thisSatellite = new SatelliteStateCreated();
        super.state = STATE_ALIVE;
    }

    @Override
    public void goNextState(){
        thisSatellite.nextState(this);
    }
    
    public void setState(SatelliteState state){
        thisSatellite = state;
    }
    
    @Override
    public void render(Graphics2D g) {
        thisSatellite.render(g, x, y);
    }

    @Override
    public void update() {
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
    
    @Override
    public int getHitPoints(){
        return 0;
    }

    @Override
    public Rectangle2D.Double getCollisionBox()
    {
        //get collisionbox of ovular saucer
        return new Rectangle2D.Double(x - 20, y - 5, 50, 50);
    }

    @Override
    public void addHitPoints() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
