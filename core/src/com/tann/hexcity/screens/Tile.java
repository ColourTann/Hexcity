package com.tann.hexcity.screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.hexcity.Main;

import game.util.Draw;
import game.util.TannFont;

public class Tile extends Actor{
	public enum TileType{
		Hut(Main.atlas.findRegion("hut")), 
		Forester(Main.atlas.findRegion("logger")),
		Garden(Main.atlas.findRegion("garden")),
		Temple(Main.atlas.findRegion("temple")),
		Shrine(Main.atlas.findRegion("shrine")),
		Circle(Main.atlas.findRegion("meeting")),
		Empty(Main.atlas.findRegion("empty")),
		Forest(Main.atlas.findRegion("trees"));
		
		TextureRegion region;
		TileType(TextureRegion region){
			this.region=region;
		}
	}
	TileType type=TileType.Empty;
	int x,y;
	public Tile(int x, int y) {
		this.x=x;
		this.y=y;
		setType(TileType.Empty);
		setPosition(x*11, y*8+-x*4);
	}
	
	public void setType(TileType type){
		this.type=type;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(1,1,1,1);
		Draw.draw(batch, type.region, getX(), getY());
		super.draw(batch, parentAlpha);
	}

	public void randomiseType() {
		setType(TileType.values()[(int)(Math.random()*6)]);
	}


}
