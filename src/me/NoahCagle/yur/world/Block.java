package me.NoahCagle.yur.world;

import me.NoahCagle.yur.graphics.Texture;

public class Block {

	private Boundary[] boundaries;
	private boolean hasTexture = false;

	public Block(int xPos, int yPos, int blockSize, int col) {
		Boundary north = new Boundary(xPos * blockSize, yPos * blockSize, (xPos + 1) * blockSize, yPos * blockSize,
				col);
		Boundary west = new Boundary(xPos * blockSize, yPos * blockSize, xPos * blockSize, (yPos + 1) * blockSize,
				col);
		Boundary south = new Boundary(xPos * blockSize, (yPos + 1) * blockSize, (xPos + 1) * blockSize,
				(yPos + 1) * blockSize, col);
		Boundary east = new Boundary((xPos + 1) * blockSize, (yPos + 1) * blockSize, (xPos + 1) * blockSize,
				yPos * blockSize, col);

		boundaries = new Boundary[] { north, east, south, west };

	}
	
	public Block(int xPos, int yPos, int blockSize, Texture texture) {
		Boundary north = new Boundary(xPos * blockSize, yPos * blockSize, (xPos + 1) * blockSize, yPos * blockSize,
				texture);
		Boundary west = new Boundary(xPos * blockSize, yPos * blockSize, xPos * blockSize, (yPos + 1) * blockSize,
				texture);
		Boundary south = new Boundary(xPos * blockSize, (yPos + 1) * blockSize, (xPos + 1) * blockSize,
				(yPos + 1) * blockSize, texture);
		Boundary east = new Boundary((xPos + 1) * blockSize, (yPos + 1) * blockSize, (xPos + 1) * blockSize,
				yPos * blockSize, texture);

		boundaries = new Boundary[] { north, east, south, west };
		
		hasTexture = true;
		
	}
	
	public boolean hasTexture() {
		return hasTexture;
	}
	
	public Boundary[] getBoundaries() {
		return boundaries;
	}

}
