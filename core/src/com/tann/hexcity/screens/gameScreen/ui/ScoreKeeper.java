package com.tann.hexcity.screens.gameScreen.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.hexcity.Main;
import com.tann.hexcity.savaData.Trophy;
import com.tann.hexcity.savaData.Trophy.AchievementType;
import com.tann.hexcity.screens.gameScreen.GameScreen;
import com.tann.hexcity.screens.gameScreen.GameScreen.GameType;

import game.util.Colours;
import game.util.TannFont;

public class ScoreKeeper extends Actor{
	public int totalScore=0;
	public int score=0;
	int shrinesScored;
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
		shrinesScored=0;
		finished=false;
		finishedFlag=false;
		score=0;
		if(full) totalScore=0;
	}

	public void finished(){
		if(finished||finishedFlag)return;
		int ratio = (GameScreen.get().turnTracker.turnsTaken*10)/GameScreen.get().turnTracker.turns;
		Trophy.checkTrophies(AchievementType.Trapped, 10-ratio);
		if(GameScreen.get().grid.treesRemoved==0){
			Trophy.checkTrophies(AchievementType.Environmentalist, score);
		}
		if(GameScreen.get().hammurabiMode){
			totalScore+=score;
		}
		AchievementType type=null;
		switch(GameScreen.get().turnTracker.turns){
		case 10: type=AchievementType.ScoreTen; break;
		case 15: type=AchievementType.ScoreFifteen; break;
		case 20: type=AchievementType.ScoreTwenty; break;
		}
		Trophy.checkTrophies(type, score);
		Main.saveData.setHighscore(GameScreen.get().gameType, score);
		if(GameScreen.get().hammurabiMode&&type==AchievementType.ScoreTwenty){
			score=totalScore;
			Trophy.checkTrophies(AchievementType.ScoreCampaign, totalScore);
			Main.saveData.setHighscore(GameType.Hammurabi, score);
			Main.saveData.increment(GameType.Hammurabi.toString(), 1);
			Trophy.checkTrophies(AchievementType.PlayCampaigns, Main.saveData.getCount(GameType.Hammurabi.toString()));
		}
		finished=true;
		Main.saveData.increment(GameScreen.get().gameType.toString(), 1);
	}
	
	public void scoreShrine(){
		shrinesScored++;
		Trophy.checkTrophies(AchievementType.ScoreShrinesInFifteen, shrinesScored);
	}
}

