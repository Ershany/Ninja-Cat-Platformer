package game;

import gamestatemanager.GameStateManager;
import gfx.Sprite;
import input.KeyMaster;
import input.MouseMaster;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import sound.Sound;
import util.CursorManager;

public class Game extends Canvas implements Runnable {

	public static final String GAMENAME = "Ludum Dare 32";
	public static final int FPSUPS = 60;
	public static Dimension screenSize;
	public static int WIDTH, HEIGHT;
	
	private JFrame frame;
	private Thread gameThread;
	private volatile boolean running;
	private BufferStrategy bs;
	private BufferedImage image;
	private Graphics2D g;
	
	private GameStateManager gsm;
	private KeyMaster keyMaster;
	private MouseMaster mouseMaster;
	private CursorManager cm;
	
	public Game() {		
		frame = new JFrame(GAMENAME);
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		WIDTH = (int)screenSize.getWidth(); HEIGHT = (int)screenSize.getHeight();
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.setIconImage(Sprite.logoCat.getImage());
		frame.setVisible(true);
		frame.toFront();
		
		start();
	}
	
	public synchronized void start() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		gsm = new GameStateManager(g);
		keyMaster = new KeyMaster(gsm);
		mouseMaster = new MouseMaster();
		cm = new CursorManager(this);
		CursorManager.setCursor(1);
		
		this.addKeyListener(keyMaster);
		addMouseWheelListener(mouseMaster);
		addMouseListener(mouseMaster);
		addMouseMotionListener(mouseMaster);
		Sound.mainSong.play();
		
		gameThread = new Thread(this, "GameThread");
		gameThread.start();
	}
	
	public synchronized void stop() {
		try {
			gameThread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private int totalSeconds = 0;
	@Override
	public void run() {
		running = true;
		long timer = System.currentTimeMillis();
		
		long before = System.nanoTime();
		long after;
		double nsPerUpdate = 1000000000.0 / FPSUPS;
		int updates = 0;
		int frames = 0;
		double delta = 0;
		
		while(running) {
			after = System.nanoTime();
			delta += (after - before) / nsPerUpdate;
			before = after;
			
			while(delta >= 1) {
				delta--;
				update();
				render();
				renderToScreen();
				updates++;
				frames++;
			}
			if((System.currentTimeMillis() - timer) >= 1000) {
				timer += 1000;
				totalSeconds++;
				updates = 0;
				frames = 0;
				if(totalSeconds == 100) {
					Sound.mainSong.play();
					totalSeconds = 0;
				}
			}
		}
	}

	public void update() {
		gsm.update();
	}
	
	public void render() {
		gsm.render();
	}
	
	public void renderToScreen() {
		bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			requestFocus();
			return;
		}
		
		Graphics g2 = bs.getDrawGraphics();
		
		g2.drawImage(image, 0, 0, null);
		
		g2.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		new Game();
	}
}
