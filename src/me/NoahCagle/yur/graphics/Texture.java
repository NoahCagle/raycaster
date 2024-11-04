package me.NoahCagle.yur.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Texture {

	public static Texture brick = new Texture("res/tex/wall.png");

	private int width, height;
	private int[] pixels;
	private int[][] columns;

	public Texture(String path) {
		loadPixels(path);
	}

	private void loadPixels(String path) {
		File file = new File(path);

		try {
			BufferedImage textureImage = ImageIO.read(file);

			int w = textureImage.getWidth();
			int h = textureImage.getHeight();

			width = w;
			height = h;

			pixels = new int[w * h];

			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					pixels[x + y * w] = textureImage.getRGB(x, y);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		createColumns();

	}

	private void createColumns() {
		columns = new int[height][width];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				columns[y][x] = pixels[x + y * width];
			}
		}

	}

	public int colorAt(double x, double y) {
		x *= width;
		y *= height;
		if (x >= width) x--;
		if (y >= height) y--;
		return columns[(int) y][(int) x];
	}

}
