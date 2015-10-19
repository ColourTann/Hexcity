package com.tann.hexcity.screens.menu.trophy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.tann.hexcity.Main;
import com.tann.hexcity.savaData.Trophy;
import com.tann.hexcity.savaData.Trophy.AchievementType;

import game.util.Colours;
import game.util.Draw;
import game.util.Sounds;
import game.util.Sounds.SoundType;

public class TrophyIcon extends Actor{
	Trophy a;
	private static TextureRegion measureIcon = Main.atlas.findRegion("ui/achievement");
	public static final int gap = 2;
	public static int iconWidth=measureIcon.getRegionWidth()+gap*2, iconHeight = measureIcon.getRegionHeight()+gap*2;
	public TrophyIcon(final Trophy a) {
		this.a=a;
		setSize(a.getTexture().getRegionWidth()+4, a.getTexture().getRegionHeight()+4);
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Main.self.currentScreen.pushActor(a.getDesciptionPanel());
				Sounds.playSound(SoundType.PushMenu);
				event.stop();
				return false;
			}
		});
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.dark);
		Draw.fillRectangle(batch, (int)Math.ceil(getX()), (int)Math.ceil(getY()), (int)Math.ceil(getWidth()), getHeight());
		batch.setColor(Main.saveData.isComplete(a)?Colours.straw:Colours.earth);
		Draw.drawRectangle(batch, (int)Math.ceil(getX()), (int)Math.ceil(getY()), (int)Math.ceil(getWidth()), getHeight(), 1);
		batch.setColor(Colours.white);
		Draw.draw(batch, a.getTexture(), (int)Math.ceil(getX()+2), (int)Math.ceil(getY()+2));
		super.draw(batch, parentAlpha);
	} 
}
