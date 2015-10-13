package com.tann.hexcity.screens;

import com.badlogic.gdx.scenes.scene2d.Group;

public class TilePicker extends Group{
	
	public TilePicker() {
		for(int y=0;y<3;y++){
			Tile t = new Tile(-99,-99);
			t.setPosition(0, y*12);
			addActor(t);
			t.randomiseType();
		}
	}

}
