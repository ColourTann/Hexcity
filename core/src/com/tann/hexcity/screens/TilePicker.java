package com.tann.hexcity.screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.tann.hexcity.Main;
import com.tann.hexcity.screens.Tile.TileType;

import game.util.Colours;
import game.util.Draw;

public class TilePicker extends Actor{
	TileType type;
	boolean picked;
	static TextureRegion whiteHex= Main.atlas.findRegion("whitehex");
	public TilePicker() {
		setSize(Tile.tileWidth, Tile.tileHeight);
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				MainScreen.self.pickTile(TilePicker.this);
				pick();
				return false;
			}
		});
		randomiseType();
	}
	
	public void randomiseType(){
		this.type=Tile.getRandomTileType();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.white);
		Draw.draw(batch, type.region, getX(), getY());
		if(picked){
			batch.setColor(Colours.grass);
			Draw.draw(batch, whiteHex, getX(), getY());
		}
		super.draw(batch, parentAlpha);
	}
	
	public void pick(){
		picked=true;
	}

	public void unpick() {
		picked=false;
	}
}


