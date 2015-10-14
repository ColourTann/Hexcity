package com.tann.hexcity.screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import game.util.Colours;
import game.util.Draw;
import game.util.TannFont;

public class MenuButton extends Actor{
	public MenuButton() {
		setSize(TannFont.font.getWidth("menu", false)+4, 8);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.earth);
		Draw.drawRectangle(batch, getX(), getY(), getWidth(), getHeight(), 1);
		TannFont.font.drawString(batch, getX()+2, getY()+2, "menu", false);
		super.draw(batch, parentAlpha);
	}

}
