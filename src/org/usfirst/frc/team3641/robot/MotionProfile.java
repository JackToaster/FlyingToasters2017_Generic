package org.usfirst.frc.team3641.robot;

import java.util.ArrayList;

/**
 * class for generation and excecution of motion profiles.
 * @author jackf
 *
 */
public class MotionProfile {
	ArrayList<Utilities.robotPosition> path;
	int stepTime = 10;
	
	/**
	 * Motion profile point class, has a position, velocity, and time (in ms)
	 * @author jackf
	 *
	 */
	public class MPPoint {
		public double position;
		public double velocity;
		public int time = stepTime;
		
		public MPPoint(double vel, double pos, int t){
			velocity = vel;
			position = pos;
			time = t;
		}
	}
}
