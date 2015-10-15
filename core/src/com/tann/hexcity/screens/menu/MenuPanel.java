package com.tann.hexcity.screens.menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.tann.hexcity.Main;
import com.tann.hexcity.screens.gameScreen.Tile.TileType;

import game.util.Button;
import game.util.Colours;
import game.util.Draw;
import game.util.TannFont;

public class MenuPanel extends Group{
	private static MenuPanel self;
	static String[] rules = new String[]{"Score as many points as you can in the given turns [arrow]", "Each turn:[n]Choose a tile to place adjacent to the last tile [arrow]", "Tap a tile to learn more"};
	private static final int menuWidth=91,menuHeight=50;
	public static final int helpX=19,helpY=17;
	public static final int rulesX=16,rulesY=38;
	
	
	public static MenuPanel get(){
		if(self==null)self = new MenuPanel();
		return self;
	}
	
	private MenuPanel() {
		self=this;
		setSize(menuWidth, menuHeight);

		setPosition((int)(Main.width/2-(int)getWidth()/2), (int)(Main.height/2f-getHeight()/2f));
		System.out.println(getX());
		Button b = new Button("Rules", new Runnable() {
			@Override
			public void run() {
				Main.self.currentScreen.addActor(new RulesBlock(rules));
			}
		});
		b.setPosition(rulesX, rulesY);
		addActor(b);
		
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
	}

	public static void show() {
		Main.self.currentScreen.addActor(InputGrabber.get());
		Main.self.currentScreen.addActor(MenuPanel.get());
	}
	
	public static void hide() {
		MenuPanel.get().remove();
		InputGrabber.get().remove();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.grass);
//		System.out.println(getX()+":"+getY()+":"+getWidth()+":"+getHeight() );
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		batch.setColor(Colours.dark);
		Draw.fillRectangle(batch, getX()+1, getY()+1, getWidth()-2, getHeight()-2);
		
		
		super.draw(batch, parentAlpha);
	}
}
