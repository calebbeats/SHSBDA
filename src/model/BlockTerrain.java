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

/**
 *
 * @author Haseeb
 */
public class BlockTerrain extends GameFigure {

    public static final int TERRAIN_WIDTH = 50, TERRAIN_HEIGHT = 50;
    
    private Image blockImage;

    public BlockTerrain(float x, float y) {
        super(x, y);
        super.state = STATE_ALIVE;
        try {
            blockImage = ImageIO.read(getClass().getResource("BlockTerrain.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open BlockTerrain.png");
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
                    TERRAIN_WIDTH, TERRAIN_HEIGHT, null);
        }
    }

    @Override
    public void shoot() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(this.x, this.y, TERRAIN_WIDTH, TERRAIN_HEIGHT);
    }
}
