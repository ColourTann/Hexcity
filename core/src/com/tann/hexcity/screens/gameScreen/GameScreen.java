package com.tann.hexcity.screens.gameScreen;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.tann.hexcity.Main;
import com.tann.hexcity.Main.TransitionType;
import com.tann.hexcity.savaData.Trophy;
import com.tann.hexcity.savaData.Trophy.AchievementType;
import com.tann.hexcity.screens.gameScreen.GameScreen.GameType;
import com.tann.hexcity.screens.gameScreen.ui.CampaignBreakdown;
import com.tann.hexcity.screens.gameScreen.ui.HighscoreIcon;
import com.tann.hexcity.screens.gameScreen.ui.ScoreKeeper;
import com.tann.hexcity.screens.gameScreen.ui.TilePicker;
import com.tann.hexcity.screens.gameScreen.ui.TurnTracker;
import com.tann.hexcity.screens.menu.PauseButton;
import com.tann.hexcity.screens.menu.trophy.TrophyIcon;
import com.tann.hexcity.screens.titleScreen.TitleScreen;

import game.util.Colours;
import game.util.Screen;
import game.util.Sounds;
import game.util.TannFont;
import game.util.Sounds.SoundType;

public class GameScreen extends Screen{
	public enum GameType{
		Ten("short"), Fifteen("medium"), Twenty("long"), Hammurabi("campaign");	
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
	public Grid grid = new Grid();
	ScoreKeeper scoreKeeper = new ScoreKeeper();
	PauseButton pauseButton = new PauseButton();
	ArrayList<TilePicker> pickers = new ArrayList<TilePicker>();
	public TurnTracker turnTracker;
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
		turnTracker= new TurnTracker();
		addActor(turnTracker);
		turnTracker.setPosition(turnX, turnY);
		addActor(grid);
		grid.setPosition(gridX, gridY);
		addActor(scoreKeeper);
		scoreKeeper.setPosition(scoreX, scoreY);
		addActor(pauseButton);
		pauseButton.setPosition(menuX, menuY);
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
				if(!scoreKeeper.finished)return false;
				if(hammurabiMode){
					switch(turnTracker.turns){
					case 10:
						turnTracker.setTurns(15);
						gameType=GameType.Fifteen;
						reset(false);
						break;
					case 15:
						turnTracker.setTurns(20);
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
		turnTracker.setTurns(turns);
	}

	public void reset(boolean full){
		grid.reset();
		scoreKeeper.reset(full);
		turnTracker.reset();
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
		for(TilePicker p:pickers){p.randomiseType(turnTracker.bonusHutTurn);}
		grid.lastTilePlaced=t;
		turnTracker.incrementTurns();
		if(!t.hasFreeSpaces())gameEnd();
	}

	public void gameEnd() {
		scoreKeeper.finished();
		for(TilePicker p:pickers){
			p.finished();
		}
	}

	@Override
	public void keyPressed(int keycode) {
//		pushActor(new CampaignBreakdown(new int[]{5,5,50}, 60));
		
	}

	public ArrayList<Actor> newNotification = new ArrayList<>();
	ArrayList<Actor> oldNotifications = new ArrayList<>();


	
	public void showAchievement(Trophy a){
		newNotification.add(new TrophyIcon(a));
	}

	private void setupNewAchievements(){
		if(newNotification.size()==0)return;
		for(int i=0;i<newNotification.size();i++){
			final Actor icon = newNotification.get(i);
			addActor(icon);
			oldNotifications.add(0, icon);
			icon.setPosition(getWidth()-icon.getWidth(), -(icon.getHeight()-1)*(i+1));
			icon.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					icon.addAction(Actions.moveTo(icon.getX()+icon.getWidth(), icon.getY(), .3f, Interpolation.pow2Out));
					oldNotifications.remove(icon);
					updateAchievementLocations();
					if(icon instanceof HighscoreIcon) Sounds.playSound(SoundType.PushMenu);
					event.stop();
					return false;
				}
			});
		}
		updateAchievementLocations();
		newNotification.clear();

	}

	public void updateAchievementLocations(){
		for(int i=0;i<oldNotifications.size();i++){
			Actor icon = oldNotifications.get(i);
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
