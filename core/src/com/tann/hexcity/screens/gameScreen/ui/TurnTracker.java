package com.tann.hexcity.screens.gameScreen.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.hexcity.screens.gameScreen.GameScreen;

import game.util.Colours;
import game.util.Draw;

public class TurnTracker extends Actor{
	int blipSize=3;
	int blipXGap=4;
	int blipYGap=4;
	int turns=10;
	int turnsTaken=0;
	public TurnTracker(int turns) {
		this.turns=turns;
		setSize(blipXGap*5+blipSize, blipYGap*4+blipSize);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		int turnNumber=0;
		for(int y=0;turnNumber<turns;y++){
			for(int x=0;x<5;x++){
				batch.setColor(turnsTaken>turnNumber?Colours.straw:Colours.earth);
				Draw.fillRectangle(batch, getX()+x*blipXGap, getY()+getHeight()-blipSize-y*blipYGap, blipSize, blipSize);
				turnNumber++;
			}
		}
		
		batch.setColor(Colours.grass);
//		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		super.draw(batch, parentAlpha);
	}

	public void incrementTurns() {
		turnsTaken ++;
		if(turnsTaken==turns){
			GameScreen.self.outOfTurns();
		}
	}

	public void reset() {
		turnsTaken=0;
	}
}