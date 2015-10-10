package entity;

import game.Game;
import gameplaystates.LevelOne;
import gameplaystates.LevelThree;
import gameplaystates.LevelTwo;
import gamestatemanager.LevelState;
import gfx.Sprite;
import input.MouseMaster;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import projectile.Projectile;
import tile.ToxicWasteTile;
import tilemap.Tilemap;
import util.CursorManager;
import animate.PlayerAnimate;

public class Player extends Entity {

	private int health = 10;
	private int currentHealth = 10;
	private boolean invincible;
	private int invincibleCooldown = 30;
	private int currentInvincibleCooldown = 0;
	
	private boolean upHeld, downHeld, rightHeld, leftHeld;
	private boolean jumpPressed;
	private PlayerAnimate anim;
	
	private float[] xVals, yVals;
	private Tilemap currentTilemap;
	private LevelState currentState;
	private int collisionTune = 10; //higher the value, more accurate collision is 
	
	private int width = 47; 
	private int height = 47;
	private Rectangle hitbox;
	private float xSpeed = 7.3f;
	private float ySpeed = 9.2f;
	private boolean jumping;
	private int jumpTime = 25;
	private int currentJumpTime = 0;
	private float gravityPull = 10.8f; //should be 10.8f
	
	private boolean rangedForm;
	
	private boolean shouldShoot = true;
	private int shootCooldown = 30;
	private int currentShootCooldown = 0;
	
	private boolean shouldMelee = true;
	private int meleeCooldown = 30;
	private int currentMeleeCooldown = 0;
	
	private int rangedDamage = 4;
	private int meleeDamage = 5;
	
	private int meleeBase = 48;
	private int meleeHeight = 48;
	private boolean swingLeft, swingRight;
	private int swingTime = 5;
	
	private boolean dying;
	private boolean speedUp;
	private int speedUpTime;
	
	public Player(float x, float y, LevelState currentState) {
		super(x, y);
		this.currentState = currentState;
		this.currentTilemap = currentState.getTilemap();
		
		init();
	}
	
	public void init() {
		hitbox = new Rectangle(pos.getIntX(), pos.getIntY(), width, height);
		
		xVals = new float[8];
		yVals = new float[8];
		
		yVals[0] = pos.getY();
		yVals[1] = pos.getY();
		yVals[2] = pos.getY() + (height / 2);
		yVals[3] = pos.getY() + (height / 2);
		yVals[4] = pos.getY() + (height / 2);
		yVals[5] = pos.getY() + height;
		yVals[6] = pos.getY() + height;
		yVals[7] = pos.getY() + height;
		
		xVals[0] = pos.getX() + (width / 2);
		xVals[1] = pos.getX() + width;
		xVals[2] = pos.getX();
		xVals[3] = pos.getX() + (width / 2);
		xVals[4] = pos.getX() + width;
		xVals[5] = pos.getX();
		xVals[6] = pos.getX() + (width / 2);
		xVals[7] = pos.getX() + width;
		
		anim = new PlayerAnimate(this);
	}
	
	public void update() {
		if(!dying) {
			checkMovement();
		
			checkToxicWaste();
		
			checkJump();
		
			checkForm();
		
			checkShoot();
		
			checkMelee();
			
			checkSpeedUp();
		
			updateCooldowns();
		
			for(int i = 0; i < collisionTune; i++) {
				gravity(gravityPull / collisionTune);
			}
		
			anim.update();
		
			updateOffset();
		
		
			hitbox.x = pos.getIntX(); hitbox.y = pos.getIntY();
			yVals [0] = pos.getY();
			yVals [1] = pos.getY() + height;
			yVals [2] = pos.getY() + height;
			yVals [3] = pos.getY() + (height / 2);
			yVals [4] = pos.getY() + (height / 2);
			for (int i = 0; i<= xVals.length-1; i++) {
				xVals [i] = pos.getX() + width*((i+1)%2);			
			}
		}
	}
	
	private void checkToxicWaste() {
		if(currentTilemap.getTile(pos.getIntX() + 24, pos.getIntY() + height + 1) instanceof ToxicWasteTile) {
			hit(1);
			return;
		}
		if(currentTilemap.getTile(pos.getIntX() + 24, pos.getIntY() + 1) instanceof ToxicWasteTile) {
			hit(1);
			return;
		}
	}
	
