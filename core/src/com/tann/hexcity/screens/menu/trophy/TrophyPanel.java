package com.tann.hexcity.screens.menu.trophy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.tann.hexcity.Main;
import com.tann.hexcity.savaData.Trophy;
import com.tann.hexcity.screens.menu.MenuPanel;
import com.tann.hexcity.screens.menu.rules.RulesBlock;

import game.util.Colours;
import game.util.Draw;
import game.util.TannFont;

public class TrophyPanel extends Group{
	static TextureRegion trophyTexture = Main.atlas.findRegion("ui/achievement");
	
	private static final int achievementOffset=3, achievementGap=2;
	private static final int achievementsAcross=7, achievementsDown=3;
	
	public static final int trophyPanelWidth=achievementOffset*2+achievementsAcross*TrophyIcon.iconWidth+(achievementsAcross-1)*achievementGap, 
			trophyPanelHeight=achievementOffset*2+achievementsDown*TrophyIcon.iconHeight+(achievementsDown-1)*achievementGap;
	
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
		
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				event.stop();
				return true;
			}
		});
	
		for(int i=0;i<Trophy.achievementsList.size();i++){
			Trophy a= Trophy.achievementsList.get(i);
			TrophyIcon icon = new TrophyIcon(a);
			icon.setPosition((int)(achievementOffset+(TrophyIcon.iconWidth+achievementGap)*(a.x)), 
					(int)(achievementOffset+(TrophyIcon.iconHeight+achievementGap)*(a.y)));
			addActor(icon);	
		}
		
		
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.grass);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		batch.setColor(Colours.dark);
		Draw.fillRectangle(batch, getX()+1, getY()+1, getWidth()-2, getHeight()-2);
		batch.setColor(Colours.earth);
//		TannFont.font.drawString(batch, "Highscores", (int)getX()+highscoreWordX, (int)getY()+highscoreWordY, false);
//		for(int i=0;i<4;i++){
//			TannFont.font.drawString(batch, "mode: 155", (int)getX()+scoreX, (int)scoreY-TannFont.font.getHeight()*i-scoreGap*(i), false);
//		}
		
		super.draw(batch, parentAlpha);
	}
}
