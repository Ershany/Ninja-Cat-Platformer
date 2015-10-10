package gameplaystates;

import entity.DemoRobot;
import entity.Sentry;
import gamestatemanager.GameStateManager;
import gamestatemanager.LevelState;
import portal.Portal;
import util.CursorManager;

public class LevelTwo extends LevelState {

	public LevelTwo(GameStateManager gsm, String mapName, int xSpawn, int ySpawn) {
		super(gsm, mapName, xSpawn, ySpawn);
	}

	private int updates = 0;
	private int spawnCount = 0;
	@Override
	public void spawn() {
		updates++;
		
		if(updates % 60 == 0) {
			spawnCount++;
		}
		
		//spawns right away
		if(spawnCount == 0) {
			CursorManager.setCursor(1);
			
			portal1 = new Portal(93 * 32 + 16, 390 * 32, true);
			portal2 = new Portal(1392, 944, false);
			
			enemies.add(new Sentry(79 * 32, 40 * 32, this));
			enemies.add(new Sentry(68 * 32, 70 * 32, this));
			enemies.add(new Sentry(83 * 32, 126 * 32, this));
			enemies.add(new Sentry(60 * 32, 126 * 32, this));
			enemies.add(new Sentry(71 * 32, 302 * 32, this));
			enemies.add(new Sentry(71 * 32, 314 * 32, this));
			enemies.add(new Sentry(64 * 32, 323 * 32, this));
			enemies.add(new Sentry(83 * 32, 345 * 32, this));
			enemies.add(new Sentry(57 * 32, 345 * 32, this));
			enemies.add(new Sentry(57 * 32, 376 * 32, this));
			enemies.add(new Sentry(68 * 32, 376 * 32, this));
			enemies.add(new Sentry(81 * 32, 376 * 32, this));
			
			enemies.add(new DemoRobot(67 * 32, 84 * 32, this));
			enemies.add(new DemoRobot(68 * 32, 146 * 32, this));
			enemies.add(new DemoRobot(93 * 32, 162 * 32, this));
			enemies.add(new DemoRobot(48 * 32, 169 * 32, this));
			enemies.add(new DemoRobot(50 * 32, 223 * 32, this));
			enemies.add(new DemoRobot(90 * 32, 223 * 32, this));
			enemies.add(new DemoRobot(69 * 32, 324 * 32, this));
			enemies.add(new DemoRobot(72 * 32, 340 * 32, this));
			
			spawnCount++;
		}
		
		
		if(updates < 60000) {
			updates = 1;
		}
	}
	@Override
	public void updatePortal() {
		portal1.update();
		portal2.update();
		
		if(portal1.getHitbox().intersects(player.getHitbox()) && portal1.getWorks()) {
			gsm.getStates().pop();
			gsm.getStates().push(new LevelThree(gsm, "/levels/level3.bmp", 52 * 32, 32 * 32));
		}
		if(portal2.getHitbox().intersects(player.getHitbox()) && portal2.getWorks()) {
			gsm.getStates().pop();
			gsm.getStates().push(new LevelThree(gsm, "/levels/level3.bmp", 52 * 32, 32 * 32));
		}	
	}

}
