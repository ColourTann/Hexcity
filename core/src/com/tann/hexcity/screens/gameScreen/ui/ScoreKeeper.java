package com.tann.hexcity.screens.gameScreen.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.hexcity.Main;

import game.util.Colours;
import game.util.TannFont;

public class ScoreKeeper extends Actor{
	int score=0;
	boolean finished;
	@Override
	public void draw(Batch batch, float parentAlpha) {
	
		batch.setColor(Colours.earth);
		if(finished){
			float freq=6;
			batch.setColor(Math.sin(Main.ticks*freq)>0?Colours.earth:Colours.straw);
		}
		String scoreString ="";
		scoreString=score+"";
		while(scoreString.length()<3)scoreString="0"+scoreString;
		TannFont.font.drawString(batch, scoreString, (int)getX(), (int)getY(), true);
		
		
		super.draw(batch, parentAlpha);
	}

	public void addPoints(int points) {
		score+=points;
	}

	public void reset() {
		score=0;
		finished=false;
	}
	
	public void finished(){
		finished=true;		
	}
}
