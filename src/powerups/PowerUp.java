package powerups;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import entity.Entity;
import entity.Player;

public abstract class PowerUp extends Entity {

	protected Rectangle hitbox;
	protected Player player;
	
	public PowerUp(float x, float y, Player player) {
		super(x, y);
		this.player = player;
		hitbox = new Rectangle(pos.getIntX(), pos.getIntY(), 32, 32);
	}
	
	public abstract void effect();
	public abstract void render(Graphics2D g, int xOffset, int yOffset);
	
	//getters
	public Rectangle getHitbox() {
		return hitbox;
	}
}
