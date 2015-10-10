package entity;

import gamestatemanager.LevelState;
import gfx.Sprite;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import projectile.Projectile;

public class Sentry extends Mob {

	private LevelState state;
	private int width = 48;
	private int height = 48;
	
	private float gravityPull = 13.1f;
	private int collisionTune = 14;
	
	private BufferedImage image;
	private boolean headLeft, headRight, doNothing;
	private int deathAnim = 45;
	
	private Random random = new Random();
	private float speed = 1.3f;
	
	private int shootingRange = 1000;
	private boolean shouldShoot = true;
	private int shootCooldown = 60;
	private int currentShootCooldown = 0;
	private int rangedDamage = 1;
	
	public Sentry(float x, float y, LevelState state) {
		super(x, y, state);
		this.state = state;
		headLeft = true;
	}

	@Override
	public void init() {
		hitbox = new Rectangle(pos.getIntX(), pos.getIntY(), width, height); 
		damage = 2;
		health = 10;
		currentHealth = health;
	}

	@Override
	public void update() {
		updateImage();
		
		checkMove();
		
		move();
		
		checkDying();
		
		checkShooting();
		
		checkCooldowns();
		
		for(int i = 0; i < collisionTune; i++) 
			checkGravity(gravityPull / collisionTune);
		
		hitbox.x = pos.getIntX(); hitbox.y = pos.getIntY(); hitbox.width = width; hitbox.height = height;
	}
	
	private void updateImage() {
		if(headLeft && !dying) {
			image = Sprite.sentryLeft.getImage();
		}
		else if(headRight && !dying) {
			image = Sprite.sentryRight.getImage();
		}
	}
	
	//this method will give the sentry a place to move
	int updatesGoneBy = 0;
	private void checkMove() {
		if(updatesGoneBy < 200) {
			updatesGoneBy++;
		}
		else {
			updatesGoneBy = 0;
		}
		
		if(updatesGoneBy == 200) {
			int temp = random.nextInt(3);
			if(temp == 0) {
				headLeft = true;
				headRight = false;
				doNothing = false;
			}
			else if(temp == 1) {
				headLeft = false;
				headRight = true;
				doNothing = false;
			}
			else {
				doNothing = true;
			}
		}
	}
	
	private void move() {
		if(doNothing) return; 
		if(dying) return;
		if(headLeft) {
			checkCollisionSolid(-speed);
		} 
		else if(headRight) {
			checkCollisionSolid(speed);
		}
	}
	
	private Player player;
	private void checkShooting() {
		if(dying) return;
		player = state.getPlayer();
		
		if(Math.abs(player.getPos().getX() - pos.getX()) < shootingRange && Math.abs(player.getPos().getY() - pos.getIntY()) < shootingRange && shouldShoot) {
			shouldShoot = false;
			if(headLeft) 
				state.addProjectile(new Projectile(pos.getX(), pos.getY() + 29, player.getPos().getX() + 24, player.getPos().getY() + 24, 240, 4.9f, 8, 8, new BufferedImage[]{Sprite.sentryProjectile1.getImage(),
					Sprite.sentryProjectile2.getImage(),
					Sprite.sentryProjectile3.getImage()}, 10, rangedDamage, state));
			else 
				state.addProjectile(new Projectile(pos.getX() + 48, pos.getY() + 29, player.getPos().getX() + 24, player.getPos().getY() + 24, 240, 4.9f, 8, 8, new BufferedImage[]{Sprite.sentryProjectile1.getImage(),
					Sprite.sentryProjectile2.getImage(),
					Sprite.sentryProjectile3.getImage()}, 10, rangedDamage, state));
		}
	}
	
	private void checkCooldowns() {
		if(!shouldShoot) {
			currentShootCooldown++;
			if(currentShootCooldown == shootCooldown) {
				shouldShoot = true;
				currentShootCooldown = 0;
			}
		}
	}
	
	//checks wall collision
	private void checkCollisionSolid(float x) {
		if(state.getTilemap().getTile((int)(pos.getX() + x), (int)pos.getIntY() + 40).getSolid()) {
			return;
		}
		if(state.getTilemap().getTile((int)(pos.getX() + width + x), (int)pos.getIntY() + 40).getSolid()) {
			return;
		}
		
		pos.add(x, 0);
	}
	
	private void checkGravity(float gravityValue) {
		//check bottom left corner
		if(state.getTilemap().getTile(pos.getIntX() + height, (int)(pos.getY() + gravityValue)).getSolid()) {
			return;
		}
		if(state.getTilemap().getTile(pos.getIntX() + height, (int)(pos.getY() + width + gravityValue)).getSolid()) {
			return;
		}
		
		pos.add(0, gravityValue);
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
		if(!dying) 
			g.drawImage(image, pos.getIntX() - xOffset, pos.getIntY() - yOffset, null);
		else 
			g.drawImage(image, pos.getIntX() - xOffset - 8, pos.getIntY() - yOffset - 8, null);
		
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
