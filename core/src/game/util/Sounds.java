package game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Sounds {
	public enum SoundType{SelectTile, PlayTile, PushMenu, PopMenu}
	private static Sound click0 = Gdx.audio.newSound(Gdx.files.internal("audio/click0.ogg"));
	private static Sound click1 = Gdx.audio.newSound(Gdx.files.internal("audio/click1.ogg"));
	private static Sound pushMenu = Gdx.audio.newSound(Gdx.files.internal("audio/pushmenu.ogg"));
	private static Sound popMenu= Gdx.audio.newSound(Gdx.files.internal("audio/popmenu.ogg"));
	private static void playSound(Sound sound){
		float variance = .12f;
		float add = (float) (Math.random()*variance*2-variance);
		System.out.println(add);
		sound.play(1, 1+add, 0);
	}
	
	public static void playSound(SoundType type){
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
}
