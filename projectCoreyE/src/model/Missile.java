package model;
//Strategy Pattern: Context

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Missile extends GameFigure {

    private MissileAnimate animation;
    
//    // missile size
    private static final int SIZE = 5;
    
    public Color color;
    private boolean exploded = false;
    private int size = SIZE;

    /*
     *
     * @param sx start x of the missile
     * @param sy start y of the missile
     * @param tx target x of the missile
     * @param ty target y of the missile
     * @param color color of the missile
     */  
      public Missile(float sx, float sy){
        super(sx, sy);
        super.state = STATE_ALIVE;
    }

    public void setAnimation(MissileAnimate animation){
        this.animation = animation;
    }
    
    @Override
    public void render(Graphics2D g) {
        this.animation.render(g, super.x, super.y, SIZE);
    }

    @Override
    public void update() {
        state = this.animation.updateState();
        if (state == STATE_ALIVE) {
            this.animation.updateLocation();
            super.x = animation.returnXLocation();
            super.y = animation.returnYLocation();
        } else if (state == STATE_DYING) {
            if(exploded == false){
                this.animation = new MissileExplosionAnimation(SIZE, super.x, super.y);
                exploded = true;
            }            
            this.animation.updateLocation();
            super.x = animation.returnXLocation();
            super.y = animation.returnYLocation();
            size += 2;
        }
    }

    @Override
    public Rectangle2D.Double getCollisionBox()
    {
        //get collisionbox of circular missle
        return new Rectangle2D.Double(super.x-size/2, super.y-size/2, size, size);
    }

    @Override
    public int getHitPoints() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addHitPoints() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void goNextState()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
