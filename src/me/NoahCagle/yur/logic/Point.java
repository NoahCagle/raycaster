package me.NoahCagle.yur.logic;

public class Point {

	private int x, y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean equals(Point p) {
		if (p.getX() == x && p.getY() == y)
			return true;
		return false;
	}

}
