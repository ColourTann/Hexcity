package com.tann.hexcity.screens.titleScreen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.tann.hexcity.Main;

import game.util.Colours;
import game.util.Draw;
import game.util.TextRenderer;

public class AnnoyingFirstTimePopup extends Group{
	static final int width=82, height=50, gap=2;
	public AnnoyingFirstTimePopup() {
		
		TextRenderer tr = new TextRenderer("For your first time, I recommend playing a short game.[n][n]open the menu to find the rules.".toUpperCase(), width-gap*2);
		tr.setPosition(gap, gap);
		setSize(width, tr.getHeight()+gap*2);
		setPosition((int)(Main.width/2-getWidth()/2), (int)(Main.height/2-getHeight()/2));
		addActor(tr);
		addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Main.self.currentScreen.popActor();
				event.stop();
				return true;
			}
		});
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.dark);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		batch.setColor(Colours.grass);
		Draw.drawRectangle(batch, getX(), getY(), getWidth(), getHeight(), 1);
		batch.setColor(Colours.white);
		super.draw(batch, parentAlpha);
	}

}
