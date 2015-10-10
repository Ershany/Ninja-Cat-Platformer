package util;

public class Vector2f {

	private float x, y;
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setPos(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void add(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	//getters
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public int getIntX() {
		return (int)x;
	}
	public int getIntY() {
		return (int)y;
	}
}
