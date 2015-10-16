package com.tann.hexcity.screens.menu.trophy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.tann.hexcity.Main;

import game.util.Colours;
import game.util.Draw;

public class AchievementIcon extends Actor{
	Achievement a;
	public AchievementIcon(final Achievement a) {
		this.a=a;
		setSize(a.getTexture().getRegionWidth()+4, a.getTexture().getRegionHeight()+4);
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Main.self.currentScreen.pushActor(new AchievementDescription(a));
				event.stop();
				return false;
			}
		});
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.earth);
		Draw.drawRectangle(batch, getX(), getY(), getWidth(), getHeight(), 1);
		batch.setColor(Colours.white);
		Draw.draw(batch, a.getTexture(), getX()+2, getY()+2);
		super.draw(batch, parentAlpha);
	} 
}
