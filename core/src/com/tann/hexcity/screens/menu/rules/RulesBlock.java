package com.tann.hexcity.screens.menu.rules;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.tann.hexcity.Main;
import com.tann.hexcity.screens.menu.MenuPanel;

import game.util.Colours;
import game.util.Draw;
import game.util.Sounds;
import game.util.TextRenderer;
import game.util.Sounds.SoundType;

public class RulesBlock extends Actor{
	TextRenderer tr;
	int gap=2;
	String[] text;
	int index=0;
	int wrapWidth=78;
	int rulesX, rulesY, rulesWidth, rulesHeight;
	
	
	
	public RulesBlock(String text) {
		setup(new String[]{text}, wrapWidth);
	}
	
	public RulesBlock(String[] text) {
		setup(text, wrapWidth);
	}
	
	public void setup(String[] text, int wrapWidth){
		setSize(Main.width, Main.height);
		this.text=text;
		this.wrapWidth=wrapWidth;
		next();
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				next();
				Sounds.playSound(SoundType.PopMenu);
				event.stop();
				return true;
			}
		});
	}
	
	public void next(){
		if(index==text.length){
			cancel();
			return;
		}
		tr = new TextRenderer(text[index], wrapWidth);
		rulesWidth=(int) (tr.getWidth()+gap*2);
		rulesHeight=(int) (tr.getHeight()+gap*2);
		rulesX=Main.width/2-rulesWidth/2;
		rulesY=Main.height/2-rulesHeight/2;
		
		index++;
	}
	
	public void cancel(){
		index=0;
		Main.self.currentScreen.popActor();
	}

	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.setColor(Colours.grass);
		Draw.fillRectangle(batch, (int)getX()+rulesX, (int)getY()+rulesY, rulesWidth, rulesHeight);
		batch.setColor(Colours.dark);
		Draw.fillRectangle(batch, (int)getX()+rulesX+1, (int)getY()+rulesY+1, rulesWidth-2, rulesHeight-2);
		batch.setColor(Colours.white);
		tr.setPosition((int)getX()+rulesX+gap, (int)getY()+rulesY+gap);
		tr.draw(batch, parentAlpha);
	}
}
