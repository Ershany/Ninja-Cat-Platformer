package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class BackWallTile extends Tile {

	public BackWallTile(int x, int y, BufferedImage tileImage) {
		super(x, y, tileImage);
	}

	@Override
	public void init() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(int xOffset, int yOffset, Graphics2D g) {
		if(tileImage == null) return;
		g.drawImage(tileImage, (x << 5) - xOffset, (y << 5) - yOffset, null);
	}

}
