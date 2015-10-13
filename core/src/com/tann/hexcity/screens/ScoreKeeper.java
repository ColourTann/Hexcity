package com.tann.hexcity.screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import game.util.Colours;
import game.util.Draw;
import game.util.TannFont;

public class ScoreKeeper extends Actor{
	int blipSize=2;
	int blipXGap=3;
	int blipYGap=3;
	@Override
	public void draw(Batch batch, float parentAlpha) {
		setSize(blipXGap*5+blipSize, blipYGap+blipSize+TannFont.font.getHeight()+2);
		batch.setColor(Colours.earth);
		TannFont.font.drawString(batch, getX()+1, getY()-5, "000");
		
		for(int y=0;y<2;y++){
			for(int x=0;x<5;x++){
				Draw.fillRectangle(batch, getX()+x*blipXGap, getY()-TannFont.font.getHeight()-4-y*blipYGap, blipSize, blipSize);
			}
		}
		super.draw(batch, parentAlpha);
	}
}
