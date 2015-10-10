package gamestatemanager;

import game.Game;
import gfx.Sprite;
import input.MouseMaster;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class InfoScreen extends GameState {
	
	private boolean skip;
	
	public InfoScreen(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		
	}

	private int timePassed = 0;
	@Override
	public void update() {
		timePassed++;
		
		if(timePassed == 120) {
			skip = true;
		}
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(Sprite.opening.getImage(), 0, 0, Game.WIDTH, Game.HEIGHT, null);
		
		if(skip) {
			g.setFont(new Font("Algerian", Font.PLAIN, 70));
			g.setColor(Color.RED);
			g.drawString("Press Any Key To Continue", Game.WIDTH / 2 - 500, Game.HEIGHT - 50);
		}
	}

	@Override
	public void keyPressed(int k) {
		if(skip) 
			gsm.getStates().push(new MainMenu(gsm));
	}

	@Override
	public void keyReleased(int k) {
		
	}

	@Override
	public void keyTyped(int k) {
		
	}

}
