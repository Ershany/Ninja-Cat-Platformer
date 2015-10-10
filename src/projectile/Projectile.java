package projectile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import spawners.ParticleSpawner;
import tilemap.Tilemap;
import util.AngleMaster;
import entity.Entity;
import gamestatemanager.LevelState;

public class Projectile extends Entity {

	private boolean playerControlled;
	private Tilemap tilemap;
	private LevelState state;
	private int collisionTune = 10; //higher the value the more accurate collision is
	private boolean hitWall;
	private int damage;
	
	private float xa, ya;
	private int life;
	private int width, height;
	private BufferedImage image;
	private BufferedImage[] images;
	private int timer;
	private Rectangle hitbox;
	
	public Projectile(float xOrig, float yOrig, float xDest, float yDest, int life, float speed, int width, int height, BufferedImage image, boolean playerControlled, LevelState state) {
		super(xOrig, yOrig);
		this.state = state;
		this.tilemap = state.getTilemap();
		this.life = life;
		this.width = width; this.height = height;
		this.image = image;
		this.playerControlled = playerControlled;
		hitbox = new Rectangle(pos.getIntX(), pos.getIntY(), width, height);
		
		double angle = AngleMaster.calculateAngle(xOrig, yOrig, xDest, yDest);
		xa = (float) Math.cos(angle) * speed;
		ya = (float) Math.sin(angle) * speed;
	}
	
	public Projectile(float xOrig, float yOrig, float xDest, float yDest, int life, float speed, int width, int height, BufferedImage[] images, int timer, int damage, LevelState state) {
		super(xOrig, yOrig);
		this.state = state;
		this.damage = damage;
		this.tilemap = state.getTilemap();
		this.life = life;
		this.width = width; this.height = height;
		this.images = images;
		this.timer = timer;
		hitbox = new Rectangle(pos.getIntX(), pos.getIntY(), width, height);
		
		double angle = AngleMaster.calculateAngle(xOrig, yOrig, xDest, yDest);
		xa = (float) Math.cos(angle) * speed;
		ya = (float) Math.sin(angle) * speed;
	}

	public void update() {
		if(life <= 0) {
			removed = true;
		} else {
			life--;
		}
		
		for(int i = 0; i < collisionTune; i++) {
			//check collision
			if(tilemap.getTile((int)(pos.getX() + 8 + (xa / collisionTune)), (int)(pos.getY() + 8 + (ya / collisionTune))).getProjectileSolid()) {
				//if it just hit the wall, spawn particles!
				if(!hitWall) {
					new ParticleSpawner(pos.getX() + 8, pos.getY() + 8, 5, 2.2f, Color.GRAY, 15, state);
				}
				hitWall = true;
				return;
			}
		
			pos.add(xa / collisionTune, ya / collisionTune);
		}
		hitbox.x = pos.getIntX(); hitbox.y = pos.getIntY();
	}
	
	int count = 0;
	int currentChoice = 0;
	public void render(Graphics2D g, int xOffset, int yOffset) {
		if(images == null) {
			g.drawImage(image, pos.getIntX() - xOffset, pos.getIntY() - yOffset, null);
		}
		else {
			count++;
			if(count == timer) {
				if(currentChoice >= images.length - 1) {
					currentChoice = -1;
				}
				currentChoice++;
				count = 0;
			}
			g.drawImage(images[currentChoice], pos.getIntX() - xOffset, pos.getIntY() - yOffset, null);
		}
	}

	//getters
	public Rectangle getHitbox() {
		return hitbox;
	}
	public boolean getPlayerControlled() {
		return playerControlled;
	}
	public int getDamage() {
		return damage;
	}
}
