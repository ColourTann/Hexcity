package com.tann.hexcity.screens.titleScreen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.tann.hexcity.Main;
import com.tann.hexcity.screens.gameScreen.GameScreen.GameType;

import game.util.Colours;
import game.util.Draw;
import game.util.Sounds;
import game.util.TannFont;
import game.util.Sounds.SoundType;

public class StartGameButton extends Actor{
	static final int buttWidth=39;
	GameType type;
	public StartGameButton(GameType type) {
		this.type=type;
		setSize(buttWidth, TannFont.font.getHeight()+4);
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				TitleScreen.self.startGame(StartGameButton.this.type);
				Sounds.playSound(SoundType.PushMenu);
				return false;
			}
			
		});
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.earth);
		Draw.drawRectangle(batch, getX(), getY(), getWidth(), getHeight(), 1);
		batch.setColor(Colours.earth);
		TannFont.font.drawString(batch, (type.description.toUpperCase()), (int)(Math.ceil(getX()+ getWidth()/2f-TannFont.font.getWidth(type.description.toUpperCase())/2f)), (int)getY()+2, false);
	}
}
