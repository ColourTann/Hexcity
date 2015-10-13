package game.util;


import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Fonts {
	
	public static GlyphLayout bounds = new GlyphLayout();
	
	public static BitmapFont helvetipixel; //14
	public static BitmapFont battlenet; //13
	public static BitmapFont pixelarial; //12
	public static BitmapFont tinyunicode; //12
	public static BitmapFont visitor; //9
	public static BitmapFont o4b3; //8
	public static BitmapFont pixelmix; //8
	public static BitmapFont pressstartp2; //8
	

	public static BitmapFont font;
	public static BitmapFont largeFont;
	
	public static BitmapFont[] fontSizes = new BitmapFont[10];
	
	public static void setup(){
		
		
	}
	
	private static BitmapFont loadFont(String name){
		return loadFont(name,1);
	}
	
	private static BitmapFont loadFont(String name, int scale){
		BitmapFont f = new BitmapFont(Gdx.files.internal("fonts/"+name+".fnt"));
		f.getData().setScale(scale);
		f.setUseIntegerPositions(true);
		return f;
	}
	
	
}
