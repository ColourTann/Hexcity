package com.tann.hexcity.screens.menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.tann.hexcity.Main;
import com.tann.hexcity.screens.menu.trophy.TrophyPanel;

import game.util.Colours;
import game.util.Draw;
import game.util.Sounds;
import game.util.Sounds.SoundType;

public class TrophyButton extends Actor{
	
	static TextureRegion trophy = Main.atlas.findRegion("ui/trophy");
	static final int gap=2;
	public TrophyButton() {
		setSize(trophy.getRegionWidth()+gap*2, trophy.getRegionHeight()+gap*2);
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Main.self.currentScreen.pushActor(TrophyPanel.get());
				Sounds.playSound(SoundType.PushMenu);
				event.stop();
				return true;
			}

		});
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.earth);
		Draw.drawRectangle(batch, getX(), getY(), getWidth(), getHeight(), 1);
		batch.setColor(Colours.white);
		Draw.draw(batch, trophy, getX()+gap, getY()+gap);
		super.draw(batch, parentAlpha);
	}

}
