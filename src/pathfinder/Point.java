package pathfinder;

public class Point {
	//the coordinates of the point
	public double x, y;
	//create a point the normal way
	public Point(double xPosition, double yPosition) {
		x = xPosition;
		y = yPosition;
	}
	
	// create a point from polar coordinates
	public static Point PolarPoint(double r, double theta) {
		double xPosition = r * Math.cos(theta);
		double yPosition = r * Math.sin(theta);
		return new Point(xPosition, yPosition);

	}
	
	//linear interpolation between two points
	public static Point lerp(Point startPoint, Point endPoint, double alpha) {
		double xPosition = (endPoint.x - startPoint.x) * alpha + startPoint.x;
		double yPosition = (endPoint.y - startPoint.y) * alpha + startPoint.y;
		return new Point(xPosition, yPosition);
	}
	
	//get the distance to another point
	public double distance(Point p2) {
		return Math.sqrt(Math.pow(x - p2.x, 2) + Math.pow(y - p2.y, 2));
	}
	
	//add two points together
	public Point sum(Point p2) {
		return new Point(x + p2.x, y + p2.y);
	}
	
	//offset this point by another one
	public void add(Point p2) {
		x += p2.x;
		y += p2.y;
	}
}
