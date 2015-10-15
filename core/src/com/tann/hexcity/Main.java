package com.tann.hexcity;


import game.util.Colours;
import game.util.Draw;
import game.util.Fonts;
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
	FrameBuffer buffer;
	public static float ticks;
	public enum MainState{Normal, Paused}
	Viewport port;

	SpriteBatch screenBatch;

	@Override
	public void create () {
		self=this;
		switch(Gdx.app.getType()){
		case Android:
			//			scale = Gdx.graphics.getWidth()/Main.width;
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
		screenBatch = new SpriteBatch();
		Fonts.setup();
		buffer = new FrameBuffer(Format.RGBA8888, Main.width, Main.height, false);
		FileHandle atlas_handle = Gdx.files.absolute("D:\\Code\\Eclipse\\Hexcity\\android\\assets\\atlas_image.atlas");
		if(!atlas_handle.exists()){
			atlas_handle=Gdx.files.internal("atlas_image.atlas");
		}
		atlas = new TextureAtlas(atlas_handle);
		port = new FitViewport(Main.width, Main.height);
		port.apply();
		stage = new Stage(port);
		cam=(OrthographicCamera) stage.getCamera();
		cam.update();
		batch = (SpriteBatch) stage.getBatch();
		Gdx.input.setInputProcessor(stage);
		setScreen(new MainScreen());	
	}

	@Override
	public void resize (int width, int height) {
		//		port.update((width/128)*128, (height/64)*64);
		int xScale =(width/128);
		int yScale =(height/64);
		int scale = Math.min(xScale, yScale);
		int w = scale*128;
		int h =	scale*64;
		port.update(w, h);
		port.setScreenBounds(Gdx.graphics.getWidth()/2-w/2, Gdx.graphics.getHeight()/2-h/2, w, h);
		port.apply();
		cam.update();
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
			//			Action a = ;
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
		Gdx.gl.glClearColor(Colours.dark.r, Colours.dark.g, Colours.dark.b, 1);
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
		update(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	public void drawFPS(Batch batch){
		//		Fonts.font.setColor(Colours.light);
		//		Fonts.font.draw(batch, "FPS: "+Gdx.graphics.getFramesPerSecond(), 0, Main.height);
	}


	public void update(float delta){
		ticks+=delta;
		stage.act(delta);
	}

}
