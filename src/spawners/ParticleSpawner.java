package spawners;

import gamestatemanager.LevelState;
import gfx.Particle;

import java.awt.Color;

public class ParticleSpawner {

	public ParticleSpawner(float x, float y, int particleLife, float particleSpeed, Color color, int amount, LevelState state) {
		for(int i = 0; i < amount; i++) {
			state.addParticle(new Particle(x, y, particleLife, particleSpeed, color));
		}
	}
	
}
