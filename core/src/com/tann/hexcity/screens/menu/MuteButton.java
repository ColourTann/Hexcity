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

public class MuteButton extends Actor{
	static TextureRegion sound = Main.atlas.findRegion("ui/sound");
	static TextureRegion mute = Main.atlas.findRegion("ui/mute");
	int gap =2;
	public MuteButton() {
		setSize(sound.getRegionWidth()+gap*2, sound.getRegionHeight()+gap*2);
		addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Main.saveData.toggleMute();
				Sounds.playSound(SoundType.PlayTile);
				return false;
			}
		});
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.earth);
		Draw.drawRectangle(batch, getX(), getY(), getWidth(), getHeight(), 1);
		batch.setColor(Colours.white);
		TextureRegion tr = sound;
		if(Main.saveData.isMuted()){
			tr=mute;
		}
		Draw.draw(batch, tr, getX()+gap, getY()+gap);
		super.draw(batch, parentAlpha);
	}

}
