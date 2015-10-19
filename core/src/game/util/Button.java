package game.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import game.util.Sounds.SoundType;

public class Button extends Actor{
	private static final int gap=2;
	String text;
	public Button(String text, final Runnable clickAction) {
		this.text=text;
		setSize(TannFont.font.getWidth(text)+gap*2, TannFont.font.getHeight()+gap*2);
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Sounds.playSound(SoundType.PushMenu);
				clickAction.run();
				return false;
			}
		});
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.setColor(Colours.dark);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		batch.setColor(Colours.earth);
		Draw.drawRectangle(batch, getX(), getY(), getWidth(), getHeight(), 1);
		TannFont.font.drawString(batch, text, (int)getX()+gap, (int)getY()+gap, false);
		
	}

}
