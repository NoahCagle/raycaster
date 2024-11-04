package me.NoahCagle.yur.graphics;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import me.NoahCagle.yur.input.InputListener;
import me.NoahCagle.yur.logic.Intersection;
import me.NoahCagle.yur.world.Ray;

public class Game3D extends Canvas {
	private static final long serialVersionUID = 1L;

	private int width, height, scale;
	private int quantityColumns;
	private int columnWidth;

	private int rayLength = 0;

	private BufferedImage image;
	private int[] pixels;

	private Screen screen;

	private Ray[] rays;

	public Game3D(int width, int height, int scale, Ray[] rays, InputListener input) {
		this.width = width;
		this.height = height;
		this.scale = scale;
		this.rayLength = Ray.rayLength;
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		
		addKeyListener(input);

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
				
				// Testing a different way normalize the distance
				// Modifying distance using graph shown at https://www.desmos.com/calculator/3v0sheddk0
				boolean usingGraph = false;
				double h = h(intersection.getDistance());
				
				// normalizedDistance = 1 when distance = 0, and normalizedDistance = 0 when distance == rayLength (longest a ray can be)
				double normalizedDistance = usingGraph ? h : g(intersection.getDistance());
				
				int col = adjustColorValue(intersection.getBoundary().getColor(), normalizedDistance);
				
				double drawHeight = (normalizedDistance + 0.25) * height;
				
				screen.fillRect((i * columnWidth), height / 2, columnWidth, (int) drawHeight, col);
			}
		}
	}

	private double f(double x) {
		return rayLength / x;
	}

	private double g(double x) {
		return (-x / rayLength) + 1;
	}
	
	private double h(double x) {
		return -(g(x - rayLength / 2) / f(x - rayLength / 2)) + 0.25;
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
