package gamestatemanager;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import portal.Portal;
import powerups.PowerUp;
import projectile.Projectile;
import spawners.ParticleSpawner;
import tilemap.Tilemap;
import entity.DemoRobot;
import entity.Mob;
import entity.Player;
import entity.Sentry;
import gfx.Particle;

public abstract class LevelState extends GameState {

	protected Tilemap tilemap;
	protected Player player;
	protected Portal portal1;
	protected Portal portal2;
	
	//lists
	protected List<Projectile> projectiles = new ArrayList<Projectile>();
	protected List<Particle> particles = new ArrayList<Particle>();
	protected List<Mob> enemies = new ArrayList<Mob>();
	protected List<PowerUp> powers = new ArrayList<PowerUp>();
	
	public LevelState(GameStateManager gsm, String mapName, int xSpawn, int ySpawn) {
		super(gsm);
		tilemap = new Tilemap(mapName);
		player = new Player(xSpawn, ySpawn, this);
	}

	public abstract void spawn();
	
	@Override
	public void init() {
		
	}


	@Override
	public void update() {
		spawn();
		
		tilemap.update();
		
		player.update();
		
		listUpdates();
		
		checkProjectileHit();
		
		checkMeleeDetection();
		
		checkPlayerHit();
		
		checkLists();
		
		updatePortal();
	}
	
	public abstract void updatePortal();
	
	private void listUpdates() {
		for(int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update();
		}
		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).update();
		}
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).update();
		}
		for(int i = 0; i < powers.size(); i++) {
			if(powers.get(i).getHitbox().intersects(player.getHitbox())) {
				powers.get(i).effect();
			}
		}
	}
	
	private void checkLists() {
		for(int i = 0; i < projectiles.size(); i++) {
			if(projectiles.get(i).getRemoved()) projectiles.remove(i);
		}
		for(int i = 0; i < particles.size(); i++) {
			if(particles.get(i).getRemoved()) particles.remove(i);
		}
		for(int i = 0; i < enemies.size(); i++) {
			if(enemies.get(i).getRemoved()) enemies.remove(i);
		}
		for(int i = 0; i < powers.size(); i++) {
			if(powers.get(i).getRemoved()) powers.remove(i);
		}
	}
	
	//used to check projectile hit detection on mobs
	private void checkProjectileHit() {
		if(projectiles.size() == 0) return;
		if(enemies.size() == 0) return;
		for(int p = 0; p < projectiles.size(); p++) {
			for(int e = 0; e < enemies.size(); e++) {
				Projectile tempP = projectiles.get(p);
				Mob mobE = enemies.get(e);
				if(tempP.getHitbox().intersects(mobE.getHitbox()) && !mobE.getDying() && tempP.getPlayerControlled()) {
					mobE.hit(player.getRangedDamage());
					tempP.setRemoved(true);
					if(mobE instanceof Sentry) {
						new ParticleSpawner(mobE.getPos().getX() + 24, mobE.getPos().getY() + 24, 15, 1.3f, Color.GRAY, 30, this);
					}
				}
			}
		}
	}
	
	//used to check projectile hit detection on player
	private void checkPlayerHit() {
		if(projectiles.size() == 0) return;
		if(enemies.size() == 0) return;
		for(int p = 0; p < projectiles.size(); p++) {
			Projectile tempP = projectiles.get(p);
			if(tempP.getPlayerControlled()) continue;
			//check to see if the player hits the projectile with his blade
			if(player.getSwingLeft() || player.getSwingRight()) {
				if(player.getMeleeSwing().intersects(tempP.getHitbox()) || player.getMeleeSwing().contains(tempP.getHitbox())) {
					tempP.setRemoved(true);
				}
			}
			if(tempP.getHitbox().intersects(player.getHitbox())) {
				player.hit(tempP.getDamage());
				tempP.setRemoved(true);
				new ParticleSpawner(player.getPos().getX() + 24, player.getPos().getY() + 24, 15, 1.3f, Color.RED, 30, this);
			}
		}
	}
	
	//if the player is standing next to an enemy, he will get hurt
	private void checkMeleeDetection() {
		if(enemies.size() == 0) return;
		for(int i = 0; i < enemies.size(); i++) {
			Mob mobE = enemies.get(i);
			if(mobE.getHitbox().intersects(player.getHitbox()) && !mobE.getDying()) {
				if(mobE instanceof DemoRobot) {
					//if the DemoRobot hits the player, blow up
					player.hit(5);
					mobE.hit(10);
				} else {
					player.hit(1);
				}
			}
		}
	}

	@Override
	public void render(Graphics2D g) {
		tilemap.render(g);
		
		renderLists(g);
		
		renderPortals(g);
		
		player.render(g, tilemap.getXOffset(), tilemap.getYOffset());
	}
	
	private void renderPortals(Graphics2D g) {
		if(portal1 != null && portal2 != null) {
			portal1.render(g, tilemap.getXOffset(), tilemap.getYOffset());
			portal2.render(g, tilemap.getXOffset(), tilemap.getYOffset());
		}
	}
	
	private void renderLists(Graphics2D g) {
		for(int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(g, tilemap.getXOffset(), tilemap.getYOffset());
		}
		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).render(g, tilemap.getXOffset(), tilemap.getYOffset());
		}
		for(int i = 0; i < enemies.size(); i++) {
			if(Math.abs(player.getPos().getIntX() - enemies.get(i).getPos().getIntX()) > 4000 || Math.abs(player.getPos().getIntY() - enemies.get(i).getPos().getIntY()) > 4000) {
				continue;
			}
			enemies.get(i).render(g, tilemap.getXOffset(), tilemap.getYOffset());
		}
		for(int i = 0; i < powers.size(); i++) {
			powers.get(i).render(g, tilemap.getXOffset(), tilemap.getYOffset());
		}
	}

	@Override
	public void keyPressed(int k) {
		player.keyPressed(k);
	}


	@Override
	public void keyReleased(int k) {
		player.keyReleased(k);
	}


	@Override
	public void keyTyped(int k) {
		player.keyTyped(k);
	}
	
	//adding
	public void addProjectile(Projectile p) {
		projectiles.add(p);
	}
	public void addParticle(Particle p) {
		particles.add(p);
	}
	public void addEnemy(Mob m) {
		enemies.add(m);
	}
	public void addPowerUp(PowerUp p) {
		powers.add(p);
	}
	
	//getters
	public GameStateManager getGSM() {
		return gsm;
	}
	public Tilemap getTilemap() {
		return tilemap;
	}
	public List<Projectile> getProjectiles() {
		return projectiles;
	}
	public List<Particle> getParticles() {
		return particles;
	}
	public List<Mob> getEnemies() {
		return enemies;
	}
	public Player getPlayer() {
		return player;
	}
}
