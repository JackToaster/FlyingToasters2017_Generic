package controllers.motion_profiles;

import controllers.motion_profiles.MPPoint;
import utilities.Utilities;

/**
 * Motion profile point class, has a position, velocity, and time (in ms)
 * 
 * @author jackf
 *
 */
public class MPPoint {
	public double position;
	public double velocity;
	public double time;

	public MPPoint(double vel, double pos, double t) {
		velocity = vel;
		position = pos;
		time = t;
	}
	
	public MPPoint lerp(MPPoint p2, double alpha){
		double newVel = Utilities.lerp(this.velocity, p2.velocity, alpha);
		double newPos = Utilities.lerp(this.position, p2.position, alpha);
		double newTime = Utilities.lerp(this.time, p2.time, alpha);
		return new MPPoint(newVel, newPos, newTime);
	}
	
	public String toString() {
		return "Position: " + position + ", velocity: " + velocity + ", time: " + time;
	}
}
