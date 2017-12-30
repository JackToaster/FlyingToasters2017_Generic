package controllers.motion_profiles;

import utilities.Logging;
import controllers.AbstractFeedbackController;
import controllers.PIDcontroller;
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
	
	public void generateProfileFromPath(Path path, double offset){
		profile = wpg.genPoints(path, offset);
		lastTarget = profile.start();
	}

	@Override
	public void setGains(double... gains) {
		if (gains.length != 5) {// check to see if there are the right number
			// of values
			Logging.logMessage("Invalid number of parameters for MotionProfile.setGains", Logging.Priority.ERROR);

		} else {
			pid.setGains(gains[0], gains[1], gains[2]);
			kV = gains[3];
			kA = gains[4];
		}
	}

	@Override
	public void readFromPrefs(String name) {
		// TODO make read from prefs
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
	
	public String toString() {
		if(profile != null) {
			return "Generator: " + wpg.toString() + "\n Profile:\n" + profile.toString();
		}else {
			return "Generator: " + wpg.toString() + ", no profile generated";
		}
	}
}
