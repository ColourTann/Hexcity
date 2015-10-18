package com.tann.hexcity.screens.menu.trophy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.tann.hexcity.Main;
import com.tann.hexcity.savaData.Trophy;

import game.util.Colours;
import game.util.Draw;
import game.util.TannFont;
import game.util.TextRenderer;

public class TrophyDescription extends Group{
	Trophy a;
	TextRenderer tr;
	
	static final int gap=2;
	public TrophyDescription(Trophy a) {
		this.a=a;
		refresh();
		addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Main.self.currentScreen.popActor();
				event.stop();
				return false;
			}
		});
	}
	
	public void refresh(){
		if(tr!=null)tr.remove();
		String text = (" "+a.getName()+" ").toUpperCase();
		setWidth(a.getTexture().getRegionWidth()*2+TannFont.font.getWidth(text)+gap*2+2);
		tr= new TextRenderer(a.getRendererIconName()+text+a.getRendererIconName()+"[n]"+a.getDescription().toUpperCase(), (int)(getWidth()-gap*2));
		tr.setPosition(gap, gap);
		setHeight(tr.getHeight()+gap*2);
		addActor(tr);
		setPosition((int)(Main.width/2-getWidth()/2), (int)(Main.height/2-getHeight()/2));
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.dark);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		batch.setColor(Colours.earth);
		Draw.drawRectangle(batch, getX(), getY(), getWidth(), getHeight(), 1);
		super.draw(batch, parentAlpha);
	}

}
