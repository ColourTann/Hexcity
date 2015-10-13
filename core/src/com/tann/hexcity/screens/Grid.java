package com.tann.hexcity.screens;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.tann.hexcity.screens.Tile.TileType;

public class Grid extends Group{
	Tile[][] tiles = new Tile[9][11];
	public Grid() {
		for(int x=0;x<9;x++){
			for(int y=0;y<11;y++){
				boolean even = x%2==0;
				int value=y-(x+(even?+1:0))/2;
				if(value<0||value>6||!even&&value==0)continue;
				Tile t= new Tile(x,y);
				tiles[x][y]=t;
				addActor(t);
			}
		}
		getTile(4, 5).setType(TileType.Shrine);
		for(int i=0;i<12;){
			Tile t=getTile((int)(Math.random()*tiles.length), (int)(Math.random()*tiles[0].length));
			if(t==null)continue;
			if(t.type!=TileType.Empty)continue;
			t.setType(TileType.Forest);
			i++;
		}
	}
	
	public Tile getTile(int x, int y){
		if(x<0||x>tiles.length||y<0||y>tiles[0].length){
			return null;
		}
		return tiles[x][y];
	}

}
