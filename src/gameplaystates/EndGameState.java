package gameplaystates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import game.Game;
import gamestatemanager.GameState;
import gamestatemanager.GameStateManager;
import gfx.Sprite;

public class EndGameState extends GameState {

	private Font font;
	private Color color;
	private String[] credits;
	private int currentCredit;
	private int maxScroll;
	
	public EndGameState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		font = new Font("Arial", Font.ITALIC, 45);
		color = Color.BLACK;
		credits = new String[]{"Lead Game Designer: Brady Jessup",
				   "Lead Programmer: Brady Jessup",
				   "Lead Artist: Josh Gauthier",
				   "Music done by EliteFerrex"};
		currentCredit = 0;
		maxScroll = Game.WIDTH * 2;
	}

	private int currentX = 0;
	private int textX = Game.WIDTH + 200;
	private int textY = (Game.HEIGHT / 5 * currentCredit) + (Game.HEIGHT / 5);
	@Override
	public void update() {
		//scroll speed
		//currentX -= Game.WIDTH / 360;
		currentX -= Game.WIDTH / 720;
		//textX -= 10;
		textX -= 5;
		
		//change the credits at the right time
		if(Math.abs(currentX) >= maxScroll / credits.length * 3 && currentCredit < 3) {
			currentCredit = 3;
			textY = (Game.HEIGHT / 5 * currentCredit) + (Game.HEIGHT / 5);
			textX = Game.WIDTH + 200;
		}
		else if(Math.abs(currentX) >= maxScroll / credits.length * 2 && currentCredit < 2) {
			currentCredit = 2;
			textY = (Game.HEIGHT / 5 * currentCredit) + (Game.HEIGHT / 5);
			textX = Game.WIDTH + 200;
		}
		else if(Math.abs(currentX) >= maxScroll / credits.length && currentCredit < 1) {
			currentCredit = 1;
			textY = (Game.HEIGHT / 5 * currentCredit) + (Game.HEIGHT / 5);
			textX = Game.WIDTH + 200;
		}
		
		
	}

	@Override
	public void render(Graphics2D g) {
		g.setFont(font);
		g.setColor(color);
		
		//draw the picture, but after a certain time, exit the game
		if(Math.abs(currentX) >= maxScroll) {
			System.out.println(currentX);
			System.exit(0);
		}
		g.drawImage(Sprite.endingBackground.getImage(), currentX, 0, Game.WIDTH * 3, Game.HEIGHT, null);
		
		g.drawString(credits[currentCredit], textX, textY);
	}

	@Override
	public void keyPressed(int k) {
		
	}

	@Override
	public void keyReleased(int k) {
		
	}

	@Override
	public void keyTyped(int k) {
		
	}

}
