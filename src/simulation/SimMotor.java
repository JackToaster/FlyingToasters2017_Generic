package simulation;

import java.util.HashMap;

import simulation.PDPJNI;
import utilities.Utilities.Conversions.Distance;

public class SimMotor {
	
	public static HashMap<Integer,MotorInfo> motors;
	
	public SimMotor(){
		motors = new HashMap<Integer, MotorInfo>();
	}
	
	public void addMotor(int id, double acceleration, double maxVel, double currentMult, int pdpPort) {
		MotorInfo newMotor = new MotorInfo(acceleration, maxVel, currentMult);
		motors.put((Integer) id, newMotor);
		PDPJNI.addMotor((byte) pdpPort, (Integer) id);
	}
	
	public void updateMotors(double dT){
		for(Integer i : motors.keySet()){
			MotorInfo current = motors.get(i);
			current.update(dT);
			motors.put(i, current);
		}
	}
	
	public class MotorInfo{
		private double accel;
		private double maxVel;
		private double currentMult = 1;
		private double currentAmps = 0;
		private double vel;
		private double throttle;
		private double position;
		private int ticksPerRevolution;
		public MotorInfo(double acceleration, double maxVelocity){
			position = 0;
			throttle = 0;
			vel = 0;
			maxVel = maxVelocity;
			accel = acceleration;
			ticksPerRevolution = 1024;
		}
		public MotorInfo(double acceleration, double maxVelocity, int tpr){
			this(acceleration, maxVelocity);
			ticksPerRevolution = tpr;
		}
		
		public MotorInfo(double acceleration, double maxVelocity, double current){
			this(acceleration, maxVelocity);
			currentMult = current;
		}
		
		public void setCurrentMult(double current){
			currentMult = current;
		}
		public void setThrottle(double throttle){
			if(throttle > 1) this.throttle = 1;
			else if(throttle < -1) this.throttle = -1;
			else this.throttle = throttle;
		}
		public void update(double dT){
			double diff = (throttle * maxVel) - vel;
			double impulse = accel * dT;
			if(Math.abs(diff) < impulse) {
				vel = throttle * maxVel;
			}else {
				if(vel > throttle * maxVel) {
					if(vel > 0) {
						vel -= 2 * impulse;
					}else {
						vel -= impulse;
					}
				}else {
					if(vel < 0) {
						vel += 2 * impulse;
					}else {
						vel += impulse;
					}
				}
			}
			
			position += vel * dT;
			limitSpeed();
			//just for test, not accurate in any way.
			currentAmps = throttle * currentMult * (-(vel/maxVel) + 1.5);
		}
		public int getPos(){
			return (int) Distance.M.convert(position, Distance.ENCODER_TICK);
		}
		public void limitSpeed(){
			if(Math.abs(vel) > maxVel){
				if(vel > 0){
					vel = maxVel;
				}else{
					vel = -maxVel;
				}
			}
		}
		
		public double getCurrent(){
			return currentAmps;
		}
		
		public String toString(){
			return "\nPosition: " + position + ", velocity: " + vel + ", current: " + currentAmps;
		}
	}
}
