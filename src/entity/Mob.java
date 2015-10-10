package entity;

import gamestatemanager.LevelState;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Mob extends Entity {

	protected LevelState currentState;
	
	protected boolean dying;
	protected Rectangle hitbox;
	protected int damage;
	protected int health;
	protected int currentHealth;
	
	public Mob(float x, float y, LevelState state) {
		super(x, y);
		this.currentState = state;
		
		init();
	}

	public void hit(int damage) {
		currentHealth -= damage;
		if(currentHealth <= 0) {
			currentHealth = 0;
			dying = true;
		}
	}
	
	public abstract void init();
	public abstract void update();
	public abstract void render(Graphics2D g, int xOffset, int yOffset);
	
	//getters
	public Rectangle getHitbox() {
		return hitbox;
	}
	public boolean getDying() {
		return dying;
	}
}
