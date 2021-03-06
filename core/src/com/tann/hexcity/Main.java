package com.tann.hexcity;


import game.util.Colours;
import game.util.Screen;
import game.util.Sounds;
import game.util.TextRenderer;
import game.util.Sounds.SoundType;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tann.hexcity.savaData.SaveData;
import com.tann.hexcity.screens.gameScreen.GameScreen;
import com.tann.hexcity.screens.titleScreen.TitleScreen;


public class Main extends ApplicationAdapter {
	public static final float version = 1.04f;
	public static final float screenTransitionSpeed = .5f;
	public static int width=128,height=64;
	SpriteBatch batch;
	Stage stage;
	OrthographicCamera cam;
	public static TextureAtlas atlas;
	public static Main self;
	public static int scale=1;
	public static boolean debug = true;
	public Screen currentScreen;
	Screen previousScreen;
	public static float ticks;
	public enum MainState{Normal, Paused}
	Viewport port;
	Stage catcherStage;
	public static SaveData saveData;
	int offsetX, offsetY;
	@Override
	public void create () {
		self=this;
		switch(Gdx.app.getType()){
		case Android:
			break;
		case Applet:
			break;
		case Desktop:
			//			setScale(6);
			break;
		case HeadlessDesktop:
			break;
		case WebGL:
			break;
		case iOS:
			break;
		default:
			break;
		}
		saveData = new SaveData();
		setupCatcherStage();



		//bunch of stuff for texturepacking //
		FileHandle atlas_handle = Gdx.files.absolute("D:\\Code\\Eclipse\\Hexcity\\android\\assets\\atlas_image.atlas");
		if(!atlas_handle.exists()){
			atlas_handle=Gdx.files.internal("atlas_image.atlas");
		}
		atlas = new TextureAtlas(atlas_handle);

		//set up my viewport to be the base resolution//
		port = new FitViewport(Main.width, Main.height);
		//make a scene2d stage using the viewport//
		stage = new Stage(port);
		cam=(OrthographicCamera) stage.getCamera();
		batch = (SpriteBatch) stage.getBatch();


		//I implemented my own screen system so I have more control over it (yay transitions and screenshake)

		setScreen(TitleScreen.get());	
		Gdx.input.setCatchBackKey(true);
		stage.addListener(new InputListener(){
			public boolean keyDown (InputEvent event, int keycode) {

				currentScreen.keyPressed(keycode);
				switch(keycode){
				case Keys.ESCAPE:
				case Keys.BACK:
					boolean handled=currentScreen.popActor();
					if(!handled){
						if(Main.self.currentScreen instanceof TitleScreen){
							Gdx.app.exit();
						}
						if(Main.self.currentScreen instanceof GameScreen){
							Main.self.setScreen(TitleScreen.get(), TransitionType.RIGHT, Interpolation.pow2Out, Main.screenTransitionSpeed);
						}
					}
					return true;
				}

				return true;

			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return false;
			}


		});
		TextRenderer.setupTextures();
		Sounds.loadAll();
	}

	@Override
	public void resize (int width, int height) {
		//override the resize method to scale by integer value and center
		//this is called when the game is first opened
		int xScale =(width/128);
		int yScale =(height/64);
		int scale = Math.min(xScale, yScale);
		int w = scale*128;
		int h =	scale*64;
		offsetX= Gdx.graphics.getWidth()/2-w/2;
		offsetY= Gdx.graphics.getHeight()/2-h/2;
		setupCatcherStage();
		port.setScreenBounds(offsetX, offsetY, w, h);
		port.apply();

	}

	private void setupCatcherStage() {
		catcherStage = new Stage();

		catcherStage.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				int newX=(int)x;
				int newY =Gdx.graphics.getHeight()-((int)y);
				boolean handled = stage.touchDown(newX, newY, 0, 0);
				if(!handled){
					if(currentScreen.popActor()){
						Sounds.playSound(SoundType.PopMenu);
					}
				}
				return true;
			}
		});
		Gdx.input.setInputProcessor(catcherStage);
	}

	public void setScale(int scale){
		Main.scale=scale;
		Gdx.graphics.setDisplayMode(Main.width*scale, Main.height*scale, false);
	}

	private MainState state=MainState.Normal;
	public MainState getState(){
		return state;
	}
	public void setState(MainState state){
		this.state=state;
	}
	public enum TransitionType{LEFT, RIGHT};
	public void setScreen(Screen screen, TransitionType type, Interpolation interp, float speed){
		if(screen==currentScreen)return;
		screen.clearActions();
		if(previousScreen!=null)previousScreen.clearActions();
		setScreen(screen);
		switch(type){
		case LEFT:
			screen.setPosition(Main.width, 0);
			screen.addAction(Actions.moveTo(0, 0, speed, interp));
			previousScreen.addAction(Actions.moveTo(-Main.width, 0, speed, interp));
			break;
		case RIGHT:
			screen.setPosition(-Main.width, 0);
			screen.addAction(Actions.moveTo(0, 0, speed, interp));
			previousScreen.addAction(Actions.moveTo(Main.width, 0, speed, interp));
			break;
		}
		previousScreen.addAction(Actions.after(Actions.removeActor()));
	}

	public void setScreen(Screen screen){
		previousScreen=currentScreen;
		currentScreen=screen;
		stage.addActor(screen);
	}

	@Override
	public void render () {
		//clear the screen
		Gdx.gl.glClearColor(Colours.dark.r, Colours.dark.g, Colours.dark.b, 1);
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
		//update everything
		float mult = 1;
		if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))mult=.1f;
		update(Gdx.graphics.getDeltaTime()*mult);
		//draw the stage
		stage.draw();
	}

	public void update(float delta){
		//ticks is just a generally useful variable to have around
		ticks+=delta;
		stage.act(delta);
	}

}
