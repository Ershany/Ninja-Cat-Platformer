package powerups;

import java.awt.Graphics2D;

import entity.Player;
import gfx.Sprite;

public class SpeedUp extends PowerUp {

	private int updatesSped = 540;
	
	public SpeedUp(float x, float y, Player player) {
		super(x, y, player);
	}

	@Override
	public void effect() {
		player.speedUp(updatesSped);
		removed = true;
	}

	@Override
	public void render(Graphics2D g, int xOffset, int yOffset) {
		g.drawImage(Sprite.speedContainer.getImage(), pos.getIntX() - xOffset, pos.getIntY() - yOffset, null);
	}

}
