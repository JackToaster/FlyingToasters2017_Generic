package controllers;

import utilities.Logging;
import utilities.Utilities;
import pathfinder.*;
/**
 * class for generation and excecution of motion profiles.
 * @author jackf
 *
 */

//TODO FIX EVERYTHING
public class MotionProfile implements AbstractFeedbackController{
	private Profile profile;
	private double totalTime = 0;
	private MPPoint lastTarget;
	private WheelProfileGenerator wpg;
	
	public PIDcontroller pid;
	public double kV = 0;
	public double kA = 0;
	
	public MotionProfile(PIDcontroller pidController, double velGain, double accelGain, WheelProfileGenerator wheelProfileGen){
		pid= pidController;
		kV = velGain;
		kA = accelGain;
		wpg = wheelProfileGen;
	}
	
	public MotionProfile(PIDcontroller pidController, double velGain, double accelGain, WheelProfileGenerator wheelProfileGen, Profile p){
		pid= pidController;
		kV = velGain;
		kA = accelGain;
		profile = p;
		wpg = wheelProfileGen;
		lastTarget = p.start();
	}
	
	public void setPoints(MPPoint... points){
		if(points.length < 2){
			Logging.w("Useless motion profile - less than 2 points");
		}else{
			profile.setPoints(points);
			lastTarget = profile.start();
		}
	}
	
	public void generateProfileFromPath(Path path){
		profile = wpg.genPoints(path);
	}
	
	public abstract class WheelProfileGenerator {
		public abstract Profile genPoints(Path p);
	}
	
	public class SkidsteerProfileGenerator extends WheelProfileGenerator {
		private double rightOffset;
		
		public SkidsteerProfileGenerator(double rightOffset){
			this.rightOffset = rightOffset;
		}
		
		@Override
		public Profile genPoints(Path p){
			Waypoint firstWP = p.waypoints.get(0);
			MPPoint first = new MPPoint(firstWP.velocity,0,0);
			Point startPoint = firstWP.position.sum(Point.polarPoint(rightOffset, 3 * Math.PI / 2));
			System.out.println("Start vel: " + first.velocity);
			return new Profile();
		}
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
				upperIndex++;
			}
			if(upperIndex > 0){
				int lowerIndex = upperIndex - 1;
				
				MPPoint upper = getPoint(upperIndex);
				MPPoint lower = getPoint(lowerIndex);
				
				//find what fraction of the way from upper to lower the time is
				double alpha = (time - lower.time) / (upper.time - lower.time); 
				
				return lower.lerp(upper, alpha);
			}else{
				return getPoint(0);
			}
		}
		
		// returns the first point
		public MPPoint start() {
			return getPoint(0);
		}

		// returns the last point
		public MPPoint end() {
			return getPoint(trajectory.length - 1);
		}

		// returns the time of the last point
		public double getEndTime() {
			return end().time;
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
		
		public MPPoint lerp(MPPoint p2, double alpha){
			double newVel = Utilities.lerp(this.velocity, p2.velocity, alpha);
			double newPos = Utilities.lerp(this.position, p2.position, alpha);
			double newTime = Utilities.lerp(this.time, p2.time, alpha);
			return new MPPoint(newVel, newPos, newTime);
		}
	}

	@Override
	public void setGains(double... gains){
		if (gains.length != 5) {// check to see if there are the right number
			// of values
			Logging.logMessage("Invalid number of parameters for MotionProfile.setGains", Logging.Priority.ERROR);

		} else {
			pid.setGains(gains[0],gains[1],gains[2]);
			kV = gains[3];
			kA = gains[4];
		}
	}
	
	@Override
	public void readFromPrefs(String name){
		//TODO make read from prefs
	}
	
	//setting/getting the setpoint of a motion profile makes no sense.
	@Override
	public void setSetpoint(double setpoint){}
	@Override
	public double getSetpoint(){ return 0;}
	
	//run the control loop
	public double run(double current, double deltaTime){
		//update current time
		totalTime += deltaTime;
		
		
		//stores the target position/velocity
		MPPoint target;
		double accel;
		if(totalTime >= profile.getEndTime()){
			Logging.l("Motion profile finished runnig");
			target = profile.end();
			accel = 0;
		}else{
			target = profile.getInterpolatedPoint(totalTime);
			accel = (target.velocity - lastTarget.velocity) / deltaTime;
		}
		
		//set up the PIDs
		pid.setSetpoint(target.position);
		
		double pidOut = pid.run(current, deltaTime);
		
		double velOut = kV * target.velocity;
		double accelOut = kA * accel;
		
		return pidOut + velOut + accelOut;
	}
	
	public void reset(){
		
	}

	public void logStatus(){
		Logging.l("Motion profile - current time: " + totalTime);
	}
}
