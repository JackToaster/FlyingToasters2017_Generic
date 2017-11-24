package simulation;

import java.util.HashMap;

public class SimMotor {
	
	public static HashMap<Integer,MotorInfo> motors;
	
	public SimMotor(){
		motors = new HashMap<Integer, MotorInfo>();
	}
	
	public void addMotor(int id, double acceleration, double maxVel) {
		MotorInfo newMotor = new MotorInfo(acceleration, maxVel);
		motors.put((Integer) id, newMotor);
	}
	
	public void updateMotors(double dT){
		for(Integer i : motors.keySet()){
			MotorInfo current = motors.get(i);
			current.update(dT);
			motors.replace(i, current);
		}
	}
	
	public class MotorInfo{
		private double accel;
		private double maxVel;
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
		
		public void setThrottle(double throttle){
			this.throttle = throttle;
		}
		public void update(double dT){
			vel += accel * throttle * dT;
			vel /= 1.0 + dT;
			position += vel;
			limitSpeed();
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
	}
}
