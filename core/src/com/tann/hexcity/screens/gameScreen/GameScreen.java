package com.tann.hexcity.screens.gameScreen;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.tann.hexcity.Main;
import com.tann.hexcity.screens.gameScreen.ui.ScoreKeeper;
import com.tann.hexcity.screens.gameScreen.ui.TilePicker;
import com.tann.hexcity.screens.gameScreen.ui.TurnTracker;
import com.tann.hexcity.screens.menu.PauseButton;

import game.util.Colours;
import game.util.Screen;
import game.util.TannFont;

public class GameScreen extends Screen{
	public enum GameType{
		Ten("10 turns"), Fifteen("15 turns"), Twenty("20 turns"), Hammurabi("campaign");	
		public String description;
		GameType(String description){
			this.description=description;
		}
	}
	//layout constants
	public static final int gridX=23,gridY=3;
	public static final int pickerX=3, pickerY=1, pickerGap = 11;
	public static final int turnX=2, turnY=29;
	public static final int scoreX=6, scoreY=49;
	public static final int menuX=1, menuY=55;

	boolean bonusHutTurn;
	public TilePicker typePicked;
	Grid grid = new Grid();
	ScoreKeeper score = new ScoreKeeper();
	PauseButton button = new PauseButton();
	ArrayList<TilePicker> pickers = new ArrayList<TilePicker>();
	TurnTracker tracker;
	
	GameType type;
	
	private static GameScreen self;
	public static GameScreen get(){
		if(self==null){
			self = new GameScreen();
		}
		return self;
	}
	
	private GameScreen() {
		tracker= new TurnTracker();
		addActor(tracker);
		tracker.setPosition(turnX, turnY);
		addActor(grid);
		grid.setPosition(gridX, gridY);
		addActor(score);
		score.setPosition(scoreX, scoreY);
		addActor(button);
		button.setPosition(menuX, menuY);
		for(int i=0;i<3;i++){
			pickers.add(new TilePicker());
		}
		for(int i=0;i<pickers.size();i++){
			TilePicker picker = pickers.get(i);
			addActor(picker);
			picker.setPosition(pickerX, pickerY+pickerGap*i);
		}	
	}

	public void setup(GameType type){
		reset();
		this.type=type;
		int turns=0;
		switch(type){
		case Ten:
			turns=10;
			break;
		case Fifteen:
			turns=15;
			break;
		case Twenty:
			turns=20;
			break;
		case Hammurabi:
			turns=10;
			break;	
		default:
			break;
		}
		tracker.setTurns(turns);
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
