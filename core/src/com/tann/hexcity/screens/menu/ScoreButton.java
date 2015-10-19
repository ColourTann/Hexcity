package com.tann.hexcity.screens.menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.tann.hexcity.Main;

import game.util.Colours;
import game.util.Draw;
import game.util.Sounds;
import game.util.Sounds.SoundType;

public class ScoreButton extends Actor{
	static TextureRegion scoreTexture = Main.atlas.findRegion("ui/score");
	int gap=2;
	public ScoreButton() {
		setSize(scoreTexture.getRegionWidth()+gap*2, scoreTexture.getRegionHeight()+gap*2);
		addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Main.self.currentScreen.pushActor(ScorePanel.get());
				Sounds.playSound(SoundType.PushMenu);
				event.cancel();
				return false;
			}
		});
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.earth);
		Draw.drawRectangle(batch, getX(), getY(), getWidth(), getHeight(), 1);
		batch.setColor(Colours.white);
		Draw.draw(batch, scoreTexture, getX()+gap, getY()+gap);
		
		super.draw(batch, parentAlpha);
	}
}
