/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Hasee
 */
public class BasicCollisionBox extends GameFigure{

    private int height;
    private int width;
    
    public BasicCollisionBox(float x, float y, int height, int width) {
        super(x, y);
        this.height = height;
        this.width = width;
    }

    @Override
    public void render(Graphics2D g) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void shoot() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(this.x, this.y, height, width);
    }
    
}
