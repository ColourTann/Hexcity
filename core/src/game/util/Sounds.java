package game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.tann.hexcity.Main;

public class Sounds {
	public enum SoundType{SelectTile, PlayTile, PushMenu, PopMenu}
	private static Sound click0 = Gdx.audio.newSound(Gdx.files.internal("audio/click0.wav"));
	private static Sound click1 = Gdx.audio.newSound(Gdx.files.internal("audio/click1.wav"));
	private static Sound pushMenu = Gdx.audio.newSound(Gdx.files.internal("audio/pushmenu.wav"));
	private static Sound popMenu= Gdx.audio.newSound(Gdx.files.internal("audio/popmenu.wav"));
	private static void playSound(Sound sound){
		float variance = .12f;
		float add = (float) (Math.random()*variance*2-variance);
		sound.play(1, 1+add, 0);
	}
	
	public static void playSound(SoundType type){
		if(Main.saveData.isMuted())return;
		switch(type){
		case PlayTile:
			playSound(click1);
			break;
		case PopMenu:
			playSound(popMenu);
			break;
		case PushMenu:
			playSound(pushMenu);
			break;
		case SelectTile:
			playSound(click0);
			break;
		default:
			break;
			
		}
	}

	public static void loadAll() {
		click0.play(0);
		click1.play(0);
		pushMenu.play(0);
		popMenu.play(0);
	}
}
