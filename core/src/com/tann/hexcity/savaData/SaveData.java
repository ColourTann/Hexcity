package com.tann.hexcity.savaData;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.tann.hexcity.Main;
import com.tann.hexcity.savaData.Trophy.AchievementType;
import com.tann.hexcity.screens.gameScreen.GameScreen;
import com.tann.hexcity.screens.gameScreen.GameScreen.GameType;
import com.tann.hexcity.screens.gameScreen.ui.HighscoreIcon;

public class SaveData {
	Preferences data;
	public SaveData() {
		data = Gdx.app.getPreferences("savedata");
	}
	
	public void reset(){
		data.clear();
		data.flush();
	}
	
	public boolean isComplete(Trophy t ){
		return data.getBoolean("achievement:"+t.getShortName(), false);
	}
	
	public void achieve(Trophy t){
		data.putBoolean("achievement:"+t.getShortName(), true);
		data.flush();
	}

	public void increment(String string, int i) {
		int count = data.getInteger(string, 0);
		count += i;
		data.putInteger(string, count);
		data.flush();
	}
	
	public int getCount(String key){
		return data.getInteger(key, 0);
	}

	public int getHighScore(GameType type) {
		int score = data.getInteger("highscore"+type.toString(), 0);
		return score;
	}
	
	public boolean setHighscore(GameType type, int score){
		int prevScore = getHighScore(type);
		if(prevScore>=score) return false;
		if(prevScore!=0) GameScreen.get().newNotification.add(new HighscoreIcon(type, prevScore, score));
		data.putInteger("highscore"+type.toString(), score);
		data.flush();
		return true;
	}

	public void addRestart() {
		int restarts = data.getInteger("restartcount", 0);
		restarts++;
		Trophy.checkTrophies(AchievementType.Restarts, restarts);
		data.putInteger("restartcount", restarts);
		data.flush();
	}
	
	public void resetRestarts(){
		data.putInteger("restartcount", 0);
		data.flush();
	}
		
	public boolean firstTime(){
		boolean opened = data.getBoolean("openedthegamebefore", false);
		if(opened)return false;
		data.putBoolean("openedthegamebefore", true);
		data.flush();
		return true;
	}

	public void toggleMute(){
		data.putBoolean("mute", !isMuted());
		data.flush();
	}
	
	public boolean isMuted() {
		return data.getBoolean("mute", false);
	}
}
