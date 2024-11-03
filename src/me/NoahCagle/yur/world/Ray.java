package me.NoahCagle.yur.world;

import java.util.ArrayList;
import java.util.List;

import me.NoahCagle.yur.graphics.Screen;
import me.NoahCagle.yur.logic.Intersection;
import me.NoahCagle.yur.logic.Point;

public class Ray {

	private int x, y;
	private double endX, endY;

	private boolean intersecting = false;
	private double angle;
	private int camDir;

	public int rayLength = 300;
	
	private Intersection intersection;

	public Ray(int x, int y, double angle, int camDir) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.camDir = camDir;
		setVectorDirection();
	}

	private void setVectorDirection() {
		double radians = Math.toRadians(angle + camDir);

		double xDir = Math.cos(radians);
		double yDir = Math.sin(radians);

		endX = xDir * rayLength;
		endY = yDir * rayLength;

	}

	public void setCamDir(int camDir) {
		this.camDir = camDir;
		setVectorDirection();
	}

	// Using 'Given two points on each line segment' from
	// https://en.wikipedia.org/wiki/Line%E2%80%93line_intersection
	public void detectIntersection(Boundary[] boundaries) {
		List<Intersection> intersections = new ArrayList<Intersection>();

		for (Boundary b : boundaries) {
			int x1 = x;
			int y1 = y;
			int x2 = (int) (x + endX);
			int y2 = (int) (y + endY);
			int x3 = b.startX;
			int y3 = b.startY;
			int x4 = b.endX;
			int y4 = b.endY;

			double tNum = ((x1 - x3) * (y3 - y4)) - ((y1 - y3) * (x3 - x4));
			double tDen = ((x1 - x2) * (y3 - y4)) - ((y1 - y2) * (x3 - x4));

			double uNum = ((x1 - x2) * (y1 - y3)) - ((y1 - y2) * (x1 - x3));
			double uDen = ((x1 - x2) * (y3 - y4)) - ((y1 - y2) * (x3 - x4));

			if (tDen != 0 && uDen != 0) {

				double t = tNum / tDen;
				double u = -(uNum / uDen);

				if ((t >= 0 && t <= 1) && (u >= 0 && u <= 1)) {
					int pX = (int) (x3 + (u * (x4 - x3)));
					int pY = (int) (y3 + (u * (y4 - y3)));

					intersections.add(new Intersection(new Point(pX, pY), b));

				}
			}
		}
		
		if (intersections.size() == 0) {
			intersecting = false;
		} else {
			intersecting = true;
			
			if (intersections.size() == 1) {
				Intersection i = intersections.get(0);
				double dist = Math.abs(Math.sqrt(Math.pow(x - i.getPoint().getX(), 2) + Math.pow(y - i.getPoint().getY(), 2)));
				i.setDistance(dist);
				intersection = i;
			} else {
				intersection = findClosest(intersections);
			}
			
		}

	}
	
	public Intersection getIntersection() {
		return intersection;
	}

	public void setOrigin(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	private Intersection findClosest(List<Intersection> intersections) {
		Intersection ret = null;
		double tempDistance = 0;
		
		for (Intersection i : intersections) {
			// Uses x^2 + y^2 = c^2, finds the absolute value of c
			double dist = Math.abs(Math.sqrt(Math.pow(x - i.getPoint().getX(), 2) + Math.pow(y - i.getPoint().getY(), 2)));
			if (ret != null) {
				if (dist < tempDistance) {
					ret = i;
					tempDistance = dist;
				}
			} else {
				ret = i;
				tempDistance = dist;
			}
		}
		
		ret.setDistance(tempDistance);
		
		return ret;
	}
	
	public boolean isIntersecting() {
		return intersecting;
	}

	public void draw(Screen screen) {

		if (intersecting) {
			screen.drawLine(x, y, intersection.getPoint().getX(), intersection.getPoint().getY(), 0xff0000);
			screen.drawPoint(intersection.getPoint().getX(), intersection.getPoint().getY(), 0x0000ff);
		} else {
			screen.drawLine(x, y, (int) (x + endX), (int) (y + endY), 0xff0000);
		}

	}

}