package com.tann.hexcity.screens.menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.hexcity.screens.gameScreen.Tile.TileType;

import game.util.Colours;
import game.util.Draw;

public class TileHelp extends Actor{
	TileType type;
	public TileHelp(TileType type) {
		this.type=type;
		setSize(type.region.getRegionWidth()-2, type.region.getRegionHeight()-2);
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