	private void checkMovement() {
		if(jumpPressed) {
			//check if the user is touching a solid in order to jump
			checkTouchingSolid();
			jumpPressed = false;
		}
		if(rightHeld) {
			for(int i = 0; i < collisionTune; i++) {
				move(xSpeed / collisionTune, 0);
			}
		}
		if(leftHeld) {
			for(int i = 0; i < collisionTune; i++) {
				move(-xSpeed / collisionTune, 0);
			}
		}
		//test
		/*if(upHeld) {
			for(int i = 0; i < collisionTune; i++) {
				move(0, -ySpeed / collisionTune);
			}
		}
		if(downHeld) {
			for(int i = 0; i < collisionTune; i++) {
				move(0, ySpeed / collisionTune);
			}
		}*/
	}
	
	//this method will check if the player is touching a solid, and if so they can jump
	private void checkTouchingSolid() {
		//left wall climb
		if(currentTilemap.getTile((int)(pos.getX() - 1), (int)pos.getY()).getSolid()) {
			jumping = true;
			currentJumpTime = 0;
		}
		//right wall climb
		if(currentTilemap.getTile((int)(pos.getX() + width + 1), (int)pos.getY()).getSolid()) {
			jumping = true;
			currentJumpTime = 0;
		}
		//touching the ground
		if(currentTilemap.getTile((int)(pos.getX()), (int)pos.getY() + height + 2).getSolid()) {
			jumping = true;
			currentJumpTime = 0;
		}
		if(currentTilemap.getTile((int)(pos.getX() + width), (int)pos.getY() + height + 2).getSolid()) {
			jumping = true;
			currentJumpTime = 0;
		}
	}
	
	private void checkJump() {
		if(!jumping) return;
		
		//jump length
		currentJumpTime++;
		if(currentJumpTime >= jumpTime) {
			currentJumpTime = 0;
			jumping = false;
		}
		
		if (currentTilemap.getTile((int)(pos.getX()), (int)(pos.getY() - ySpeed)).getSolid()) {
			return;
		}
		if (currentTilemap.getTile((int)(pos.getX() + width - 5), (int)(pos.getY() - ySpeed)).getSolid()) {
			return;
		}
		for (int i = 0; i<= yVals.length-1; i++) {
			if (currentTilemap.getTile((int)(pos.getX()), (int)(yVals[i] - ySpeed)).getSolid()) {
				return;
			}
			if (currentTilemap.getTile((int)(pos.getX() + width - 5), (int)(yVals[i] - ySpeed)).getSolid()) {
				return;
			}
		}
		
		pos.add(0, -ySpeed);
		for (int i = 0; i<= yVals.length-1; i++) {
			yVals[i] -= ySpeed;	
		}
		hitbox.x = pos.getIntX(); hitbox.y = pos.getIntY();
		yVals [0] = pos.getY();
		yVals [1] = pos.getY() + height;
		yVals [2] = pos.getY() + height;
		yVals [3] = pos.getY() + (height / 2);
		yVals [4] = pos.getY() + (height / 2);
	}
	
	//TODO:
	//this method messes up collision atm
	private void gravity(float gravityWeight) {
		if(jumping) return;
		if (currentTilemap.getTile((int)(pos.getX()), (int)(pos.getY() + gravityWeight)).getSolid()) {
			return;
		}
		if (currentTilemap.getTile((int)(pos.getX() + width), (int)(pos.getY() + gravityWeight)).getSolid()) {
			return;
		}
		for (int i = 0; i <= xVals.length-1; i++) {
			if(currentTilemap.getTile((int)(pos.getX()), (int)(yVals[i] + gravityWeight)).getSolid()) {
				return;
			}
			if(currentTilemap.getTile((int)(pos.getX() + width), (int)(yVals[i] + gravityWeight)).getSolid()) {
				return;
			}
		}
		
		pos.add(0, gravityWeight);
		for (int i = 0; i <= yVals.length-1; i++) {
			yVals[i] += gravityWeight;	
		}
	}
	
	private void move(float testSpeedX, float testSpeedY) {
		if (currentTilemap.getTile((int)(pos.getX() +  testSpeedX), (int)(pos.getY() + testSpeedY)).getSolid()) {
			return;
		}
		if (currentTilemap.getTile((int)(pos.getX() + width + testSpeedX), (int)(pos.getY() + testSpeedY)).getSolid()) {
			return;
		}
		for (int i = 0; i<= xVals.length-1; i++) {
			if (currentTilemap.getTile((int) (xVals[i] +  testSpeedX), (int)(yVals[i] + testSpeedY)).getSolid()) {
				return;
			}
		}

		pos.add(testSpeedX, 0);
		pos.add(0, testSpeedY);
		
		for (int i = 0; i<= xVals.length-1; i++) {
			xVals[i]  += testSpeedX;
			yVals[i]  += testSpeedY;	
		}
	}
	
