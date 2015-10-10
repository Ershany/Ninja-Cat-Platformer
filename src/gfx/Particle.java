package gfx;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import entity.Entity;

public class Particle extends Entity {
	
	private Color color;
	private float xa, ya;
	private float particleSpeed;
	private int particleLife;
	
	private Random random = new Random();
	
	public Particle(float x, float y, int particleLife, float particleSpeed, Color color) {
		super(x, y);
		this.color = color;
		this.particleLife = particleLife;
		this.particleSpeed = particleSpeed;
		
		init();
	}
	
	public void init() {
		xa = (float) random.nextGaussian() * particleSpeed;
		ya = (float) random.nextGaussian() * particleSpeed;
	}
	
	public void update() {
		if(particleLife <= 0) {
			removed = true;
		}
		else {
			particleLife--;
		}
		
		pos.add(xa, ya);
	}

	public void render(Graphics2D g, int xOffset, int yOffset) {
		g.setColor(color);
		g.fillRect(pos.getIntX() - xOffset, pos.getIntY() - yOffset, 2, 2);
	}
	
}
