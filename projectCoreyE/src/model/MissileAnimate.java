// Strategy Pattern: Strategy
package model;

import java.awt.Color;
import java.awt.Graphics2D;


public interface MissileAnimate {
    void updateLocation();
    int updateState();
    void render(Graphics2D g, float x, float y, int size);
    float returnXLocation();
    float returnYLocation();
}
