package me.NoahCagle.yur.logic;

import me.NoahCagle.yur.world.Boundary;

public class Intersection {

	private Point point;
	private Boundary boundary;
	private double distance;
	public boolean distanceSet = false;

	public Intersection(Point point, Boundary boundary) {
		this.point = point;
		this.boundary = boundary;
	}
	
	public Point getPoint() {
		return point;
	}

	public Boundary getBoundary() {
		return boundary;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
		if (!distanceSet) distanceSet = true;
	}

}