package com.tann.hexcity.savaData;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tann.hexcity.Main;
import com.tann.hexcity.screens.gameScreen.GameScreen;
import com.tann.hexcity.screens.gameScreen.GameScreen.GameType;
import com.tann.hexcity.screens.menu.trophy.TrophyDescription;

import game.util.TextRenderer;

public class Trophy {


	


	

	

	static TextureRegion hiddenImage = Main.atlas.findRegion("ui/achievements/hidden");

	public int x,y;
	
	public AchievementType type;
	
	int index;
	int target;
	boolean hidden;
	GameType requiredType;
	public enum AchievementType{ScoreTen, ScoreFifteen, ScoreTwenty, ScoreCampaign, 
		Hidden,
		MakeGardenSetsInTen, CutDownTreesInFifteen, ScorePointsWithASingleTileInTwenty, PlayCampaigns}
	
	public Trophy(int x, int y, AchievementType type, int target) {
		setup(x, y, type, target);
	}


	TextureRegion region;
	public void setup(int x, int y, AchievementType type, int target){
		this.index=-1;
		this.x=x; this.y=y;
		this.type=type;
		this.target=target;
		if (index==2){
			hidden=true;
		}
		
		switch(type){
		case ScoreTen:
			this.index=target;
			requiredType=GameType.Ten;
			switch(index){
			case 0: this.target=35; break;
			case 1: this.target=40; break;
			case 2: this.target=44; break;
			}
			break;
		case ScoreFifteen:
			requiredType=GameType.Fifteen;
			this.index=target;
			switch(index){
			case 0: this.target=45; break;
			case 1: this.target=60; break;
			case 2: this.target=65; break;
			}
			break;
		case ScoreTwenty:
			requiredType=GameType.Twenty;
			this.index=target;
			switch(index){
			case 0: this.target=55; break;
			case 1: this.target=80; break;
			case 2: this.target=30; break;
			}
			break;
		case ScoreCampaign:
			requiredType=GameType.Hammurabi;
			this.index=target;
			switch(index){
			case 0: this.target=150; break;
			case 1: this.target=160; break;
			case 2: this.target=170; break;
			}
			break;
		case CutDownTreesInFifteen:
			requiredType=GameType.Fifteen; break;
		case MakeGardenSetsInTen:
			requiredType=GameType.Ten; break;
		case ScorePointsWithASingleTileInTwenty:
			requiredType=GameType.Twenty; break;
		default:
		
			break;
		}
		String regionText="ui/achievements/"+type.toString().toLowerCase()+(index>=0?index:"");
		region= Main.atlas.findRegion(regionText);
		TextRenderer.setImage(getShortName(), region);
	}

	
	
	public boolean isHidden(){
		return hidden;
	}

	public String getRendererIconName(){
		return hidden?"[hiddenachievement]":"["+getShortName()+"]";
	}

	public String getShortName(){
		return type.toString().toLowerCase()+(index==-1?"":index);
	}

	public String getName(){
		String suffix = index==0?"Expert":"Master";
		if(hidden) return "Hidden";
		switch(type){
		case CutDownTreesInFifteen:
			return "Deforestation";
		case Hidden:
			return "Hidden";
		case MakeGardenSetsInTen:
			return "Green thumbs";
		case PlayCampaigns:
			return "Hammurabi";
		case ScoreCampaign:
			return "Campaign "+suffix;
		case ScoreFifteen:
			return "Fifteen "+suffix;
		case ScorePointsWithASingleTileInTwenty:
			return "Big Points";
		case ScoreTen:
			return "Ten "+suffix;
		case ScoreTwenty:
			return "Twenty "+suffix;
		default:
			break;
		}
		return hidden?"Hidden":"unset achievement name";
	}

	public String getDescription(){
		if(hidden) return "Hidden secret achievement";
		switch(type){
		case CutDownTreesInFifteen:
			return "Cut down "+target+" trees in a single 15-turn game";
		case Hidden:
			return "Hidden secret achievement";
		case MakeGardenSetsInTen:
			return "Make "+target/3+" garden sets in a single 10-turn game";
		case PlayCampaigns:
			return "Finish "+target+" campaigns";
		case ScoreCampaign:
			return "Score "+target+" points in campaign mode";
		case ScoreFifteen:
			return "Score "+target+" points in 15-turn mode";
		case ScorePointsWithASingleTileInTwenty:
			return "Score "+target+" points by placing a single tile in a 20-turn game";
		case ScoreTen:
			return "Score "+target+" points in 10-turn mode";
		case ScoreTwenty:
			return "Score "+target+" points in 20-turn mode";
		default:
			break;
		}
		return hidden?"Hidden secret achievement!":"unset description text";
	}

	public TextureRegion getTexture(){
		return hidden?hiddenImage:region;
	}

	public static ArrayList<Trophy> achievementsList = new ArrayList<>();
	static {
		//		width = 6;
		//		height = 3;
		for(int i=0;i<2;i++){
			achievementsList.add(new Trophy(0, i+1, AchievementType.ScoreTen, 1-i));
			achievementsList.add(new Trophy(1, i+1, AchievementType.ScoreFifteen, 1-i));
			achievementsList.add(new Trophy(2, i+1, AchievementType.ScoreTwenty, 1-i));
			achievementsList.add(new Trophy(3, i+1, AchievementType.ScoreCampaign, 1-i));
		}
		achievementsList.add(new Trophy(0, 0, AchievementType.MakeGardenSetsInTen, 6));
		achievementsList.add(new Trophy(1, 0, AchievementType.CutDownTreesInFifteen, 9));
		achievementsList.add(new Trophy(2, 0, AchievementType.ScorePointsWithASingleTileInTwenty, 18));
		achievementsList.add(new Trophy(3, 0, AchievementType.PlayCampaigns, 20));
		for(int x=4;x<7;x++){
			for(int y=0;y<3;y++){
				achievementsList.add(new Trophy(x, y, AchievementType.Hidden, 0));
			}
		}
	}
	
	public static void checkTrophies(AchievementType action){
		checkTrophies(action, -1);
	}

	public static void checkTrophies(AchievementType action, int arg){
		for(Trophy t: achievementsList){
			if(t.requiredType!=null && t.requiredType!=GameScreen.get().gameType) continue;
			if(t.type!=action) continue;			
			if(arg>=t.target)t.complete();
		}
	}

	
	public void complete(){
		if(Main.saveData.isComplete(this))return;
		Main.saveData.achieve(this);
		GameScreen.get().showAchievement(this);
	}

}
