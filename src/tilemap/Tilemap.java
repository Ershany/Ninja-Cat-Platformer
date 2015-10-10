package tilemap;

import game.Game;
import gfx.Sprite;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import tile.BackWallTile;
import tile.FakeWallTile;
import tile.NullTile;
import tile.ShootThroughWallTile;
import tile.SolidSwitchTile;
import tile.Tile;
import tile.ToxicWasteTile;
import tile.WallTile;

public class Tilemap {

	private BufferedImage map;
	private int width, height;
	private String path;
	private Tile[] tiles;
	private int[] pixels;

	private int xOffset;
	private int yOffset;

	// will read an image for its pixel data
	public Tilemap(String path) {
		this.path = path;
		xOffset = 0;
		yOffset = 0;

		load();
	}

	private void load() {
		try {
			map = ImageIO.read(Tilemap.class.getResource(path));
			width = map.getWidth();
			height = map.getHeight();
			tiles = new Tile[width * height];
			pixels = map.getRGB(0, 0, width, height, null, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// now lets load the tiles
		int loc;
		int colorCode;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
			 loc = x + y * width;
				colorCode = pixels[loc];
				Tile toPlace = null;
				switch (colorCode) {
				case 0xFF353535:
					toPlace = new WallTile(x, y, Sprite.wall.getImage());
					break;
				case 0xFF9E9E9E:
					toPlace = new BackWallTile(x, y, Sprite.backWall.getImage());
					break;
				case 0xFF007F0E:
					toPlace = new ToxicWasteTile(x, y, Sprite.toxicWaste1.getImage());
					break;
				case 0xFFFF6A00:
					toPlace = new ShootThroughWallTile(x, y, Sprite.elevator.getImage());
					break;
				case 0xFFFF00DC: 
					toPlace = new FakeWallTile(x, y, Sprite.wall.getImage());
					break;
				case 0xFFFF0000: 
					toPlace = new SolidSwitchTile(x, y, Sprite.solidSwitch.getImage());
					break;
				default:
					toPlace = new NullTile(x, y, null);
					break;
				}
				tiles[loc] = toPlace;
			}
		}
	}

	public void update() {
		for (int i = 0; i < tiles.length; i++) {
			tiles[i].update();
		}
	}

	public void render(Graphics2D g) {
		for (int i = (xOffset / Tile.TILESIZE) + (yOffset / Tile.TILESIZE)
				* width; i < ((xOffset + Game.WIDTH + 50) / Tile.TILESIZE + (yOffset
				+ Game.HEIGHT + 50)
				/ Tile.TILESIZE * width); i++) {
			try {
				if (tiles[i].getX() * Tile.TILESIZE < xOffset - 32)
					continue;
				if (tiles[i].getX() * Tile.TILESIZE > xOffset + Game.WIDTH + 0)
					continue;
				if (tiles[i].getY() * Tile.TILESIZE < yOffset - 32)
					continue;
				if (tiles[i].getY() * Tile.TILESIZE > yOffset + Game.HEIGHT
						+ 0)
					continue;
			} catch (ArrayIndexOutOfBoundsException e) {
				continue;
			}

			// if it made it to this point, render the tile as it is on the
			// screen
			tiles[i].render(xOffset, yOffset, g);
		}
	}

	// getters
	public int getXOffset() {
		return xOffset;
	}
	public int getYOffset() {
		return yOffset;
	}
	public int[] getPixels() {
		return pixels;
	}
	public Tile[] getTiles() {
		return tiles;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}

	// pass in pixel precision x and y
	public Tile getTile(int x, int y) {
		return tiles[(x / Tile.TILESIZE) + (y / Tile.TILESIZE) * width];
	}
	
	//used to switch out tiles (pass in tile precision)
	public void replaceTile(int x, int y, Tile tile) {
		tiles[x + y * width] = tile;
	}

	// setters
	public void setXOffset(int newOffset) {
		xOffset = newOffset;
	}

	public void setYOffset(int newOffset) {
		yOffset = newOffset;
	}

	public void addOffset(int xOffset, int yOffset) {
		this.xOffset += xOffset;
		this.yOffset += yOffset;
	}
}
