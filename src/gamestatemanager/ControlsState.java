package gamestatemanager;

import game.Game;
import gfx.Sprite;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class ControlsState extends GameState {

	public ControlsState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(Sprite.controls.getImage(), 0, 0, Game.WIDTH, Game.HEIGHT, null);
		
		g.setColor(Color.RED);
		g.drawString("Back", Game.WIDTH / 2 - 80, Game.HEIGHT - (Game.HEIGHT / 10));
	}

	@Override
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER) {
			gsm.getStates().pop();
		}
	}

	@Override
	public void keyReleased(int k) {
		
	}

	@Override
	public void keyTyped(int k) {
		
	}

}
