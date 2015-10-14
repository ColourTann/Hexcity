package com.tann.hexcity.screens;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.tann.hexcity.Main;

import game.util.Colours;
import game.util.Draw;
import game.util.TannFont;

public class ScoreKeeper extends Actor{
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
	
		batch.setColor(Colours.earth);
		String score ="";
		int num = 13;
		score=num+"";
		while(score.length()<3)score="0"+score;
		TannFont.font.drawString(batch, getX(), getY(), score, true);
		
		
		super.draw(batch, parentAlpha);
	}
}
