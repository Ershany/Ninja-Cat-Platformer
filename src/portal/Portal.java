package portal;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import entity.Entity;
import entity.Player;
import gfx.Sprite;

public class Portal extends Entity {
	
	private boolean works;
	private BufferedImage portalImage;
	
	private int width = 32;
	private int height = 64;
	private Rectangle hitbox;
	
	public Portal(float x, float y, boolean works) {
		super(x, y);
		this.works = works;
		
		if(works) {
			portalImage = Sprite.portalWorks.getImage();
		}
		else {
			portalImage = Sprite.portalDoesntWork.getImage();
		}
				
		hitbox = new Rectangle((int)x - 8, (int)y, width, height);
	}
	
	public void update() {
		
	}
	
	public void render(Graphics2D g, int xOffset, int yOffset) {
		g.drawImage(portalImage, pos.getIntX() - xOffset, pos.getIntY() - yOffset, null);
	}
	
	
	//getters
	public boolean getWorks() {
		return works;
	}
	public Rectangle getHitbox() {
		return hitbox;
	}
}
