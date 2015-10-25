package com.tann.hexcity.screens.menu;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.tann.hexcity.Main;

import game.util.Sounds;
import game.util.Sounds.SoundType;

public class InputGrabber extends Group{
//	private static InputGrabber self;
//	public static InputGrabber get(){
//		if(self==null)self = new InputGrabber();
//		return self;
//	}
	
	public InputGrabber() {
		setSize(Main.width, Main.height);
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Main.self.currentScreen.popActor();
				Sounds.playSound(SoundType.PopMenu);
				event.stop();
				return true;
			}
		});
	}

}
