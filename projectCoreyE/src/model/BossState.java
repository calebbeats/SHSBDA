/*
    State pattern: State
 */
package model;

import java.awt.Graphics2D;


public interface BossState {
    void nextState(Boss context);
    
    void render(Graphics2D g, float x, float y);
}
