package com.tann.hexcity.screens.menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.tann.hexcity.Main;

import game.util.Colours;
import game.util.Draw;
import game.util.TextRenderer;

public class RulesBlock extends Actor{
	TextRenderer tr;
	int gap=2;
	String[] text;
	int index=0;
	int wrapWidth;
	public RulesBlock(String[] text, int wrapWidth) {
		this.text=text;
		this.wrapWidth=wrapWidth;
		next();
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				next();
				return false;
			}
		});
	}
	
	public void next(){
		if(index==text.length){
			remove();
			return;
		}
		tr = new TextRenderer(text[index], wrapWidth);
		setSize(tr.getWidth()+gap*2, tr.getHeight()+gap*2);
		setPosition(Main.width/2-getWidth()/2, Main.height/2-getHeight()/2);
		index++;
	}

	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.setColor(Colours.grass);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		batch.setColor(Colours.dark);
		Draw.fillRectangle(batch, getX()+1, getY()+1, getWidth()-2, getHeight()-2);
		batch.setColor(Colours.white);
		tr.setPosition(getX()+gap, getY()+gap);
		tr.draw(batch, parentAlpha);
	}
}
