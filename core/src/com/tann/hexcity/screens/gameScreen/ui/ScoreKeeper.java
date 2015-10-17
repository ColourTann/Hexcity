package com.tann.hexcity.screens.gameScreen.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.hexcity.Main;
import com.tann.hexcity.savaData.Trophy;
import com.tann.hexcity.savaData.Trophy.AchievementType;
import com.tann.hexcity.screens.gameScreen.GameScreen;

import game.util.Colours;
import game.util.TannFont;

public class ScoreKeeper extends Actor{
	public int totalScore=0;
	public int score=0;
	public boolean finishedFlag;
	private boolean finished;

	@Override
	public void act(float delta) {
		finishedFlag=finished;
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {

		batch.setColor(Colours.earth);
		if(finished){
			float freq=6;
			batch.setColor(Math.sin(Main.ticks*freq)>0?Colours.earth:Colours.straw);
		}
		String scoreString ="";
		scoreString=score+"";
		while(scoreString.length()<3)scoreString="0"+scoreString;
		TannFont.font.drawString(batch, scoreString, (int)getX(), (int)getY(), true);


		super.draw(batch, parentAlpha);
	}

	public void addPoints(int points) {
		score+=points;
	}

	public void reset(boolean full) {
		finished=false;
		finishedFlag=false;
		score=0;
		if(full) totalScore=0;
	}

	public void finished(){
		if(GameScreen.get().hammurabiMode){
			totalScore+=score;
		}
		AchievementType type=null;
		switch(GameScreen.get().tracker.turns){
		case 10: type=AchievementType.ScoreTen; break;
		case 15: type=AchievementType.ScoreFifteen; break;
		case 20: 
			score=totalScore;
			type=AchievementType.ScoreTwenty;
			if(GameScreen.get().hammurabiMode){
				Trophy.checkTrophies(AchievementType.ScoreCampaign, totalScore);
			}
			break;
		}
		Trophy.checkTrophies(type, score);
		finished=true;		
	}
}

