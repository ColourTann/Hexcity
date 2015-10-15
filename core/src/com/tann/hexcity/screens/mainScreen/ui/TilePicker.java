package com.tann.hexcity.screens.mainScreen.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.tann.hexcity.Main;
import com.tann.hexcity.screens.mainScreen.MainScreen;
import com.tann.hexcity.screens.mainScreen.Tile;
import com.tann.hexcity.screens.mainScreen.Tile.TileType;

import game.util.Colours;
import game.util.Draw;

public class TilePicker extends Actor{
	public TileType type;
	boolean picked;
	static TextureRegion whiteHex= Main.atlas.findRegion("whitehex");
	boolean bonusHut;
	boolean finished;
	public TilePicker() {
		setSize(Tile.tileWidth, Tile.tileHeight);
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if(finished)return false;
				MainScreen.self.pickTile(TilePicker.this);
				pick();
				return false;
			}
		});
		randomiseType(false);
	}
	
	public void randomiseType(boolean allHuts){
		bonusHut=allHuts;
		this.type=allHuts? TileType.Hut: Tile.getRandomTileType();
		if(allHuts){
			MainScreen.self.typePicked=this;	
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.white);
		Draw.draw(batch, type.region, getX(), getY());
		if(picked||bonusHut){
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

	public void reset() {
		randomiseType(false);
		bonusHut=false;
		finished=false;
	}

	public void finished() {
		type=TileType.Empty;
		finished=true;
	}
}


