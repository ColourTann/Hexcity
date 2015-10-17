package com.tann.hexcity.screens.gameScreen;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.tann.hexcity.Main;
import com.tann.hexcity.Main.TransitionType;
import com.tann.hexcity.savaData.Trophy;
import com.tann.hexcity.savaData.Trophy.AchievementType;
import com.tann.hexcity.screens.gameScreen.GameScreen.GameType;
import com.tann.hexcity.screens.gameScreen.ui.ScoreKeeper;
import com.tann.hexcity.screens.gameScreen.ui.TilePicker;
import com.tann.hexcity.screens.gameScreen.ui.TurnTracker;
import com.tann.hexcity.screens.menu.PauseButton;
import com.tann.hexcity.screens.menu.trophy.TrophyIcon;
import com.tann.hexcity.screens.titleScreen.TitleScreen;

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
	public static final int gridX=24,gridY=3;
	public static final int pickerX=3, pickerY=1, pickerGap = 11;
	public static final int turnX=2, turnY=29;
	public static final int scoreX=6, scoreY=49;
	public static final int menuX=0, menuY=55;


	public TilePicker typePicked;
	Grid grid = new Grid();
	ScoreKeeper score = new ScoreKeeper();
	PauseButton button = new PauseButton();
	ArrayList<TilePicker> pickers = new ArrayList<TilePicker>();
	public TurnTracker tracker;
	public boolean hammurabiMode;
	public GameType gameType;

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

		addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if(!score.finishedFlag)return false;
				if(hammurabiMode){
					switch(tracker.turns){
					case 10:
						tracker.setTurns(15);
						gameType=GameType.Fifteen;
						reset(false);
						break;
					case 15:
						tracker.setTurns(20);
						gameType=GameType.Twenty;
						reset(false);
						break;
					case 20: 
						Main.self.setScreen(TitleScreen.get(), TransitionType.RIGHT, Interpolation.pow2Out, Main.screenTransitionSpeed);
						break;
					}
					return false;
				}
				Main.self.setScreen(TitleScreen.get(), TransitionType.RIGHT, Interpolation.pow2Out, Main.screenTransitionSpeed);

				return false;
			}
		});
	}

	public void setup(GameType type){
		reset(true);
		hammurabiMode=false;
		this.gameType=type;
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
			this.gameType=GameType.Ten;
			hammurabiMode=true;
			turns=10;
			break;	
		default:
			break;
		}
		tracker.setTurns(turns);
	}

	public void reset(boolean full){
		grid.reset();
		score.reset(full);
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
		setupNewAchievements();
	}

	public void pickTile(TilePicker tilePicker) {
		if(typePicked!=null) typePicked.unpick();
		typePicked=tilePicker;
	}

	public void tilePlaced(Tile t) {
		typePicked.unpick();
		typePicked=null;
		for(TilePicker p:pickers){p.randomiseType(tracker.bonusHutTurn);}
		grid.lastTilePlaced=t;
		tracker.incrementTurns();
		if(!t.hasFreeSpaces())gameEnd();
	}

	public void gameEnd() {
		score.finished();
		for(TilePicker p:pickers){
			p.finished();
		}
	}

	@Override
	public void keyPressed(int keycode) {
		
	}

	ArrayList<TrophyIcon> newAchievements = new ArrayList<>();
	ArrayList<TrophyIcon> currentAchievements = new ArrayList<>();

	public void showAchievement(Trophy a){
		newAchievements.add(new TrophyIcon(a));
	}

	private void setupNewAchievements(){
		if(newAchievements.size()==0)return;
		for(int i=0;i<newAchievements.size();i++){
			final TrophyIcon icon = newAchievements.get(i);
			addActor(icon);
			currentAchievements.add(0, icon);
			icon.setPosition(getWidth()-icon.getWidth(), -(icon.getHeight()-1)*(i+1));
			icon.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					icon.addAction(Actions.moveTo(icon.getX()+icon.getWidth(), icon.getY(), .3f, Interpolation.pow2Out));
					currentAchievements.remove(icon);
					updateAchievementLocations();
					return false;
				}
			});
		}
		updateAchievementLocations();
		newAchievements.clear();

	}

	public void updateAchievementLocations(){
		for(int i=0;i<currentAchievements.size();i++){
			TrophyIcon icon = currentAchievements.get(i);
			icon.addAction(Actions.moveTo(icon.getX(), (int)(icon.getHeight()-1)*i, .3f, Interpolation.linear));
		}

	}

	public void restart() {
		if(hammurabiMode){
			setup(GameType.Hammurabi);
		}
		else{
			setup(gameType);
		}
	}
}
