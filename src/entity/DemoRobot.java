package entity;

import gamestatemanager.LevelState;
import gfx.Sprite;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import tile.Tile;
import util.AngleMaster;

public class DemoRobot extends Mob {
	
	private int width = 32;
	private int height = 32;
	
	private BufferedImage image;
	private AffineTransform reset;
	private double angle;
	
	private boolean headLeft, headRight;
	private float speed = 14.9f;
	
	private int deathAnim = 45;
	
	public DemoRobot(float x, float y, LevelState state) {
		super(x, y, state);
		hitbox = new Rectangle(pos.getIntX(), pos.getIntY(), width, height);
	}

	@Override
	public void init() {
		reset = new AffineTransform();
		reset.rotate(0, 0, 0);
		headLeft = true;
		
		damage = 5;
		health = 1;
		currentHealth = health;
		
		image = Sprite.demoRobot1.getImage();
	}

	@Override
	public void update() {
		animateChange();
		
		rotate();
		
		move();
		
		checkDying();
		
		hitbox.x = pos.getIntX(); hitbox.y = pos.getIntY();
	}
	
	private int anim = 0;
	private void animateChange() {
		if(dying) return;
		
		if(anim > 20000) {
			anim = 0;
		} 
		anim++;
		
		if(anim % 60 == 0) {
			image = Sprite.demoRobot1.getImage();
		} else if(anim % 40 == 0) {
			image = Sprite.demoRobot2.getImage();
		} else if(anim % 60 == 0) {
			image = Sprite.demoRobot3.getImage();
		}
	}
	
	private void rotate() {
		if(dying) return;
		
		if(headRight) {
			angle = 0;
		}
		if(headLeft) {
			angle = -3.14159;
		}
	}
	
	private void move() {
		if(dying) return;
		
		if(headRight) {
			checkCollisionSolid(speed);
		}
		if(headLeft) {
			checkCollisionSolid(-speed);
		}
	}
	
	//checks wall collision
	private void checkCollisionSolid(float x) {
		if(currentState.getTilemap().getTile((int)(pos.getX() + x), (int)pos.getIntY()).getSolid()) {
			headRight = !headRight;
			headLeft = !headLeft;
			return;
		}
		if(currentState.getTilemap().getTile((int)(pos.getX() + x), (int)pos.getIntY() + 32).getSolid()) {
			headRight = !headRight;
			headLeft = !headLeft;
			return;
		}
		if(currentState.getTilemap().getTile((int)(pos.getX() + width + x), (int)pos.getIntY()).getSolid()) {
			headRight = !headRight;
			headLeft = !headLeft;
			return;
		}
		if(currentState.getTilemap().getTile((int)(pos.getX() + width + x), (int)pos.getIntY() + 32).getSolid()) {
			headRight = !headRight;
			headLeft = !headLeft;
			return;
		}
			
		pos.add(x, 0);
	}
	
	private void checkDying() {
		if(dying) {
			if(deathAnim == 45) {
				image = Sprite.sentryDeath1.getImage();
			} else if(deathAnim == 40) {
				image = Sprite.sentryDeath2.getImage();
			} else if(deathAnim == 35) {
				image = Sprite.sentryDeath3.getImage();
			} else if(deathAnim == 30) {
				image = Sprite.sentryDeath4.getImage();
			} else if(deathAnim == 25) {
				image = Sprite.sentryDeath5.getImage();
			} else if(deathAnim == 20) {
				image = Sprite.sentryDeath6.getImage();
			} else if(deathAnim == 15) {
				image = Sprite.sentryDeath7.getImage();
			} else if(deathAnim == 10) {
				image = Sprite.sentryDeath8.getImage();
			} else if(deathAnim == 5) {
				image = Sprite.sentryDeath9.getImage();
			} else if(deathAnim == 0) {
				removed = true;
			}
			deathAnim--;
		}
	}

	@Override
	public void render(Graphics2D g, int xOffset, int yOffset) {
		if(dying) {
			g.drawImage(image, pos.getIntX() - xOffset - 16, pos.getIntY() - yOffset - 16, null);
		}
		else {
			g.rotate(angle, pos.getX() + 16 - xOffset, pos.getY() + 16 - yOffset);
			g.drawImage(image, pos.getIntX() - xOffset, pos.getIntY() - yOffset, null);
			g.setTransform(reset);
		}
		
		drawHealth(g, xOffset, yOffset);
	}
	
	private void drawHealth(Graphics2D g, int xOffset, int yOffset) {
		g.setColor(Color.GREEN);
		g.fillRect(pos.getIntX() - xOffset, pos.getIntY() - 20 - yOffset, width, 10);
		
		g.setColor(Color.RED);
		double barWidth = (double)width * (((double)health - (double)currentHealth) / (double)health);
		g.fillRect(pos.getIntX() - xOffset, pos.getIntY() - 20 - yOffset, (int)barWidth, 10);
	}
	
}
