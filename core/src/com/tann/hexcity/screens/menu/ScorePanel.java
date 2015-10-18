package com.tann.hexcity.screens.menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;
import com.tann.hexcity.Main;
import com.tann.hexcity.screens.gameScreen.GameScreen.GameType;

import game.util.Colours;
import game.util.Draw;
import game.util.TannFont;

public class ScorePanel extends Actor{
	
	static final int textTypeX=3, textScoreX=46, textY=3, textGap=8;
	static final int scorePanelWidth = 60, scorePanelHeight = textY*2+textGap*4+TannFont.font.getHeight();
	private static ScorePanel self;
	public static ScorePanel get() {
		if(self==null) self = new ScorePanel();
		return self;
	}
	
	private ScorePanel() {
		setSize(scorePanelWidth, scorePanelHeight);
		setPosition((int)(Main.width/2-getWidth()/2), (int)(Main.height/2-getHeight()/2));
		addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Main.self.currentScreen.popActor();
				event.cancel();
				return false;
			}
		});
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.dark);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		batch.setColor(Colours.grass);
		Draw.drawRectangle(batch, getX(), getY(), getWidth(), getHeight(), 1);
		batch.setColor(Colours.earth);
		for(int i=0;i<GameType.values().length;i++){
			GameType type = GameType.values()[3-i];
			String typeString = type.description.toUpperCase()+":";
			String scoreString = Main.saveData.getHighScore(type)+"";
			TannFont.font.drawString(batch, typeString, (int)getX()+textTypeX, (int)getY()+textY+textGap*i, false);
			TannFont.font.drawString(batch, scoreString, (int)getX()+textScoreX, (int)getY()+textY+textGap*i, false);
		}
		TannFont.font.drawString(batch, "HIGHSCORES", (int)(getX()+getWidth()/2), (int)getY()+textY+textGap*4, false, Align.center);
		super.draw(batch, parentAlpha);
	}
}
