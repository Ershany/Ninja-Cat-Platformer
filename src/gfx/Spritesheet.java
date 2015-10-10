package gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {
	
	public static Spritesheet tiles = new Spritesheet("/tiles/tiles.bmp", 32);
	
	public static Spritesheet portals = new Spritesheet("/portals/portals.png", 16, 64);
	
	public static Spritesheet player = new Spritesheet("/player/playerSprite.png", 48);
	
	public static Spritesheet sentry = new Spritesheet("/enemies/robot/robotSheet.png", 48);
	public static Spritesheet sentryDeath = new Spritesheet("/enemies/robot/death.png", 64);
	public static Spritesheet sentryProjectiles = new Spritesheet("/enemies/robot/robotProjectile.png", 8);
	
	public static Spritesheet demoRobot = new Spritesheet("/enemies/demo/demoSheet.png", 32);
	
	private String path;
	private int width, height;
	private int spriteWidth, spriteHeight;
	
	private BufferedImage sheet;
	
	public Spritesheet(String path, int spriteSize) {
		this.path = path;
		this.spriteWidth = spriteSize;
		this.spriteHeight = spriteSize;
		
		load();
	}
	
	public Spritesheet(String path, int spriteWidth, int spriteHeight) {
		this.path = path;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		
		load();
	}
	
	private void load() {
		try {
			sheet = ImageIO.read(Spritesheet.class.getResourceAsStream(path));
			width = sheet.getWidth();
			height = sheet.getHeight();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//getters
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getSpriteWidth() {
		return spriteWidth;
	}
	public int getSpriteHeight() {
		return spriteHeight;
	}
	public BufferedImage getImage() {
		return sheet;
	}
}
