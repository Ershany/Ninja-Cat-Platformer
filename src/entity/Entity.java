package entity;

import util.Vector2f;

public class Entity {

	protected Vector2f pos;
	protected boolean removed;
	
	public Entity(float x, float y) {
		pos = new Vector2f(x, y);
	}
	
	public Entity(Vector2f pos) {
		this.pos = pos;
	}
	
	//setter
	public void setRemoved(boolean decide) {
		removed = decide;
	}
	
	//getters
	public Vector2f getPos() {
		return pos;
	}
	public boolean getRemoved() {
		return removed;
	}
}
