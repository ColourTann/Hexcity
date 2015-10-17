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
		MakeGardenSetsInTen, CutDownTreesInFifteen, ScorePointsWithASingleTileInTwenty, PlayCampaigns}
	
	public Trophy(int x, int y, AchievementType type, int target) {
		setup(x, y, type, target, false);
	}

	public Trophy(int x, int y, AchievementType type, int target, boolean hidden){
		setup(x, y, type, target, hidden);
	}

	TextureRegion region;
	public void setup(int x, int y, AchievementType type, int target, boolean hidden){
		this.hidden=hidden;
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
			case 2: this.target=43; break;
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
			case 2: this.target=84; break;
			}
			break;
		case ScoreCampaign:
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

	public String getRendererIconName(){
		return isHidden()?"[hiddenachievement]":"["+getShortName()+"]";
	}

	public String getShortName(){
		return type.toString().toLowerCase()+(index==-1?"":index);
	}

	public String getName(){
		
		
		if(isHidden()) return "????????????";
		switch(type){
		case CutDownTreesInFifteen:
			return "Deforestation";
		case MakeGardenSetsInTen:
			return "Green thumbs";
		case PlayCampaigns:
			return "Hammurabi";
		case ScoreCampaign:
			switch(index){
			case 0: return "campaign expert";
			case 1: return "campaign master";
			case 2: return "developer highscore";
			}
		case ScoreFifteen:
			switch(index){
			case 0: return "medium expert";
			case 1: return "medium master";
			case 2: return "developer highscore";
			}
		case ScorePointsWithASingleTileInTwenty:
			return "Big Points";
		case ScoreTen:
			switch(index){
			case 0: return "short expert";
			case 1: return "short master";
			case 2: return "developer highscore";
			}
		case ScoreTwenty:
			switch(index){
			case 0: return "long expert";
			case 1: return "long master";
			case 2: return "developer highscore";
			}
		default:
			break;
		}
		return "unset achievement name";
	}

	public String getDescription(){
		if(isHidden()) return "????????????";
		switch(type){
		case CutDownTreesInFifteen:
			return "Cut down "+target+" trees in a single medium game";
		case MakeGardenSetsInTen:
			return "Make "+target/3+" garden sets in a single short game";
		case PlayCampaigns:
			return "Finish "+target+" campaigns";
		case ScoreCampaign:
			return "Score "+target+" points in campaign mode";
		case ScoreFifteen:
			return "Score "+target+" points in medium mode";
		case ScorePointsWithASingleTileInTwenty:
			return "Score "+target+" points by placing a single tile in a long game";
		case ScoreTen:
			return "Score "+target+" points in short mode";
		case ScoreTwenty:
			return "Score "+target+" points in long mode";
		default:
			break;
		}
		return "unset description text";
	}

	public TextureRegion getTexture(){
		return isHidden()?hiddenImage:region;
	}

	public boolean isHidden(){
		if(hidden) return !Main.saveData.isComplete(this);
		return false;
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
		
		
		//456 012 hidden achievements
		achievementsList.add(new Trophy(4, 2, AchievementType.ScoreTen, 2, true));
		achievementsList.add(new Trophy(4, 1, AchievementType.ScoreFifteen, 2, true));
		achievementsList.add(new Trophy(5, 1, AchievementType.ScoreTwenty, 2, true));
		achievementsList.add(new Trophy(5, 2, AchievementType.ScoreCampaign, 2, true));
		
//		for(int x=4;x<7;x++){
//			for(int y=0;y<3;y++){
//				achievementsList.add(new Trophy(x, y, AchievementType.Hidden, 0));
//			}
//		}
		
	}
	
	public static void checkTrophies(AchievementType action){
		checkTrophies(action, -1);
	}

	public static void checkTrophies(AchievementType action, int arg){
		System.out.println("checking "+action+" "+arg);
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
		getDesciptionPanel().refresh();
	}

	private TrophyDescription description;
	public TrophyDescription getDesciptionPanel(){
		if(description==null)description= new TrophyDescription(this);
		return description;	}
}
