package com.tann.hexcity.screens.menu.trophy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.tann.hexcity.Main;
import com.tann.hexcity.screens.menu.MenuPanel;

import game.util.Colours;
import game.util.Draw;

public class TrophyPanel extends Group{
	static TextureRegion trophyTexture = Main.atlas.findRegion("ui/achievement");
	
	private static final int trophyPanelWidth=MenuPanel.menuWidth/2, trophyPanelHeight=MenuPanel.menuHeight;
	
	private static TrophyPanel self;
	public static TrophyPanel get(){
		if(self==null){
			self = new TrophyPanel();
		}
		return self;
	}
	
	private TrophyPanel() {
		setSize(trophyPanelWidth, trophyPanelHeight);
		setPosition((int)(Main.width/2-(int)getWidth()/2), (int)(Main.height/2-getHeight()/2));
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
