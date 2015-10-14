package com.tann.hexcity.screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.tann.hexcity.Main;

import game.util.Draw;
import game.util.TannFont;

public class Tile extends Actor{
	static final int tileWidth=16,tileHeight=9;
	static final int tileTapOffsetX=3;
	static final int tileTapOffsetY=1;
	
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
		setSize(tileWidth-tileTapOffsetX*2, tileHeight-tileTapOffsetY*2);
		setPosition(x*11 +tileTapOffsetX, y*8-x*4 +tileTapOffsetY);
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
//				if(x<tileTapOffsetX||x>getWidth()-tileTapOffsetX)return false;
//				if(y<tileTapOffsetY||y>getHeight()-tileTapOffsetY)return false;
				setType(MainScreen.self.typePicked.type);
				
				return false;
			}
		});
	}
	
	public void setType(TileType type){
		this.type=type;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(1,1,1,1);
		Draw.draw(batch, type.region, getX()-tileTapOffsetX, getY()-tileTapOffsetY);
		
//		batch.setColor(1,0,0,.5f);
//		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
//		batch.setColor(0,1,1,.2f);
//		Draw.fillRectangle(batch, getX()+tileTapOffsetX, getY()+tileTapOffsetY, getWidth()-tileTapOffsetX*2, getHeight()-tileTapOffsetY*2);
		
		super.draw(batch, parentAlpha);
	}

	public static TileType getRandomTileType() {
		return TileType.values()[(int)(Math.random()*6)];
	}


}
