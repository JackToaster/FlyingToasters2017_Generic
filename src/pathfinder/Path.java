package pathfinder;

import java.util.ArrayList;

public class Path {
	static final double maxVel = 4.0;
	static final int defaultPoints = 200;
	
	//a path consists of a list of waypoints.
	ArrayList<Waypoint> waypoints;
	
	public Path(Waypoint start, Waypoint end){
		this(start, end, defaultPoints, maxVel);
	}
	
	//create a path from two waypoints
	public Path(Waypoint start, Waypoint end, int numberOfPoints, double velocity) {
		waypoints = new ArrayList<Waypoint>();
		genBezierPath(start, end, numberOfPoints, 0.5);
		setSpeeds(velocity);
		alignWaypoints();
	}
	
	//sets the speeds of the points on the path
	void setSpeeds(double velocity){
		for(int i = 0; i < waypoints.size(); i++){
			//wow big equation makes an S-curve
			waypoints.get(i).velocity = 
				(-0.5 * Math.cos(((double) i / (double)waypoints.size()) * 2.0 * Math.PI) + 0.5) * velocity;
		}
	}
	
	//generates a path with a bezier curve
	void genBezierPath(Waypoint start, Waypoint end, int numberOfPoints, double tightness) {
		//get the location of the start and end points
		Point startPoint = start.getPoint();
		Point endPoint = end.getPoint();
		double distance = startPoint.distance(endPoint);
		double gpLength = distance / 2 * tightness;

		Point startOffset = Point.PolarPoint(gpLength, start.rotation);
		Point endOffset =  Point.PolarPoint(gpLength, end.rotation);

		Point gp1 = startPoint.sum(startOffset);
		Point gp2 = endPoint.sum(endOffset);

		waypoints.add(start);
		for (int i = 1; i < numberOfPoints; i++) {
			double alpha = (double) i / (double) numberOfPoints;
			Point p1 = Point.lerp(startPoint, gp1, alpha);
			Point p2 = Point.lerp(gp1, gp2, alpha);
			Point p3 = Point.lerp(gp2, endPoint, alpha);

			Point p5 = Point.lerp(p1, p2, alpha);
			Point p6 = Point.lerp(p2, p3, alpha);

			Point p = Point.lerp(p5, p6, alpha);
			Waypoint wp = new Waypoint(p, 0);
			waypoints.add(wp);
		}
		waypoints.add(end);
	}
	//aligns the waypoints all pointing to each other.
	void alignWaypoints() {
		for (int i = 1; i < waypoints.size() - 1; i++) {
			waypoints.get(i).pointTowards(waypoints.get(i + 1).getPoint());
		}
	}
	
	@Override
	public String toString(){
		String out = "";
		for( Waypoint wp : waypoints){
			out = out + wp.toString() + "\n";
		}
		return out;
	}
}
