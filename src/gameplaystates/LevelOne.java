package gameplaystates;

import entity.DemoRobot;
import entity.Sentry;
import gamestatemanager.GameStateManager;
import gamestatemanager.LevelState;
import portal.Portal;
import powerups.HeartUp;
import util.CursorManager;

public class LevelOne extends LevelState {

	public LevelOne(GameStateManager gsm, String mapName, int xSpawn, int ySpawn) {
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
		
		if(spawnCount == 0) {
			CursorManager.setCursor(1);
			spawnCount++;
			portal1 = new Portal(4127, 910, true);
			portal2 = new Portal(100, 100, false);
			enemies.add(new Sentry(2430, 1200, this));
			enemies.add(new DemoRobot(2600, 1600, this));
			powers.add(new HeartUp(132 * 32, 51 * 32, player));
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
			gsm.getStates().push(new LevelTwo(gsm, "/levels/level2.bmp", 1504, 944));
		}
		if(portal2.getHitbox().intersects(player.getHitbox()) && portal2.getWorks()) {
			gsm.getStates().pop();
			gsm.getStates().push(new LevelTwo(gsm, "/levels/level2.bmp", 1504, 944));
		}	
	}

}
