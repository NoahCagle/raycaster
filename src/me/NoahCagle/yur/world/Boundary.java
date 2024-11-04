package me.NoahCagle.yur.world;

import me.NoahCagle.yur.Game;
import me.NoahCagle.yur.graphics.Screen;

public class Boundary {
	
	public int startX, startY, endX, endY, col;
	
	public Boundary(int startX, int startY, int endX, int endY, int col) {
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.col = col;
	}
	
	public int getColor() {
		return col;
	}
	
	public void draw(Screen screen, int playerX, int playerY) {
		int px = playerX - (Game.width / 2);
		int py = playerY - (Game.height / 2);
		screen.drawLine(startX - px, startY - py, endX - px, endY - py, col);
	}

}
