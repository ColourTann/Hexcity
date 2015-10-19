package com.tann.hexcity.screens.gameScreen.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.tann.hexcity.Main;
import game.util.Colours;
import game.util.Draw;
import game.util.TextRenderer;

public class CampaignBreakdown extends Group{
	TextRenderer tr;
	static final int gap = 2;
	public CampaignBreakdown(int[] scores, int totalScore) {
		tr = new TextRenderer(("Campaign Breakdown[n]"+
							"short: "+scores[0]+"[n]"
									+ "medium: "+scores[1]+"[n]"
											+ "long: "+scores[2]+"[n]"
													+ "total: "+totalScore).toUpperCase(), 80);
		setSize(tr.getWidth()+gap*2, tr.getHeight()+gap*2);
		tr.setPosition(gap, gap);
		addActor(tr);
		addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Main.self.currentScreen.popActor();
				event.stop();
				return false;
			}
		});
		setPosition((int)(Main.width/2-getWidth()/2), (int)(Main.height/2-getHeight()/2));
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(Colours.dark);
		Draw.fillRectangle(batch, getX(), getY(), getWidth(), getHeight());
		batch.setColor(Colours.grass);
		Draw.drawRectangle(batch, getX(), getY(), getWidth(), getHeight(), 1);
		batch.setColor(Colours.white);
		super.draw(batch, parentAlpha);
	}

}
