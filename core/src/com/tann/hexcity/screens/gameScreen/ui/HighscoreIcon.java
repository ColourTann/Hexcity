package com.tann.hexcity.screens.gameScreen.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.tann.hexcity.Main;
import com.tann.hexcity.screens.gameScreen.GameScreen.GameType;
import com.tann.hexcity.screens.menu.trophy.TrophyIcon;

import game.util.Colours;
import game.util.Draw;

public class HighscoreIcon extends Actor{
	public static TextureRegion highscore = Main.atlas.findRegion("ui/highscore");
	public HighscoreIcon(final GameType type, final int oldScore, final int newScore) {
		setSize(TrophyIcon.iconWidth, TrophyIcon.iconHeight);
		addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Main.self.currentScreen.pushActor(new HighscoreDescription(type, oldScore, newScore));
				event.stop();
				return false;
			}
		});
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.dark);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		batch.setColor(Colours.straw);
		Draw.drawRectangle(batch, getX(), getY(), getWidth(), getHeight(), 1);
		batch.setColor(Colours.white);
		Draw.draw(batch, highscore, getX()+TrophyIcon.gap, getY()+TrophyIcon.gap);
		super.draw(batch, parentAlpha);
	}

}
