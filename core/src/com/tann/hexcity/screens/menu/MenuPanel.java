package com.tann.hexcity.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.tann.hexcity.Main;
import com.tann.hexcity.Main.TransitionType;
import com.tann.hexcity.screens.gameScreen.GameScreen;
import com.tann.hexcity.screens.gameScreen.Tile.TileType;
import com.tann.hexcity.screens.menu.rules.RulesBlock;
import com.tann.hexcity.screens.menu.rules.TileHelp;
import com.tann.hexcity.screens.menu.trophy.TrophyPanel;
import com.tann.hexcity.screens.titleScreen.TitleScreen;

import game.util.Button;
import game.util.Colours;
import game.util.Draw;

public class MenuPanel extends Group{
	private static MenuPanel self;
	static String[] rules = new String[]{"Score as many points as you can in the given turns [arrow]", "Each turn:[n]Choose a tile to place adjacent to the last tile [arrow]", "Tap a tile to learn more"};

	public static final int menuWidth=88,menuHeight=49;
	public static final int helpX=19, helpY=16;
	private static final int rulesX=14,rulesY=37;
	private static final int trophyX=58,scoreX=77,trophyY=30;
	private static final int restartX=52,restartY=4,quitY=16, muteX=72;
	
	static Group menuContainerGroup;
	public static MenuPanel get(){
		if(self==null)self = new MenuPanel();
		return self;
	}

	private MenuPanel() {
		self=this;
		setSize(menuWidth, menuHeight);

		setPosition((int)(Main.width/2-(int)getWidth()/2), (int)(Main.height/2f-getHeight()/2f));
		Button rulesButton = new Button("RULES", new Runnable() {
			@Override
			public void run() {
				Main.self.currentScreen.pushActor(new RulesBlock(rules));
			}
		});
		rulesButton.setPosition(rulesX, rulesY);
		addActor(rulesButton);

		TrophyButton achievementsButton = new TrophyButton();
		achievementsButton.setPosition((int)(trophyX-achievementsButton.getWidth()/2), trophyY);
		addActor(achievementsButton);
		
		ScoreButton scoreButton = new ScoreButton();
		scoreButton.setPosition((int)(scoreX-scoreButton.getWidth()/2), trophyY);
		addActor(scoreButton);

		Button quitButton = new Button("EXIT", new Runnable() {

			@Override
			public void run() {
				Main.self.currentScreen.popActor();
				if(Main.self.currentScreen==TitleScreen.get()){
					Gdx.app.exit();
					return;
				}
				Main.self.setScreen(TitleScreen.get(), TransitionType.RIGHT, Interpolation.pow2Out, Main.screenTransitionSpeed);
			}
		});
		quitButton.setPosition((int)(restartX), quitY);
		addActor(quitButton);

		Button restartButton = new Button("RESTART", new Runnable() {

			@Override
			public void run() {
				Main.self.currentScreen.popActor();
				if(Main.self.currentScreen instanceof GameScreen){
					Main.saveData.addRestart();
					GameScreen.get().restart();
				}
			}
		});
		restartButton.setPosition((int)(restartX), restartY);
		addActor(restartButton);

		MuteButton mb = new MuteButton();
		mb.setPosition(muteX, quitY);
		addActor(mb);
		
		for(TileType type:TileType.values()){
			if(type==TileType.Empty) continue;
			TileHelp help = new TileHelp(type);
			addActor(help);
			switch(type){
			case Circle:
				help.setHexPosition(1, 0);
				break;
			case Forest:
				help.setHexPosition(0, 0);
				break;
			case Forester:
				help.setHexPosition(1, 1);
				break;
			case Garden:
				help.setHexPosition(0, 1);
				break;
			case Hut:
				help.setHexPosition(-1, 0);
				break;
			case Shrine:
				help.setHexPosition(0, -1);
				break;
			case Temple:
				help.setHexPosition(-1, -1);
				break;
			default:
				break;
			}
		}
		addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				event.stop();
				return false;
			}
		});
	}

	static boolean shown;
	public static void show() {
		shown=true;	
		Main.self.currentScreen.pushActor(get());
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.grass);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		batch.setColor(Colours.dark);
		Draw.fillRectangle(batch, getX()+1, getY()+1, getWidth()-2, getHeight()-2);
		super.draw(batch, parentAlpha);
	}

	
}
