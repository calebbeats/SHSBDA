/*
    Strategy Pattern: Concrete Strategy
 */
package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public class MissileLaunchedAnimation implements MissileAnimate{
    
    
    private float dx; // displacement at each frame
    private float dy; // displacement at each frame
    
        // public properties for quick access
    public Color color;
    public Point2D.Float target;

    private static final int UNIT_TRAVEL_DISTANCE = 4; // per frame move

    private int size = 5;
    
    float x;
    float y;
    
    public MissileLaunchedAnimation(float sx, float sy, float tx, float ty, Color color){
        this.x = sx;
        this.y = sy;
        
        this.target = new Point2D.Float(tx, ty);
        this.color = color;
        double angle = Math.atan2(Math.abs(ty - sy), Math.abs(tx - sx));
        dx = (float) (UNIT_TRAVEL_DISTANCE * Math.cos(angle));
        dy = (float) (UNIT_TRAVEL_DISTANCE * Math.sin(angle));        

        
        if (tx > sx && ty < sy) { // target is upper-right side
            dy = -dy; // dx > 0, dx < 0
        } else if (tx < sx && ty < sy) { // target is upper-left side
            dx = -dx;
            dy = -dy;
        } else if (tx < sx && ty > sy) { // target is lower-left side
            dx = -dx;
        } else { // target is lower-right side
            // dx > 0 , dy > 0
        }
    }

    @Override
    public void updateLocation() {
       this.x += dx;
       this.y += dy;
    }

    @Override
    public int updateState() {
        double distance = target.distance(this.x, this.y);
        boolean targetReached = distance <= 2.0;
        if(targetReached){
            return 2;
        }
        return 1;
    }

    @Override
    public void render(Graphics2D g, float x, float y, int size) {
        g.setColor(this.color);
        g.drawOval((int) (this.x - size / 2),
                (int) (this.y - size / 2),
                size, size);
    }

    @Override
    public float returnXLocation()
    {
        return this.x;
    }

    @Override
    public float returnYLocation()
    {
        return this.y;
    }
    
}
