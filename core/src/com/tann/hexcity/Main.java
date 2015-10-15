package com.tann.hexcity;


import game.util.Colours;
import game.util.Draw;
import game.util.Screen;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tann.hexcity.screens.mainScreen.MainScreen;


public class Main extends ApplicationAdapter {
	public static int width=128,height=64;
	SpriteBatch batch;
	Stage stage;
	OrthographicCamera cam;
	public static TextureAtlas atlas;
	public static Main self;
	public static int scale=1;
	public static boolean debug = true;
	Screen currentScreen;
	Screen previousScreen;
	public static float ticks;
	public enum MainState{Normal, Paused}
	Viewport port;



	@Override
	public void create () {
		self=this;
		switch(Gdx.app.getType()){
		case Android:
			break;
		case Applet:
			break;
		case Desktop:
			setScale(8);
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
		Gdx.input.setInputProcessor(stage);
		//I implemented my own screen system so I have more control over it (yay transitions and screenshake)
		setScreen(new MainScreen());	
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
		port.setScreenBounds(Gdx.graphics.getWidth()/2-w/2, Gdx.graphics.getHeight()/2-h/2, w, h);
		port.apply();
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
	public enum TransitionType{LEFT};
	public void setScreen(Screen screen, TransitionType type, Interpolation interp, float speed){
		if(screen==currentScreen)return;
		setScreen(screen);
		switch(type){
		case LEFT:
			screen.setPosition(Main.width, 0);
			screen.addAction(Actions.moveTo(0, 0, speed, interp));
			previousScreen.addAction(Actions.moveTo(-Main.width, 0, speed, interp));
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
		update(Gdx.graphics.getDeltaTime());
		//draw the stage
		stage.draw();
	}

	public void update(float delta){
		//ticks is just a generally useful variable to have around
		ticks+=delta;
		stage.act(delta);
	}

}
