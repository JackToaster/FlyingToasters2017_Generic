package simulation;

import java.util.HashMap;

public class SimMotor {
	
	public static HashMap<Integer,MotorInfo> motors;
	
	public SimMotor(){
		motors = new HashMap<Integer, MotorInfo>();
	}
	
	public void addMotor(int id, double acceleration, double maxVel, double currentMult, int pdpPort) {
		MotorInfo newMotor = new MotorInfo(acceleration, maxVel, currentMult);
		motors.put((Integer) id, newMotor);
		PDPJNI.addMotor((Byte) (byte) pdpPort, (Integer) id);
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
			ticksPerRevolution = 1;
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
			this.throttle = throttle;
		}
		public void update(double dT){
			vel += accel * throttle * dT;
			vel /= 1.0 + dT;
			position += vel * dT;
			limitSpeed();
			currentAmps = throttle * currentMult * (-(vel/maxVel) + 1.5);
		}
		public int getPos(){
			return (int) (ticksPerRevolution * position);
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
