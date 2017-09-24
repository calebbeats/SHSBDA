package model;

import java.awt.Graphics2D;

public abstract class GameFigure implements CollisionBox{
 // public for a faster access during animation
    public float x;
    public float y;
    
    public int state;
    public FigureState fState;
    
    public static final int STATE_ALIVE = 1;
    public static final int STATE_DYING = 2;
    public static final int STATE_DONE = 0;
    public int shootTimer = 0;

    public GameFigure(float x, float y) {
        this.x = x;
        this.y = y;
        this.fState = new FigureAlive(this);
    }
    
    public void goNextState() {
        fState.goNext(this);
    }
   
    public void setState(FigureState state) {
        this.fState = state;
    }
    
    
    
    // how to render on the canvas
    public abstract void render(Graphics2D g);
    
    public abstract void shoot();
    // changes per frame
    public abstract void update();

    

    

}
