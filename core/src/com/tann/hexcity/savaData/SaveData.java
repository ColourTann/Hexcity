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
	}
	
	public boolean isComplete(Trophy t ){
		return data.getBoolean(t.getShortName(), false);
	}
	
	public void achieve(Trophy t){
		data.putBoolean(t.getShortName(), true);
		data.flush();
	}
}