	private void checkShoot() {
		if(!rangedForm) return;
		
		if(MouseMaster.getMouseB() == 1 && shouldShoot) {
			shouldShoot = false;
			currentState.addProjectile(new Projectile(pos.getX() + (width / 2), pos.getY() + (height / 2), MouseMaster.getMouseX() + currentTilemap.getXOffset(), MouseMaster.getMouseY() + currentTilemap.getYOffset(), 600, 12.3f, 15, 16, Sprite.shuriken.getImage(), true,  currentState));
		}
	}
	
	private int xDest; private int yDest;
	private int xDiff; private int yDiff;
	private Rectangle meleeSwing = new Rectangle();
	private void checkMelee() {
		//firstly ensure we are in melee form, if not get out of this method
		if(rangedForm) return;
		
		if(MouseMaster.getMouseB() == 1 && shouldMelee) {
			xDest = MouseMaster.getMouseX() + currentTilemap.getXOffset();
			yDest = MouseMaster.getMouseY() + currentTilemap.getYOffset();
			xDiff = xDest - ((int)pos.getIntX() + (width / 2));
			yDiff = yDest - ((int)pos.getIntY() + (height / 2));
			
			if(xDiff > 0) { //right attack
				meleeSwing.x = (int)pos.getX() + meleeHeight; meleeSwing.y = (int)pos.getY();
				meleeSwing.width = meleeHeight; meleeSwing.height = meleeBase;
				swingRight = true;
			} 
			else { //left attack
				meleeSwing.x = (int)pos.getIntX() - meleeHeight; meleeSwing.y = (int)pos.getY();
				meleeSwing.width = meleeHeight; meleeSwing.height = meleeBase;
				swingLeft = true;
			}
			//finally check meleeHit
			checkMeleeHit();
			shouldMelee = false;
		}
	}
	
	private Mob tempEnemy;
	//helper method for checkMelee
	private void checkMeleeHit() {
		for(int i = 0; i < currentState.getEnemies().size(); i++) {
			tempEnemy = currentState.getEnemies().get(i);
			if(meleeSwing.intersects(tempEnemy.getHitbox()) || meleeSwing.contains(tempEnemy.getHitbox())) {
				tempEnemy.hit(meleeDamage);
			}
		}
	}
	
	private void updateCooldowns() {
		if(rangedForm) {
			if(!shouldShoot) {
				currentShootCooldown++;
				if(currentShootCooldown >= shootCooldown) {
					shouldShoot = true;
					currentShootCooldown = 0;
				}
			}
		}
		else {
			if(!shouldMelee) {
				currentMeleeCooldown++;
				if(currentMeleeCooldown >= meleeCooldown) {
					shouldMelee = true;
					currentMeleeCooldown = 0;
				}
			}
		}
		if(swingLeft || swingRight) {
			if(swingTime >= 0) {
				swingTime--;
			}
			else {
				swingTime = 5;
				swingLeft = false;
				swingRight = false;
			}
		}
		if(invincible) {
			currentInvincibleCooldown++;
			if(currentInvincibleCooldown == invincibleCooldown) {
				invincible = false;
				currentInvincibleCooldown = 0;
			}
		}
	}
	
	private void updateOffset() {
		currentTilemap.setXOffset((int)pos.getIntX() - (Game.WIDTH / 2) + (width / 2));
		currentTilemap.setYOffset((int)pos.getIntY() - (Game.HEIGHT / 2) + (height / 2));
	}
	
	//checks to see if the player changes form (melee - ranged)
	private void checkForm() {
		if(MouseMaster.getCurrentNotches() < 0 || MouseMaster.getCurrentNotches() > 0) {
			//reset current notches
			MouseMaster.resetCurrentNotches();
			
			//change focus
			rangedForm = !rangedForm;
			
			if(rangedForm) {
				CursorManager.setCursor(2);
			}
			else if(!rangedForm) {
				CursorManager.setCursor(1);
			}
		}
	}
	
