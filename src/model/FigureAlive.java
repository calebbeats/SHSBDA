/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author caleb
 */
public class FigureAlive implements FigureState {
       
        public FigureAlive(GameFigure context) {
		context.state = 1;
	}

        @Override
	public void goNext(GameFigure context) {
		context.setState(new FigureDying(context)); // end state
	}
    
}
