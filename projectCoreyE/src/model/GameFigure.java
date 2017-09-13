package model;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public abstract class GameFigure implements CollisionBox{

    // public for a faster access during animation
    public float x;
    public float y;
    
    public int state;
    public static final int STATE_ALIVE = 1;
    public static final int STATE_DYING = 2;
    public static final int STATE_DONE = 0;

    public GameFigure(float x, float y) {
        this.x = x;
        this.y = y;
    }
    //goes to next state when figure is hit
    public abstract void goNextState();    
    
    // how to render on the canvas
    public abstract void render(Graphics2D g);

    // changes per frame
    public abstract void update();
    
    //returns the hitpoint number
    public abstract int getHitPoints();
    
    public abstract void addHitPoints();
    
    @Override
    public abstract Rectangle2D.Double getCollisionBox();

}
