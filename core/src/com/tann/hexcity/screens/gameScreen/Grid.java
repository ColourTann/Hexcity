package com.tann.hexcity.screens.gameScreen;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.tann.hexcity.savaData.Trophy;
import com.tann.hexcity.savaData.Trophy.AchievementType;
import com.tann.hexcity.screens.gameScreen.Tile.TileType;

public class Grid extends Group{
	//two lists of tiles, one for getting locations, one for iterating through all tiles
	private static final int startingTrees=14;
	private int treesRemoved;
	private int gardensScored;
	Tile[][] tiles = new Tile[9][11];
	ArrayList<Tile> allTiles = new ArrayList<>();
	public Tile lastTilePlaced;
	public Grid() {
		setupTiles();
		reset();
	}
	
	void setupTiles(){
		for(int x=0;x<9;x++){
			for(int y=0;y<11;y++){
				boolean even = x%2==0;
				int value=y-(x+(even?+1:0))/2;
				if(value<0||value>6||!even&&value==0)continue;
				Tile t= new Tile(x,y);
				allTiles.add(t);
				tiles[x][y]=t;
				addActor(t);
			}
		}
	}
	
	public Tile getTile(int x, int y){
		if(x<0||x>=tiles.length||y<0||y>=tiles[0].length){
			return null;
		}
		return tiles[x][y];
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

	public void reset() {
		for(Tile t:allTiles)t.reset();
		Tile startingTile = getTile(4, 5);
		startingTile.setType(TileType.Shrine);
		lastTilePlaced=startingTile;
		for(int i=0;i<startingTrees;){
			Tile t=getTile((int)(Math.random()*tiles.length), (int)(Math.random()*tiles[0].length));
			if(t==null)continue;
			if(t.type!=TileType.Empty)continue;
			if(t.getDistance(startingTile)<2)continue;
			t.setType(TileType.Forest);
			i++;
		}
		treesRemoved=0;
		gardensScored=0;
	}
	
	public void removeTree(){
		treesRemoved++;
		Trophy.checkTrophies(AchievementType.CutDownTreesInFifteen, treesRemoved);
	}
	
	public void scoreGarden(int amount){
		gardensScored+=amount;
		Trophy.checkTrophies(AchievementType.MakeGardenSetsInTen, gardensScored);
	}

}
