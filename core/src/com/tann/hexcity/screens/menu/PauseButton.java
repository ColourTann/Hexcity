package com.tann.hexcity.screens.menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import game.util.Colours;
import game.util.Draw;
import game.util.TannFont;

public class PauseButton extends Actor{
	public PauseButton() {
		setSize(TannFont.font.getWidth("MENU", false)+4, 9);
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				MenuPanel.show();
				return false;
			}
		});
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.earth);
		Draw.drawRectangle(batch, (int)getX(), (int)getY(), (int)getWidth(), (int)getHeight(), 1);
		TannFont.font.drawString(batch, "MENU", (int)(getX()+2), (int)getY()+2, false);
		super.draw(batch, parentAlpha);
	}

}
