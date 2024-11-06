package me.NoahCagle.yur.world;

import me.NoahCagle.yur.Game;
import me.NoahCagle.yur.graphics.Screen;
import me.NoahCagle.yur.graphics.Texture;

public class Boundary {

	public int startX, startY, endX, endY, col, length;
	private Texture texture;
	private boolean hasTexture = false;
	private boolean vertical = false;

	public Boundary(int startX, int startY, int endX, int endY, int col) {
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;

		if (startX == endX) {
			length = endY - startY;
			vertical = true;
		}

		if (startY == endY) {
			length = endX - startX;
		}

		this.col = col;
	}

	public Boundary(int startX, int startY, int endX, int endY, Texture texture) {
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.texture = texture;

		if (startX == endX) {
			length = endY - startY;
			vertical = true;
		}

		if (startY == endY) {
			length = endX - startX;
		}

		hasTexture = true;

		col = 0xffffff;

	}

	public int getColor() {
		return col;
	}

	public boolean hasTexture() {
		return hasTexture;
	}

	public Texture getTexture() {
		return texture;
	}

	// If not vertical, then horizontal
	public boolean isVertical() {
		return vertical;
	}

	public void draw(Screen screen, int playerX, int playerY) {
		int px = playerX - (Game.width / 2) * Game.reduction2d;
		int py = playerY - (Game.height / 2) * Game.reduction2d;
		screen.drawLine((startX - px) / Game.reduction2d, (startY - py) / Game.reduction2d,
				(endX - px) / Game.reduction2d, (endY - py) / Game.reduction2d, col);
	}

}
