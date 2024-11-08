package me.NoahCagle.yur.graphics;

import me.NoahCagle.yur.logic.Intersection;
import me.NoahCagle.yur.world.Boundary;

public class Screen {

	public int width, height;
	public int[] pixels;

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	public void drawLine(int x1, int y1, int x2, int y2, int col) {
		// Bresenham's line algorithm to draw a line between two points
		int dx = Math.abs(x2 - x1);
		int dy = Math.abs(y2 - y1);
		int sx = x1 < x2 ? 1 : -1;
		int sy = y1 < y2 ? 1 : -1;
		int err = dx - dy;

		while (true) {
			// Check bounds before plotting
			if (x1 >= 0 && x1 < width && y1 >= 0 && y1 < height) {
				pixels[x1 + y1 * width] = col;
			}
			if (x1 == x2 && y1 == y2)
				break;
			int e2 = err * 2;
			if (e2 > -dy) {
				err -= dy;
				x1 += sx;
			}
			if (e2 < dx) {
				err += dx;
				y1 += sy;
			}
		}
	}

	public void drawPoint(int x, int y, int col) {
		int size = 8;
		for (int yp = 0; yp < size; yp++) {
			for (int xp = 0; xp < size; xp++) {
				int xa = x + xp;
				int ya = y + yp;
				xa -= (size / 2);
				ya -= (size / 2);

				int draw = xa + ya * width;
				if (draw >= 0 && draw < pixels.length) {
					pixels[xa + ya * width] = col;
				}

			}
		}
	}

	public void fillRect(int x, int y, int w, int h, int col) {
		for (int yp = 0; yp < h; yp++) {
			for (int xp = 0; xp < w; xp++) {
				int xa = x + xp;
				int ya = (y - (h / 2)) + yp;

				if (xa < 0)
					xa = 0;
				if (xa >= width)
					xa = width - 1;
				if (ya < 0)
					ya = 0;
				if (ya >= height)
					ya = height - 1;

				pixels[xa + ya * width] = col;

			}
		}
	}

	public void drawTexturedColumn(int x, int y, int w, int h, Intersection intersection, Texture texture, double distance) {
		for (int yp = 0; yp < h; yp++) {
			for (int xp = 0; xp < w; xp++) {
				int xa = x + xp;
				int ya = (y - (h / 2)) + yp;

				if (xa < 0)
					xa = 0;
				if (xa >= width)
					xa = width - 1;
				if (ya < 0)
					ya = 0;
				if (ya >= height)
					ya = height - 1;

				Boundary b = intersection.getBoundary();

				double relativeX = ((double) (b.isVertical() ? intersection.getPoint().getY() - b.startY
						: intersection.getPoint().getX() - b.startX)) / (double) b.length;
				double relativeY = (double) yp / (double) h;

				pixels[xa + ya * width] = adjustColorValue(texture.colorAt(relativeX, relativeY), (distance - .25 < 0 ? 0 : distance - .25));

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

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
//			if (i > pixels.length / 2) pixels[i] = 0x003300;
//			else pixels[i] = 0x0063aa;
			pixels[i] = 0;
		}
	}

	public void sync(int[] pixels) {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = this.pixels[i];
		}
	}

}
