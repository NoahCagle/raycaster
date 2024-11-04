package me.NoahCagle.yur.world;

public class Block {

	private Boundary[] boundaries;

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
	
	public Boundary[] getBoundaries() {
		return boundaries;
	}

}
