package controllers.motion_profiles;
import pathfinder.*;
//wheel profile generator for robots with wheels on either side
public class SkidsteerProfileGenerator extends WheelProfileGenerator {
	//how far to the right the wheel is, negative for left
	private double rightOffset;
	
	//constructor
	public SkidsteerProfileGenerator(double rightOffset){
		this.rightOffset = rightOffset;
	}
	//generate the motion profile for the path
	@Override
	public Profile genPoints(Path p, double offset){
		Profile outProfile = new Profile(p.waypoints.size());
		Waypoint firstWP = p.waypoints.get(0);
		Point firstPoint = getOffsetPoint(firstWP);
		MPPoint startMPPoint = new MPPoint(firstWP.velocity,offset,0);

		outProfile.setPoint(0,startMPPoint);

		//loop through every other point and add it to the profile
		double totalDist = offset;
		Point lastOffsetPoint = firstPoint;
		for(int i = 1; i < p.waypoints.size(); i++){
			//get the current waypoint
			Waypoint wp = p.waypoints.get(i);

			//get the position of this wheel at the point
			Point offsetPoint = getOffsetPoint(wp);

			//get the velocity and distance
			double dT = wp.time - p.waypoints.get(i-1).time;
			double dist = lastOffsetPoint.distance(offsetPoint);
			totalDist += dist;
			double vel = dist / dT;
			//create the profile point and add it
			MPPoint currentMPPoint = new MPPoint(vel, totalDist, wp.time);
			outProfile.setPoint(i, currentMPPoint);
			
			lastOffsetPoint = offsetPoint;
		}
		//return it!
		return outProfile;
	}

	private Point getOffsetPoint(Waypoint wp){
		return wp.position.sum(Point.PolarPoint(rightOffset, 3 * Math.PI / 2));
	}
}