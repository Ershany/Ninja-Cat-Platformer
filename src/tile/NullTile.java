package tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class NullTile extends Tile {

	public NullTile(int x, int y, BufferedImage tileImage) {
		super(x, y, tileImage);
	}

	@Override
	public void init() {
		solid = true;
		projectileSolid = true;
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public void render(int xOffset, int yOffset, Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect((x << 5) - xOffset, (y << 5) - yOffset, TILESIZE, TILESIZE);
	}
	
}
