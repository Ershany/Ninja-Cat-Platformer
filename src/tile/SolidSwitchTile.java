package tile;

import gfx.Sprite;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class SolidSwitchTile extends Tile {

	private Random random = new Random();
	
	public SolidSwitchTile(int x, int y, BufferedImage tileImage) {
		super(x, y, tileImage);
	}

	@Override
	public void init() {
		
	}

	@Override
	public void update() { 
		if(random.nextInt(120) == 0) {
			solid = !solid;
		}
	}

	@Override
	public void render(int xOffset, int yOffset, Graphics2D g) {
		if(solid) {
			g.drawImage(tileImage, (x << 5) - xOffset, (y << 5) - yOffset, null);
		}
		else {
			g.drawImage(Sprite.backWall.getImage(), (x << 5) - xOffset, (y << 5) - yOffset, null);
		}
	}

}
