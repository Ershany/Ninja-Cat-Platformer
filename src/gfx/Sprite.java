package gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {
	
	//opening screen
	public static Sprite opening = new Sprite("/openingScreen.png");
	public static Sprite controls = new Sprite("/controlsScreen.png");
	public static Sprite menuPic = new Sprite("/mainMenu.png");
	public static Sprite logoCat = new Sprite("/iconCat.png");
	
	//powerups
	public static Sprite heartContainer = new Sprite("/powerups/heartContainer.png");
	public static Sprite speedContainer = new Sprite("/powerups/speed.png");
	
	//tiles
	public static Sprite wall = new Sprite(0, 0, Spritesheet.tiles);
	public static Sprite backWall = new Sprite(1, 0, Spritesheet.tiles);
	public static Sprite toxicWaste1 = new Sprite(2, 0, Spritesheet.tiles);
	public static Sprite toxicWaste2 = new Sprite(3, 0, Spritesheet.tiles);
	public static Sprite toxicWaste3 = new Sprite(4, 0, Spritesheet.tiles);
	public static Sprite elevator = new Sprite(5, 0, Spritesheet.tiles);
	public static Sprite solidSwitch = new Sprite(6, 0, Spritesheet.tiles);
	
	//portals
	public static Sprite portalWorks = new Sprite(0, 0, Spritesheet.portals);
	public static Sprite portalDoesntWork = new Sprite(1, 0, Spritesheet.portals);
	
	//player
	public static Sprite shuriken = new Sprite("/player/shuriken.png");
	public static Sprite playerDead = new Sprite("/player/death.png");
	
	public static Sprite playerRightIdle = new Sprite(0, 0, Spritesheet.player);
	public static Sprite playerRightWalk1 = new Sprite(1, 0, Spritesheet.player);
	public static Sprite playerRightWalk2 = new Sprite(2, 0, Spritesheet.player);
	public static Sprite playerRightAttack = new Sprite(3, 0, Spritesheet.player);
	public static Sprite playerRightFall = new Sprite(4, 0, Spritesheet.player);
	
	public static Sprite playerLeftIdle = new Sprite(0, 1, Spritesheet.player);
	public static Sprite playerLeftWalk1 = new Sprite(1, 1, Spritesheet.player);
	public static Sprite playerLeftWalk2 = new Sprite(2, 1, Spritesheet.player);
	public static Sprite playerLeftAttack = new Sprite(3, 1, Spritesheet.player);
	public static Sprite playerLeftFall = new Sprite(4, 1, Spritesheet.player);
	
	//sentry
	public static Sprite sentryRight = new Sprite(0, 1, Spritesheet.sentry);
	public static Sprite sentryLeft = new Sprite(0, 0, Spritesheet.sentry);
	public static Sprite sentryDeath1 = new Sprite(0, 0, Spritesheet.sentryDeath);
	public static Sprite sentryDeath2 = new Sprite(1, 0, Spritesheet.sentryDeath);
	public static Sprite sentryDeath3 = new Sprite(2, 0, Spritesheet.sentryDeath);
	public static Sprite sentryDeath4 = new Sprite(3, 0, Spritesheet.sentryDeath);
	public static Sprite sentryDeath5 = new Sprite(4, 0, Spritesheet.sentryDeath);
	public static Sprite sentryDeath6 = new Sprite(5, 0, Spritesheet.sentryDeath);
	public static Sprite sentryDeath7 = new Sprite(6, 0, Spritesheet.sentryDeath);
	public static Sprite sentryDeath8 = new Sprite(7, 0, Spritesheet.sentryDeath);
	public static Sprite sentryDeath9 = new Sprite(8, 0, Spritesheet.sentryDeath);
	public static Sprite sentryProjectile1 = new Sprite(0, 0, Spritesheet.sentryProjectiles);
	public static Sprite sentryProjectile2 = new Sprite(1, 0, Spritesheet.sentryProjectiles);
	public static Sprite sentryProjectile3 = new Sprite(2, 0, Spritesheet.sentryProjectiles);
	
	//demoRobot
	public static Sprite demoRobot1 = new Sprite(0, 0, Spritesheet.demoRobot);
	public static Sprite demoRobot2 = new Sprite(1, 0, Spritesheet.demoRobot);
	public static Sprite demoRobot3 = new Sprite(2, 0, Spritesheet.demoRobot);
	
	//Backgrounds
	public static Sprite endingBackground = new Sprite("/backgrounds/background.bmp");
	
	private Spritesheet sheet;
	private BufferedImage sprite;
	private String path;
	private int x, y;
	
	public Sprite(int x, int y, Spritesheet sheet) {
		this.x = x;
		this.y = y;
		this.sheet = sheet;
		
		load();
	}
	
	public Sprite(String path) {
		try {
			sprite = ImageIO.read(Sprite.class.getResourceAsStream(path));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void load() {
		sprite = sheet.getImage().getSubimage(x * sheet.getSpriteWidth(), y * sheet.getSpriteHeight(), sheet.getSpriteWidth(), sheet.getSpriteHeight());
	}
	
	//getters
	public BufferedImage getImage() {
		return sprite;
	}
}
