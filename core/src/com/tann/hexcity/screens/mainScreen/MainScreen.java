package com.tann.hexcity.screens.mainScreen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.tann.hexcity.Main;
import com.tann.hexcity.screens.mainScreen.Tile.TileType;
import com.tann.hexcity.screens.mainScreen.ui.MenuButton;
import com.tann.hexcity.screens.mainScreen.ui.ScoreKeeper;
import com.tann.hexcity.screens.mainScreen.ui.TilePicker;
import com.tann.hexcity.screens.mainScreen.ui.TurnTracker;

import game.util.Colours;
import game.util.Draw;
import game.util.Screen;
import game.util.TannFont;

public class MainScreen extends Screen{
	//layout stuff 
	public static final int gridX=23,gridY=3;
	
	public static final int pickerX=3, pickerY=1, pickerGap = 11;
	public static final int turnX=2, turnY=29;
	public static final int scoreX=6, scoreY=49;
	public static final int menuX=1, menuY=55;
	
	
	boolean bonusHutTurn;
	
	
	TannFont font= new TannFont(Main.atlas.findRegion("font"));
	public TilePicker typePicked;
	Grid grid = new Grid();
	ScoreKeeper score = new ScoreKeeper();
	MenuButton button = new MenuButton();
	ArrayList<TilePicker> pickers = new ArrayList<TilePicker>();
	TurnTracker tracker = new TurnTracker();
	
	public static MainScreen self;
	public MainScreen() {
		self=this;
		addActor(tracker);
		tracker.setPosition(turnX, turnY);
		addActor(grid);
		addActor(score);
		score.setPosition(scoreX, scoreY);
		addActor(button);
		button.setPosition(menuX, menuY);
		grid.setPosition(gridX, gridY);
		for(int i=0;i<3;i++){
			pickers.add(new TilePicker());
		}
		for(int i=0;i<pickers.size();i++){
			TilePicker picker = pickers.get(i);
			addActor(picker);
			picker.setPosition(pickerX, pickerY+pickerGap*i);
		}	
	}
	
	public void reset(){
		bonusHutTurn=false;
		grid.reset();
		score.reset();
		tracker.reset();
		for(TilePicker p:pickers)p.reset();
		
	}
	
	@Override
	public void preDraw(Batch batch) {
	}

	@Override
	public void postDraw(Batch batch) {
		batch.setColor(Colours.straw);	
	}

	@Override
	public void preTick(float delta) {
	}

	@Override
	public void postTick(float delta) {
	}

	public void pickTile(TilePicker tilePicker) {
		if(typePicked!=null) typePicked.unpick();
		typePicked=tilePicker;
	}

	public void tilePlaced(Tile t) {
		typePicked.unpick();
		typePicked=null;
		for(TilePicker p:pickers){p.randomiseType(bonusHutTurn);}
		grid.lastTilePlaced=t;
		if(!bonusHutTurn)tracker.incrementTurns();
	}

	public void outOfTurns() {
		score.finished();
		for(TilePicker p:pickers){
			p.finished();
		}
	}

}
