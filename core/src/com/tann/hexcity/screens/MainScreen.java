package com.tann.hexcity.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.tann.hexcity.Main;

import game.util.Colours;
import game.util.Screen;
import game.util.TannFont;

public class MainScreen extends Screen{
	TannFont font= new TannFont(Main.atlas.findRegion("font"));
	@Override
	public void preDraw(Batch batch) {
	}

	String[] strings = new String[]{"Twas bryllyg and the slythy toves", "Did gyre and gymble in the wabe:", "All mymsy were the borogroves,", "And the mome raths outgrabe"};
	@Override
	public void postDraw(Batch batch) {
		batch.setColor(Colours.straw);
		for(String s:strings)drawString(batch, s);
		y=50;
	}
	int y=60;
	public void drawString(Batch batch, String text){
		font.drawString(batch, 5, y, text);
		y-=7;
	}

	@Override
	public void preTick(float delta) {
	}

	@Override
	public void postTick(float delta) {
	}

}
