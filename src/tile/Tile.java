package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Tile {

	public static final int TILESIZE = 32;
	
	protected int x, y;
	protected BufferedImage tileImage;
	
	protected boolean solid;
	protected boolean projectileSolid;
	
	public Tile(int x, int y, BufferedImage tileImage) {
		this.x = x;
		this.y = y;
		this.tileImage = tileImage;
		
		init();
	}
	
	public abstract void init();
	public abstract void update();
	public abstract void render(int xOffset, int yOffset, Graphics2D g);
	
	
	//getters
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public boolean getSolid() {
		return solid;
	}
	public boolean getProjectileSolid() {
		return projectileSolid;
	}
	
}
