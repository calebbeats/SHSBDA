/*
    State pattern: State
*/
package model;

import java.awt.Graphics2D;

public interface AsteroidState
{
    void nextState(Asteroid context);
    
    void render(Graphics2D g, int radius, float x, float y);
}
