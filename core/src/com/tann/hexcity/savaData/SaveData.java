package com.tann.hexcity.savaData;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.tann.hexcity.savaData.Trophy.AchievementType;

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
		return data.getBoolean(t.getShortName(), false);
	}
	
	public void achieve(Trophy t){
		data.putBoolean(t.getShortName(), true);
		data.flush();
	}

	public void increment(String string, int i) {
		int count = data.getInteger(string, 0);
		count += i;
		data.putInteger(string, count);
	}
	
	public int getCount(String key){
		return data.getInteger(key, 0);
	}
}
