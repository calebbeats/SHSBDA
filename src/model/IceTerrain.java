/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import static model.GameFigure.STATE_ALIVE;

/**
 *
 * @author Haseeb
 */
public class IceTerrain extends GameFigure {

    public int terrainWidth = 50, terrainHeight = 50;
    
    private Image blockImage;

    public IceTerrain(float x, float y, int width, int height) {
        super(x, y);
        this.terrainWidth = width;
        this.terrainHeight = height;
        super.state = STATE_ALIVE;
        try {
            blockImage = ImageIO.read(getClass().getResource("/resources/IceTerrain.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open IceTerrain.png");
            System.exit(-1);
        }
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void render(Graphics2D g) {
        if (state == STATE_ALIVE) {
            g.drawImage(blockImage, (int) super.x, (int) super.y,
                    terrainWidth, terrainHeight, null);
        }
    }

    @Override
    public void shoot() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(this.x, this.y, terrainWidth, terrainHeight);
    }
}
