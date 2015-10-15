package com.tann.hexcity.screens.mainScreen;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.tann.hexcity.Main;

import game.util.Colours;
import game.util.Draw;
import game.util.TannFont;

public class Tile extends Actor{
	public static final int tileWidth=16;
	public static final int tileHeight=9;
	static final int tileTapOffsetX=3;
	static final int tileTapOffsetY=1;
	static TextureRegion availableTileRegion = Main.atlas.findRegion("available");
	static TextureRegion scoreRegion = Main.atlas.findRegion("tilescore");
	ArrayList<TileType> adjacentTypes = new ArrayList<>();
	boolean gardenBonusScored;
	boolean showScore;
	int lastScore;
	public enum TileType{
		Hut(Main.atlas.findRegion("hut")), 
		Forester(Main.atlas.findRegion("logger")),
		Garden(Main.atlas.findRegion("garden")),
		Temple(Main.atlas.findRegion("temple")),
		Shrine(Main.atlas.findRegion("shrine")),
		Circle(Main.atlas.findRegion("meeting")),
		Empty(Main.atlas.findRegion("empty")),
		Forest(Main.atlas.findRegion("trees"));

		public TextureRegion region;
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
				if(type!=TileType.Empty) return false;
				if(MainScreen.self.typePicked==null) return false;
				if(!MainScreen.self.grid.lastTilePlaced.isAdjacentTo(Tile.this))return false;
				setType(MainScreen.self.typePicked.type);
				placementEffect();
				MainScreen.self.tilePlaced(Tile.this);
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

		int counter=0;

		switch (type) {
		case Circle:
			score(isCompletelySurrounded()?6:1);
			break;
		case Forester:
			for(Tile t:getAdjacentTiles(1, false)){
				if(t.type==TileType.Forest){
					t.setType(TileType.Empty);
					counter++;
				}
			}
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
					}
				}
				else{
					if(t.gardenBonusScored){
						t.gardenBonusScored=false;
						t.score(t==this?0:-2);
					}
					else{
						if(t==this)t.score(2);
					}

				}
			}
			break;
		case Hut:
			MainScreen.self.bonusHutTurn=!MainScreen.self.bonusHutTurn;
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
			score(counter+1);
			break;
		default:
			break;
		}

		for(Tile t:getAdjacentTiles(1, false)){
			switch(t.type){
			case Circle:
				if(t.isCompletelySurrounded()){
					t.score(5);
				}
				break;
			case Temple:
				if(!t.adjacentTypes.contains(type)){
					t.adjacentTypes.add(type);
					t.score(1);
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
	float delay =.7f;


	public void score(int points){

		MainScreen.self.score.addPoints(points);
		showScore=false;
		clearActions();
		lastScore=points;
		SequenceAction scoreFlash = new SequenceAction();

		scoreFlash.addAction(Actions.run(invertShowScore));
		scoreFlash.addAction(Actions.delay(delay));
		scoreFlash.addAction(Actions.run(invertShowScore));



		addAction(scoreFlash);

	}

	public ArrayList<Tile> getAdjacentTiles(int dist, boolean includeSelf){
		ArrayList<Tile> tiles= new ArrayList<>();
		for(int x=-dist;x<=dist;x++){
			for(int y=-dist;y<=dist;y++){
				Tile t = MainScreen.self.grid.getTile(this.x+x, this.y+y);

				if(!includeSelf && t==this){
					continue;
				}
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
		if(type==TileType.Empty && Tile.this.isAdjacentTo(MainScreen.self.grid.lastTilePlaced)) tr=availableTileRegion;
		if(showScore) tr=scoreRegion;
		batch.setColor(1,1,1,1);
		Draw.draw(batch, tr, getX()-tileTapOffsetX, getY()-tileTapOffsetY);
		if(showScore){
			batch.setColor(lastScore>0?Colours.straw:Colours.earth);
			TannFont.font.drawString(batch, getX()+1, getY()+1, (lastScore>=0?"+":"")+lastScore, false);
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
}
