package gamestatemanager;

import game.Game;
import gameplaystates.LevelOne;
import gameplaystates.LevelThree;
import gfx.Sprite;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class MainMenu extends GameState {

	private String[] options = {"Play", "Info", "Quit"};
	private int currentOption = 0;
	
	private Font font;
	private Color color;
	
	public MainMenu(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		font = new Font("Algerian", Font.PLAIN, 70);
		color = Color.RED;
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(Sprite.menuPic.getImage(), 0, 0, Game.WIDTH, Game.HEIGHT, null);
		
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(currentOption == i) {
				g.setColor(color);
			}
			else {
				g.setColor(Color.BLACK);
			}
			g.drawString(options[i], (Game.WIDTH / 2) - 80, 200 + (i * 100));
		}
	}

	private void select() {
		if(currentOption == 0) {
			gsm.getStates().push(new LevelOne(gsm, "/levels/level1.bmp", 1400, 950));
		} 
		else if(currentOption == 1) {
			gsm.getStates().push(new ControlsState(gsm));
		}
		else if(currentOption == 2) {
			System.exit(0);
		}
	}
	
	@Override
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_W || k == KeyEvent.VK_UP) {
			if(currentOption == 0) currentOption = options.length - 1;
			else currentOption--;
		}
		else if(k == KeyEvent.VK_S || k == KeyEvent.VK_DOWN) {
			if(currentOption == options.length - 1) currentOption = 0;
			else currentOption++;
		}
		else if(k == KeyEvent.VK_ENTER) {
			select();
		}
	}

	@Override
	public void keyReleased(int k) {
		
	}

	@Override
	public void keyTyped(int k) {
		
	}

}
