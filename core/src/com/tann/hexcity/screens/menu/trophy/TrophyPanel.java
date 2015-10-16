package com.tann.hexcity.screens.menu.trophy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.tann.hexcity.Main;
import com.tann.hexcity.screens.menu.MenuPanel;
import com.tann.hexcity.screens.menu.rules.RulesBlock;

import game.util.Colours;
import game.util.Draw;
import game.util.TannFont;

public class TrophyPanel extends Group{
	static TextureRegion trophyTexture = Main.atlas.findRegion("ui/achievement");
	private static final int trophyPanelWidth=Main.width, trophyPanelHeight=Main.height;
	private static final int achievementOffset=3, achievementGap=15;
	private static final int scoreX=80, scoreY=45, scoreGap=2, highscoreWordX=80, highscoreWordY=56;
	private static final int hiddenX=70, hiddenY=3, hiddenGap=18;
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
				Main.self.currentScreen.popActor();
				return false;
			}
		});
		
		int rows = 4;
		int cols = 4;
		for(int i=0;i<Achievement.achievementsList.size();i++){
			Achievement a= Achievement.achievementsList.get(i);
			AchievementIcon icon = new AchievementIcon(a);
			icon.setPosition(achievementOffset+achievementGap*(i%cols), achievementOffset+achievementGap*(i/rows));
			addActor(icon);	
		}
		
		for(int i=0;i<3;i++){
			Achievement a= new Achievement(true);
			AchievementIcon icon = new AchievementIcon(a);
			icon.setPosition(hiddenX+hiddenGap*i, hiddenY);
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
		TannFont.font.drawString(batch, "Highscores", (int)getX()+highscoreWordX, (int)getY()+highscoreWordY, false);
		for(int i=0;i<4;i++){
			TannFont.font.drawString(batch, "mode: 155", (int)getX()+scoreX, (int)scoreY-TannFont.font.getHeight()*i-scoreGap*(i), false);
		}
		
		super.draw(batch, parentAlpha);
	}
}
