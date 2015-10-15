package com.tann.hexcity.screens.menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.tann.hexcity.Main;
import com.tann.hexcity.screens.gameScreen.Tile.TileType;

import game.util.Colours;
import game.util.Draw;

public class TileHelp extends Actor{
	TileType type;
	public TileHelp(final TileType type) {
		this.type=type;
		setSize(type.region.getRegionWidth()-2, type.region.getRegionHeight()-2);
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Main.self.currentScreen.addActor(new RulesBlock("["+type.toString().toLowerCase()+"] "+type.toString()+" ["+type.toString().toLowerCase()+"]"+"[n]"+type.rulesText));
				return false;
			}
			
		});
	}
	
	public void setHexPosition(int x, int y){
		setPosition(x*14+MenuPanel.helpX, y*11-x*5+MenuPanel.helpY);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.white);
		Draw.draw(batch, type.region, getX()-1, getY()-1);
		super.draw(batch, parentAlpha);
	}

}
