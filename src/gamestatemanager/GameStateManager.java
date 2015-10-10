package gamestatemanager;

import java.awt.Graphics2D;
import java.util.Stack;

import gameplaystates.EndGameState;

public class GameStateManager {

	private Graphics2D g;
	private Stack<GameState> gameStates = new Stack<GameState>();
	
	public GameStateManager(Graphics2D g) {
		this.g = g;
		
		gameStates.push(new InfoScreen(this));
	}
	
	public void update() {
		gameStates.peek().update();
	}
	
	public void render() {
		gameStates.peek().render(g);
	}
	
	public void keyPressed(int k) {
		gameStates.peek().keyPressed(k);
	}
	
	public void keyReleased(int k) {
		gameStates.peek().keyReleased(k);
	}
	
	public void keyTyped(int k) {
		gameStates.peek().keyTyped(k);
	}
	
	//getters
	public Stack<GameState> getStates() {
		return gameStates;
	}
}
