package com.tann.hexcity.screens.gameScreen;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.tann.hexcity.Main;
import com.tann.hexcity.savaData.Trophy;
import com.tann.hexcity.savaData.Trophy.AchievementType;

import game.util.Colours;
import game.util.Draw;
import game.util.TannFont;
import game.util.TextRenderer;

public class Tile extends Actor{
	public static final int tileWidth=16;
	public static final int tileHeight=9;
	static final int tileTapOffsetX=3;
	static final int tileTapOffsetY=1;
	static TextureRegion availableTileRegion = Main.atlas.findRegion("tile/available");
	static TextureRegion scoreRegion = Main.atlas.findRegion("tile/score");
	ArrayList<TileType> adjacentTypes = new ArrayList<>();
	boolean gardenBonusScored;
	boolean showScore;
	int lastScore;
	static int totalScoreThisPlacement;
	public enum TileType{
		//java enums are way cool
		Hut(Main.atlas.findRegion("tile/hut"), "+1 point[n]Place another free hut."), 
		Forester(Main.atlas.findRegion("tile/logger"), "+1 point[n]Cut down all adjacent trees for +1 point each."),
		Garden(Main.atlas.findRegion("tile/garden"), "+2 points[n]+4 points instead if in a group of exactly 3 adjacent gardens."),
		Temple(Main.atlas.findRegion("tile/temple"), "+1 point per different adjacent tile. Other temples don't count due to religious competition by-laws."),
		Shrine(Main.atlas.findRegion("tile/shrine"), "+5 points if more than 2 hexes away from all other shrines."),
		Circle(Main.atlas.findRegion("tile/meeting"), "+1 point[n]+4 more points if completely surrounded."),
		Empty(Main.atlas.findRegion("tile/empty"), null),
		Forest(Main.atlas.findRegion("tile/trees"), "Must be cut down with a forester to build on top. Doesn't count for circles or temples.");
		public TextureRegion region;
		public String rulesText;
		TileType(TextureRegion region, String rulesText){
			this.region=region;
			this.rulesText=rulesText;
		}
	}
	TileType type;
	int x,y;
	public Tile(int x, int y) {
		this.x=x;
		this.y=y;
		setType(TileType.Empty);
		//I want to use the default inputlistener so I'm setting the size of the actor to be smaller than how big it looks
		setSize(tileWidth-tileTapOffsetX*2, tileHeight-tileTapOffsetY*2);
		setPosition(x*11 +tileTapOffsetX, y*8-x*4 +tileTapOffsetY);
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if(type!=TileType.Empty) return false;
				if(GameScreen.get().typePicked==null) return false;
				if(!GameScreen.get().grid.lastTilePlaced.isAdjacentTo(Tile.this)) return false;
				setType(GameScreen.get().typePicked.type);
				placementEffect();
				GameScreen.get().tilePlaced(Tile.this);
				return false;
			}
		});
	}

	public void reset(){
		adjacentTypes.clear();
		gardenBonusScored=false;
		type=TileType.Empty;
	}

	public void placementEffect(){
		//Ahh I wish switches didn't have the damn fall-though stuff
		int counter=0;
		switch (type) {
		case Circle:
			score(isCompletelySurrounded()?5:1);
			break;
		case Forester:
			for(Tile t:getAdjacentTiles(1, false)){
				if(t.type==TileType.Forest){
					t.setType(TileType.Empty);
					GameScreen.get().grid.removeTree();
					counter++;
				}
			}
			Trophy.checkTrophies(AchievementType.CutDownTreesWithASingleWoodsman, counter);
			score(counter+1);
			break;
		case Garden:
			ArrayList<Tile> gardensFound = new ArrayList<>();
			gardensFound.add(this);
			for(int i=0;i<gardensFound.size();i++){
				Tile g=gardensFound.get(i);
				for(Tile t:g.getAdjacentTiles(1, false)){
					if(gardensFound.contains(t))continue;
					if(t.type==TileType.Garden){
						gardensFound.add(t);
					}
				}
			}
			boolean shouldScore = gardensFound.size()==3;
			for(Tile t:gardensFound){
				if(shouldScore){
					if(!t.gardenBonusScored){
						t.gardenBonusScored=true;
						t.score(t==this?4:2);
						GameScreen.get().grid.scoreGarden(1);
					}
				}
				else{
					if(t.gardenBonusScored){
						t.gardenBonusScored=false;
						t.score(t==this?0:-2);
						GameScreen.get().grid.scoreGarden(-1);
					}
					else{
						if(t==this)t.score(2);
					}
				}
			}
			break;
		case Hut:
			GameScreen.get().turnTracker.flipBonusHutTurn();
			score(1);
			break;
		case Shrine:
			boolean foundAnotherShrine=false;
			for(Tile t:getAdjacentTiles(2, false)){
				if(t.type==TileType.Shrine){
					foundAnotherShrine=true;
					break;
				}
			}
			if(!foundAnotherShrine){
				score(5);
				GameScreen.get().scoreKeeper.scoreShrine();
			}
			else score(0);
			break;
		case Temple:
			adjacentTypes.add(TileType.Temple); //temple is not counted//
			for(Tile t:getAdjacentTiles(1, false)){
				if(adjacentTypes.contains(t.type)) continue;
				if(t.type==TileType.Empty||t.type==TileType.Forest)continue;
				adjacentTypes.add(t.type);
				counter++;
			}
			score(counter);
			checkForZiggurat();
			break;
		default:
			break;
		}
		checkSurroundingTiles();
		Trophy.checkTrophies(AchievementType.ScorePointsWithASingleTileInTwenty, totalScoreThisPlacement);
		totalScoreThisPlacement=0;
	}

	private void checkForZiggurat() {
		if(adjacentTypes.size()==6){
			for(Tile t:getAdjacentTiles(1, false)){
				if(t.type==TileType.Temple){
					if(t.adjacentTypes.size()==6){
						Trophy.checkTrophies(AchievementType.Ziggurat, 0);
					}
				}
			}
		}
	}

	public void checkSurroundingTiles(){
		for(Tile t:getAdjacentTiles(1, false)){
			switch(t.type){
			case Circle:
				if(t.isCompletelySurrounded()){
					t.score(4);
				}
				break;
			case Temple:
				if(!t.adjacentTypes.contains(type)){
					t.adjacentTypes.add(type);
					t.score(1);
					t.checkForZiggurat();
				}
				break;
			default:
				break;
			}
		}
		
	}
	
	Runnable invertShowScore = new Runnable() {
		@Override
		public void run() {
			Tile.this.showScore=!Tile.this.showScore;
		}
	};
	static final float delay =.7f;
	public void score(int points){
		GameScreen.get().scoreKeeper.addPoints(points);
		showScore=false;
		clearActions();
		lastScore=points;
		SequenceAction scoreFlash = new SequenceAction();
		scoreFlash.addAction(Actions.run(invertShowScore));
		scoreFlash.addAction(Actions.delay(delay));
		scoreFlash.addAction(Actions.run(invertShowScore));
		addAction(scoreFlash);
		totalScoreThisPlacement+=points;
	}

	public ArrayList<Tile> getAdjacentTiles(int dist, boolean includeSelf){
		ArrayList<Tile> tiles= new ArrayList<>();
		for(int x=-dist;x<=dist;x++){
			for(int y=-dist;y<=dist;y++){
				Tile t = GameScreen.get().grid.getTile(this.x+x, this.y+y);
				if(!includeSelf && t==this) continue;
				if(t!=null&&t.getDistance(this)<=dist){
					tiles.add(t);
				}
			}
		}
		return tiles;
	}

	public void setType(TileType type){
		this.type=type;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		TextureRegion tr = type.region;
		if(type==TileType.Empty && Tile.this.isAdjacentTo(GameScreen.get().grid.lastTilePlaced)) tr=availableTileRegion;
		if(showScore) tr=scoreRegion;
		batch.setColor(1,1,1,1);
		Draw.draw(batch, tr, getX()-tileTapOffsetX, getY()-tileTapOffsetY);
		if(showScore){
			batch.setColor(lastScore>0?Colours.straw:Colours.earth);
			TannFont.font.drawString(batch, (lastScore>=0?"+":"")+lastScore, (int)getX()+1, (int)getY()+1, false);
		}
		super.draw(batch, parentAlpha);
	}

	private void showHitbox(Batch batch){
		batch.setColor(1,0,0,.5f);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
	}

	public static TileType getRandomTileType() {
		return TileType.values()[(int)(Math.random()*6)];
	}

	public boolean isAdjacentTo(Tile t){
		return getDistance(t)==1;
	}

	boolean isCompletelySurrounded(){
		ArrayList<Tile> adj = getAdjacentTiles(1, false);
		if(adj.size()<6)return false;
		boolean complete=true;
		for(Tile t:adj){
			if(t.type==TileType.Empty||t.type==TileType.Forest){
				complete=false;
				break;
			}
		}
		return complete;
	}

	public int getDistance(Tile t){
		int xDist = t.x-x;
		int yDist = t.y-y;
		return Math.max(
				Math.abs(xDist-yDist), 
				Math.max(
						Math.abs(xDist), 
						Math.abs(yDist)));
	}

	public String toString(){
		return type+" at "+x+":"+y;
	}

	public boolean hasFreeSpaces() {
		for(Tile t:getAdjacentTiles(1, false)){
			if(t.type==TileType.Empty) return true;
		}
		return false;
	}
}

