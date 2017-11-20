package controllers;

import java.util.ArrayList;

import hardware.DriveBase;
import utilities.Utilities;

/**
 * class for generation and excecution of motion profiles.
 * @author jackf
 *
 */

//TODO FIX EVERYTHING
public class MotionProfile implements AbstractFeedbackController{
	private Profile profile;
	private double totalTime = 0;
	
	public PIDcontroller pid;
	
	public MotionProfile(PIDcontroller pidController){
		pid = pidController;
	}
	
	
	public class Profile {
		private MPPoint[] trajectory;
		
		public Profile(){}
		
		public void setPoints(MPPoint... points){
			if(points.length < 2){
				Logging.e("Useless motion profile - less than 2 points");
			}else{
				trajectory = points;
			}
		}
		
		public MPPoint getPoint(int index){
			return trajectory[index];
		}
		
		public MPPoint getInterpolatedPoint(double time){
			int upperIndex = 0;
			while(getPoint(upperIndex).time < time){
				upperIndex++
			}
			if(upperIndex > 0){
				int lowerIndex = upperIndex - 1;
				
				MPPoint upper = getPoint(upperIndex);
				MPPoint lower = getPoint(lowerIndex);
				
			}else{
				return getPoint(0);
			}
		}
	}
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
	}
	
	public void setGains(double... gains);

	public void readFromPrefs(String name);
	
	public void setSetpoint(double setpoint);
	
	public double getSetpoint();
	
	public double run(double current, double deltaTime){
		totalTime += deltaTime;
	}
	
	public void reset();

	public void logStatus();
}
