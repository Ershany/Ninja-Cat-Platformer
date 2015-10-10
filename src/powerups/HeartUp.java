package powerups;

import java.awt.Graphics2D;

import entity.Player;
import gfx.Sprite;

public class HeartUp extends PowerUp {

	private int healAmount = 2;
	
	public HeartUp(float x, float y, Player player) {
		super(x, y, player);
	}

	@Override
	public void effect() {
		player.heal(healAmount);
		removed = true;
	}

	@Override
	public void render(Graphics2D g, int xOffset, int yOffset) {
		g.drawImage(Sprite.heartContainer.getImage(), pos.getIntX() - xOffset, pos.getIntY() - yOffset, null);
	}

}
