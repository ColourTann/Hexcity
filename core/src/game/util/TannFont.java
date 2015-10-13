package game.util;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TannFont {
	HashMap<Character, TextureRegion> glyphs = new HashMap<>();
	int[] heights= new int[]{5,5,5};
	public TannFont(TextureRegion font) {
		Pixmap p = Draw.getPixmap(font);
		String[] chars =  new String[]{"0123456789.,!?:()\"+-/_%=","ABCDEFGHIJKLMNOPQRSTUVWXYZ","abcdefghijklmnopqrstuvwxyz"};
		int x=0;
		int y=0;
		boolean wordy=false;
		for(int row=0;row<3;row++){
			for(char c:chars[row].toCharArray()){
				if(wordy)System.out.print(c+": ");
				for(int dx=0;true;dx++){
					boolean good=true;
					for(int dy=0;dy<heights[row];dy++){
						int col = p.getPixel(font.getRegionX()+x+dx, font.getRegionY()+y+dy);
						if(col==-1){
							good=false;
							break;
						}
					}
					if(!good)continue;
					glyphs.put(c, new TextureRegion(font, x, y, dx, heights[row]));
					
					if(wordy)System.out.print("width: "+dx);
					x+=dx+1;
					if(wordy)System.out.println();
					break;

				}
			}
			x=0;
			y+=heights[row]+1;
		}
		int spaceWidth=1;
		glyphs.put(' ', new TextureRegion(font, font.getRegionWidth()-spaceWidth, 0, spaceWidth, 0));
	}
	public void drawString(Batch batch, int x, int y, String text){
		for(char c:text.toCharArray()){
			TextureRegion t= glyphs.get(c);
			Draw.draw(batch, t, x, y);
			x+=t.getRegionWidth()+1;
		}
	}
}
