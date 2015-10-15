package com.tann.hexcity.screens.titleScreen;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.tann.hexcity.Main;
import com.tann.hexcity.Main.TransitionType;
import com.tann.hexcity.screens.gameScreen.GameScreen;
import com.tann.hexcity.screens.gameScreen.GameScreen.GameType;
import com.tann.hexcity.screens.gameScreen.Tile.TileType;
import com.tann.hexcity.screens.menu.PauseButton;

import game.util.Colours;
import game.util.Draw;
import game.util.Screen;
import game.util.TannFont;
import game.util.TextRenderer;

public class TitleScreen extends Screen{
	static final int titleOffsetX=27;
	static final int titleOffsetY=48;
	
	static final int buttonX=25;
	static final int buttonY=28;
	static final int buttonXGap=40;
	static final int buttonYGap=12;
	
	static TextureRegion[] cuneiform = new TextureRegion[]{Main.atlas.findRegion("ui/cuneiform1"),Main.atlas.findRegion("ui/cuneiform2")};
	static TextureRegion smallerTile = Main.atlas.findRegion("ui/smallertile");
	static TextureRegion[] smallerDecoration = new TextureRegion[]{Main.atlas.findRegion("ui/smallergarden"), Main.atlas.findRegion("ui/smallershrine"), Main.atlas.findRegion("ui/smallerlogger")};
	
	ArrayList<StartGameButton> butts = new ArrayList<>();
	public static TitleScreen self;
	
	public static TitleScreen get(){
		if(self==null)self = new TitleScreen();
		return self;
	}
	
	private TitleScreen() {
		for(GameType t:GameType.values()){
			butts.add(new StartGameButton(t));
		}
		
		for(int i=0;i<butts.size();i++){
			StartGameButton b = butts.get(i);
			addActor(b);
			b.setPosition(buttonX+buttonXGap*(i%2), buttonY-buttonYGap*(i/2));
		}
		PauseButton mButt = new PauseButton();
		mButt.setPosition(getWidth()/2-mButt.getWidth()/2, 5);
		addActor(mButt);	
	}
	
	public void startGame(GameType type){
		Main.self.setScreen(new GameScreen(type), TransitionType.LEFT, Interpolation.pow2Out, Main.screenTransitionSpeed);
	}
	
	@Override
	public void preDraw(Batch batch) {
		batch.setColor(Colours.white);
		Draw.draw(batch, cuneiform[0], getX(), getY());
		Draw.draw(batch, cuneiform[1], getX()+Main.width-cuneiform[1].getRegionWidth(), getY());
		drawLogoHex(batch, 0, 0, "H", smallerTile);
		drawLogoHex(batch, 1, -1, "E", smallerTile);
		drawLogoHex(batch, 2, -1, "X", smallerTile);
		drawLogoHex(batch, 3, -2, "C", smallerTile);
		drawLogoHex(batch, 3, -1, null, smallerDecoration[1]);
		drawLogoHex(batch, 4, -2, "I", smallerTile);
		drawLogoHex(batch, 5, -3, "T", smallerTile);
		drawLogoHex(batch, 6, -3, "Y", smallerTile);
		batch.setColor(Colours.white);
	
		
	}

	public void drawLogoHex(Batch batch, int x, int y, String word, TextureRegion tileRegion){
		batch.setColor(Colours.white);
		int xPos=x*10+titleOffsetX;
		int yPos=y*8+x*4+titleOffsetY;
		Draw.draw(batch, tileRegion, getX()+xPos, getY()+yPos);
		if(word!=null){
			batch.setColor(Colours.earth);
			int textX=xPos+6;
			int textY=yPos+2;
			if(word=="I"){
				textX++;
			}
			TannFont.font.drawString(batch, word, (int)getX()+textX, (int)getY()+textY, false);
		}
	}

	@Override
	public void postDraw(Batch batch) {
	}

	@Override
	public void preTick(float delta) {
	}

	@Override
	public void postTick(float delta) {
	}

}
