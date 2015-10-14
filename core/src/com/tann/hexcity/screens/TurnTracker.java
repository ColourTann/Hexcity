package com.tann.hexcity.screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import game.util.Colours;
import game.util.Draw;
import game.util.TannFont;

public class TurnTracker extends Actor{
	int blipSize=3;
	int blipXGap=4;
	int blipYGap=4;
	public TurnTracker() {
		setSize(blipXGap*5+blipSize, blipYGap+blipSize+TannFont.font.getHeight()+2);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.earth);
		for(int y=0;y<2;y++){
			for(int x=0;x<5;x++){
				Draw.fillRectangle(batch, getX()+x*blipXGap, getY()+y*blipYGap, blipSize, blipSize);
			}
		}
		super.draw(batch, parentAlpha);
	}
}
