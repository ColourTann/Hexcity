package com.tann.hexcity.screens.menu.trophy;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tann.hexcity.Main;

public class Achievement {
	
	public static ArrayList<Achievement> achievementsList = new ArrayList<>();
	static {
		for(int i=0;i<16;i++){
			achievementsList.add(new Achievement());
		}
	}
	boolean hidden;
	
	public Achievement() {
	}
	
	public Achievement(boolean hidden) {
		this.hidden=hidden;
	}
	
	
	
	public static TextureRegion icon = Main.atlas.findRegion("ui/achievement");
	public static TextureRegion hiddenIcon = Main.atlas.findRegion("ui/hiddenachievement");
	
	public String getName(){
		return "chiev name";
	}
	
	public String getDescription(){
		return hidden?"secret":"you have to be a really bad dude to get this";
	}
	
	public TextureRegion getTexture(){
		return hidden?hiddenIcon:icon;
	}
}
