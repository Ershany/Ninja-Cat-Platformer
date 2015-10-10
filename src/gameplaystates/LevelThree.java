package gameplaystates;

import entity.DemoRobot;
import entity.Sentry;
import gamestatemanager.GameStateManager;
import gamestatemanager.LevelState;
import portal.Portal;
import powerups.HeartUp;
import powerups.SpeedUp;
import util.CursorManager;

public class LevelThree extends LevelState {

	public LevelThree(GameStateManager gsm, String mapName, int xSpawn,
			int ySpawn) {
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
			portal1 = new Portal(285 * 32 + 16, 302 * 32, true);
			portal2 = new Portal(49 * 32 + 16, 32 * 32, false);
			
			powers.add(new SpeedUp(58 * 32, 117 * 32, player));
			powers.add(new SpeedUp(180 * 32, 57 * 32, player));
			powers.add(new SpeedUp(272 * 32, 68 * 32, player));
			powers.add(new SpeedUp(209 * 32, 159 * 32, player));
			powers.add(new SpeedUp(239 * 32, 226 * 32, player));
			powers.add(new SpeedUp(279 * 32, 192 * 32, player));
			
			powers.add(new HeartUp(166 * 32, 31 * 32, player));
			powers.add(new HeartUp(162 * 32, 38 * 32, player));
			powers.add(new HeartUp(232 * 32, 32 * 32, player));
			powers.add(new HeartUp(234 * 32, 32 * 32, player));
			powers.add(new HeartUp(180 * 32, 57 * 32, player));
			powers.add(new HeartUp(278 * 32, 193 * 32, player));
			powers.add(new HeartUp(279 * 32, 193 * 32, player));
			powers.add(new HeartUp(280 * 32, 193 * 32, player));
			powers.add(new HeartUp(162 * 32, 47 * 32, player));
			powers.add(new HeartUp(110 * 32, 242 * 32, player));
			powers.add(new HeartUp(239 * 32, 228 * 32, player));
			powers.add(new HeartUp(241 * 32, 228 * 32, player));
			powers.add(new HeartUp(237 * 32, 228 * 32, player));
			
			enemies.add(new Sentry(94 * 32, 33 * 32, this));
			enemies.add(new Sentry(95 * 32, 40 * 32, this));
			enemies.add(new Sentry(105 * 32, 40 * 32, this));
			enemies.add(new Sentry(100 * 32, 66 * 32, this));
			enemies.add(new Sentry(127 * 32, 86 * 32, this));
			enemies.add(new Sentry(135 * 32, 78 * 32, this));
			enemies.add(new Sentry(144 * 32, 73 * 32, this));
			enemies.add(new Sentry(123 * 32, 68 * 32, this));
			enemies.add(new Sentry(139 * 32, 59 * 32, this));
			enemies.add(new Sentry(129 * 32, 40 * 32, this));
			enemies.add(new Sentry(144 * 32, 44 * 32, this));
			enemies.add(new Sentry(279 * 32, 144 * 32, this));
			enemies.add(new Sentry(285 * 32, 144 * 32, this));
			enemies.add(new Sentry(279 * 32, 171 * 32, this));
			enemies.add(new Sentry(286 * 32, 171 * 32, this));
			enemies.add(new Sentry(193 * 32, 111 * 32, this));
			enemies.add(new Sentry(211 * 32, 115 * 32, this));
			enemies.add(new Sentry(228 * 32, 110 * 32, this));
			enemies.add(new Sentry(249 * 32, 115 * 32, this));
			enemies.add(new Sentry(267 * 32, 206 * 32, this));
			enemies.add(new Sentry(64 * 32, 268 * 32, this));
			enemies.add(new Sentry(76 * 32, 268 * 32, this));
			enemies.add(new Sentry(73 * 32, 282 * 32, this));
			enemies.add(new Sentry(94 * 32, 267 * 32, this));
			enemies.add(new Sentry(87 * 32, 278 * 32, this));
			enemies.add(new Sentry(99 * 32, 279 * 32, this));
			enemies.add(new Sentry(112 * 32, 267 * 32, this));
			enemies.add(new Sentry(113 * 32, 281 * 32, this));
			enemies.add(new Sentry(126 * 32, 269 * 32, this));
			enemies.add(new Sentry(128 * 32, 280 * 32, this));
			enemies.add(new Sentry(138 * 32, 268 * 32, this));
			enemies.add(new Sentry(138 * 32, 280 * 32, this));
			enemies.add(new Sentry(150 * 32, 268 * 32, this));
			enemies.add(new Sentry(153 * 32, 280 * 32, this));
			enemies.add(new Sentry(164 * 32, 268 * 32, this));
			enemies.add(new Sentry(164 * 32, 280 * 32, this));
			enemies.add(new Sentry(178 * 32, 268 * 32, this));
			enemies.add(new Sentry(178 * 32, 281 * 32, this));
			enemies.add(new Sentry(188 * 32, 269 * 32, this));
			enemies.add(new Sentry(189 * 32, 282 * 32, this));
			enemies.add(new Sentry(206 * 32, 266 * 32, this));
			enemies.add(new Sentry(205 * 32, 279 * 32, this));
			enemies.add(new Sentry(217 * 32, 267 * 32, this));
			enemies.add(new Sentry(219 * 32, 279 * 32, this));
			enemies.add(new Sentry(230 * 32, 266 * 32, this));
			enemies.add(new Sentry(230 * 32, 279 * 32, this));
			enemies.add(new Sentry(244 * 32, 264 * 32, this));
			enemies.add(new Sentry(244 * 32, 280 * 32, this));
			enemies.add(new Sentry(263 * 32, 267 * 32, this));
			enemies.add(new Sentry(257 * 32, 282 * 32, this));
			enemies.add(new Sentry(217 * 32, 292 * 32, this));
			enemies.add(new Sentry(99 * 32, 291 * 32, this));
			
			
			enemies.add(new DemoRobot(103 * 32, 55 * 32, this));
			enemies.add(new DemoRobot(162 * 32, 38 * 32, this));
			enemies.add(new DemoRobot(105 * 32, 66 * 32, this));
			enemies.add(new DemoRobot(162 * 32, 91 * 32, this));
			enemies.add(new DemoRobot(166 * 32, 67 * 32, this));
			enemies.add(new DemoRobot(281 * 32, 73 * 32, this));
			enemies.add(new DemoRobot(281 * 32, 158 * 32, this));
			enemies.add(new DemoRobot(276 * 32, 183 * 32, this));
			enemies.add(new DemoRobot(265 * 32, 80 * 32, this));
			enemies.add(new DemoRobot(213 * 32, 94 * 32, this));
			enemies.add(new DemoRobot(248 * 32, 152 * 32, this));
			enemies.add(new DemoRobot(235 * 32, 194 * 32, this));
			enemies.add(new DemoRobot(265 * 32, 282 * 32, this));
			enemies.add(new DemoRobot(269 * 32, 289 * 32, this));
			
			spawnCount++;
		}
		
		
		if(updates < 60000) {
			updates = 1;
		}	
	}

	@Override
	public void updatePortal() {
		if(portal1.getHitbox().intersects(player.getHitbox()) && portal1.getWorks()) {
			gsm.getStates().pop();
			gsm.getStates().push(new EndGameState(gsm));
		}
	}

}
