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
	public static final int gridX=22,gridY=3;
	public static final int pickerX=3, pickerY=3;
	public static final int scoreX=2, scoreY=Main.height-4;
	Grid grid = new Grid();
	ScoreKeeper score = new ScoreKeeper();
	TilePicker picker = new TilePicker();
	public MainScreen() {
		addActor(grid);
		addActor(score);
		addActor(picker);
		score.setPosition(scoreX, scoreY);
		grid.setPosition(gridX, gridY-grid.getHeight());
		picker.setPosition(pickerX, pickerY);
	}
	
	@Override
	public void preDraw(Batch batch) {
	}

	
	@Override
	public void postDraw(Batch batch) {
		batch.setColor(Colours.white);
		
	
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
