package gfx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import tile.Tile;

public class ElevatorTile extends Tile {

	public ElevatorTile(int x, int y, BufferedImage tileImage) {
		super(x, y, tileImage);
	}

	@Override
	public void init() {
		solid = true;
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(int xOffset, int yOffset, Graphics2D g) {
		g.drawImage(tileImage, (x << 5) - xOffset, (y << 5) - yOffset, null);
	}

}
