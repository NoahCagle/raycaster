package me.NoahCagle.yur.graphics;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import me.NoahCagle.yur.logic.Intersection;
import me.NoahCagle.yur.world.Ray;

public class Game3D extends Canvas {
	private static final long serialVersionUID = 1L;

	private int width, height, scale;
	private int quantityColumns;
	private int columnWidth;

	private BufferedImage image;
	private int[] pixels;

	private Screen screen;

	private Ray[] rays;

	public Game3D(int width, int height, int scale, Ray[] rays) {
		this.width = width;
		this.height = height;
		this.scale = scale;
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);

		screen = new Screen(width, height);

		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

		this.rays = rays;

		quantityColumns = rays.length;
		System.out.println(rays.length);
		columnWidth = width / quantityColumns;

	}

	public void tick() {
	}

	public void render() {

		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.clear();

		draw3D();

		screen.sync(pixels);

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, width * scale, height * scale, null);
		g.dispose();
		bs.show();

	}

	private void draw3D() {
		for (int i = 0; i < rays.length; i++) {
			if (rays[i].isIntersecting()) {
				Intersection intersection = rays[i].getIntersection();
				// normalizedDistance = 1 when distance = 0, and normalizedDistance = 0 when distance == rayLength (longest a ray can be)
				double normalizedDistance = ((-intersection.getDistance() / rays[i].rayLength) + 1);
				double x = i / rays.length;
				double drawHeight = normalizedDistance * height;
				int col = adjustColorValue(intersection.getBoundary().getColor(), normalizedDistance);
				screen.fillRect((i * columnWidth), height / 2, columnWidth, (int) drawHeight, col);
			}
		}
	}

	private int adjustColorValue(int col, double brightness) {
		int r = (col >> 16) & 0xff;
		int g = (col >> 8) & 0xff;
		int b = col & 0xff;

		r = (int) (r * brightness);
		g = (int) (g * brightness);
		b = (int) (b * brightness);

		return (0xff << 24) | (r << 16) | (g << 8) | b;

	}

}
