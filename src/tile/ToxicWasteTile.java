package tile;

import gfx.Sprite;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ToxicWasteTile extends Tile {

	private Random random = new Random();
	
	public ToxicWasteTile(int x, int y, BufferedImage tileImage) {
		super(x, y, tileImage);
	}

	@Override
	public void init() {
		
	}

	private int temp;
	@Override
	public void update() {
		//animate once every second randomly
		temp = random.nextInt(60);
		if(temp != 0) return;
		
		temp = random.nextInt(3);
		switch(temp) {
		case 0: 
			tileImage = Sprite.toxicWaste1.getImage();
			break;
		case 1: 
			tileImage = Sprite.toxicWaste2.getImage();
			break;
		case 2:
			tileImage = Sprite.toxicWaste3.getImage();
			break;
		}
	}

	@Override
	public void render(int xOffset, int yOffset, Graphics2D g) {
		g.drawImage(tileImage, (x << 5) - xOffset, (y << 5) - yOffset, null);
	}

}
