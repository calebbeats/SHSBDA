/*
    Strategy Pattern: Concrete Strategy
 */
package model;

import java.awt.Color;
import java.awt.Graphics2D;

public class MissileExplosionAnimation implements MissileAnimate{

    private int size;
    private final float X;
    private final float Y;
    private final int MAX_SIZE = 50;
    
    public MissileExplosionAnimation(int size, float x, float y){
        this.X = x;
        this.Y = y;
        this.size = size;
    }
    
    @Override
    public void updateLocation() {        
        this.size += 2;
    }

    @Override
    public int updateState() {
        if(this.size >= MAX_SIZE){
            return 0;
        }
        return 2;
    }

    @Override
    public void render(Graphics2D g, float x, float y, int size)
    {
        g.setColor(Color.RED);
        g.drawOval((int) (this.X - this.size / 2),
                (int) (this.Y - this.size / 2),
                this.size, this.size);
    }

    @Override
    public float returnXLocation()
    {
        return this.X;
    }

    @Override
    public float returnYLocation()
    {
        return this.Y;
    }    
}
