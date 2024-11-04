package me.NoahCagle.yur.world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class World {

	private List<Block> blocks = new ArrayList<Block>();
	private int mapWidth, mapHeight;

	private int[] blockPixels;
	
	public static int blockSize = 64;

	public World() {
		generateWorld();
	}

	private void generateWorld() {
		loadPixels();

		for (int yp = 0; yp < mapHeight; yp++) {
			for (int xp = 0; xp < mapWidth; xp++) {
				int block = blockPixels[xp + yp * mapWidth];
				System.out.println(block);
				if (block != -16777216) {
					blocks.add(new Block(xp, yp, blockSize, block));
				}
			}
		}

	}

	private void loadPixels() {
		File file = new File("res/level/map.png");

		try {
			BufferedImage mapImage = ImageIO.read(file);
			int w = mapImage.getWidth();
			int h = mapImage.getHeight();
			
			this.mapWidth = w;
			this.mapHeight = h;

			blockPixels = new int[w * h];

			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					blockPixels[x + y * w] = mapImage.getRGB(x, y);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public List<Boundary> getBoundaries() {
		List<Boundary> boundariesList = new ArrayList<Boundary>();

		for (Block b : blocks) {
			for (Boundary bound : b.getBoundaries()) {
				boundariesList.add(bound);
			}
		}

		return boundariesList;

	}

}
