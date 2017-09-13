/*
    State Pattern: State
 */
package model;

import java.awt.Graphics2D;

public interface SatelliteState {
    void nextState(Satellite context);
    
    void render(Graphics2D g, float x, float y);
}
