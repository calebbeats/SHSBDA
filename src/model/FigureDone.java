/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.GameFigure;
import model.GameFigure;
import model.GameFigure;

/**
 *
 * @author caleb
 */
public class FigureDone implements FigureState{
    
  public FigureDone(GameFigure context) {
		context.state = 0;
	}

  @Override
	public void goNext(GameFigure context) {
		context.setState(null); // end state
	}
 
}