	private int deathTime = 0;
	public void render(Graphics2D g, int xOffset, int yOffset) {
		if(!dying) {
			anim.render(g, xOffset, yOffset);
		
			drawHealth(g, xOffset, yOffset);
		}
		else {
			deathTime++;
			g.setFont(new Font("Algerian", Font.PLAIN, 70));
			g.drawImage(Sprite.playerDead.getImage(), pos.getIntX() - xOffset, pos.getIntY() + 16 - yOffset, null);
			g.drawString("Game Over", Game.WIDTH / 2 - 200, Game.HEIGHT / 2);
			if(deathTime == 300) {
				if(currentState.getGSM().getStates().peek() instanceof LevelOne) {
					currentState.getGSM().getStates().pop();
					currentState.getGSM().getStates().push(new LevelOne(currentState.getGSM(), "/levels/level1.bmp", 1400, 950));
				}
				if(currentState.getGSM().getStates().peek() instanceof LevelTwo) {
					currentState.getGSM().getStates().pop();
					currentState.getGSM().getStates().push(new LevelTwo(currentState.getGSM(), "/levels/level2.bmp", 1504, 944));
				}
				if(currentState.getGSM().getStates().peek() instanceof LevelThree) {
					currentState.getGSM().getStates().pop();
					currentState.getGSM().getStates().push(new LevelThree(currentState.getGSM(), "/levels/level3.bmp", 52 * 32, 32 * 32));
				}
			}
		}
	}
	
	private void drawHealth(Graphics2D g, int xOffset, int yOffset) {
		g.setColor(Color.GREEN);
		g.fillRect(pos.getIntX() - xOffset, pos.getIntY() - 20 - yOffset, width, 10);
		
		g.setColor(Color.RED);
		double barWidth = (double)width * (((double)health - (double)currentHealth) / (double)health);
		g.fillRect(pos.getIntX() - xOffset, pos.getIntY() - 20 - yOffset, (int)barWidth, 10);
	}
	
	public void hit(int damage) {
		if(invincible) return;
		
		currentHealth -= damage;
		invincible = true;
		if(currentHealth <= 0) {
			currentHealth = 0;
			dying = true;
		}
	}
	
	public void heal(int amount) {
		currentHealth += amount;
		if(currentHealth > health) {
			currentHealth = health;
		}
	}
	
	public void speedUp(int time) {
		speedUpTime = time;
		speedUp = true;
		xSpeed = 14.6f;
		ySpeed = 18.4f;
		collisionTune = 20;
	}
	
	private void checkSpeedUp() {
		if(speedUp) {
			speedUpTime--;
			if(speedUpTime <= 0) {
				speedUp = false;
				xSpeed = 7.3f;
				ySpeed = 9.2f;
				collisionTune = 10;
			}
		}
	}
	
	public void keyPressed(int k) {
		anim.keyPressed(k);
		if(k == KeyEvent.VK_W) {
			upHeld = true;
			jumpPressed = true;
		}
		if(k == KeyEvent.VK_S) {
			downHeld = true;
		}
		if(k == KeyEvent.VK_D) {
			rightHeld = true;
		}
		if(k == KeyEvent.VK_A) {
			leftHeld = true;
		}
	}
	
	public void keyReleased(int k) {
		anim.keyReleased(k);
		if(k == KeyEvent.VK_W) {
			upHeld = false;
		}
		if(k == KeyEvent.VK_S) {
			downHeld = false;
		}
		if(k == KeyEvent.VK_D) {
			rightHeld = false;
		}
		if(k == KeyEvent.VK_A) {
			leftHeld = false;
		}
	}
	
	public void keyTyped(int k) {
		
	}

	
	//getters
	public float getX() {
		return pos.getX();
	}
	public float getY() {
		return pos.getY();
	}
	public boolean getDying() {
		return dying;
	}
	public Rectangle getMeleeSwing() {
		return meleeSwing;
	}
	public Rectangle getHitbox() {
		return hitbox;
	}
	public boolean getJumping() {
		return jumping;
	}
	public boolean getRangedForm() {
		return rangedForm;
	}
	public boolean getShouldMelee() {
		return shouldMelee;
	}
	public boolean getShouldShoot() {
		return shouldShoot;
	}
	public int getRangedDamage() {
		return rangedDamage;
	}
	public boolean getSwingLeft() {
		return swingLeft;
	}
	public boolean getSwingRight() {
		return swingRight;
	}
}
