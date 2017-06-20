package org.usfirst.frc.team3641.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TrajectoryPoint;

public class Talon implements AbstractTalon{
	private CANTalon talon;
	private CANTalon.MotionProfileStatus talonStatus;
	
	public Talon(int talonID){
		talon  = new CANTalon(talonID);
	}
	
	public void set(double power){
		talon.set(power);
		talonStatus = new CANTalon.MotionProfileStatus();
	}

	@Override
	/**
	 * set the pid of the talon.
	 */
	public void setPIDgains(double feedForward, double proportional, double integral, double derivative) {
		talon.setPID(proportional, integral, derivative);
		talon.setF(feedForward);
	}

	@Override
	public int getNumBufferPoints() {
		talon.getMotionProfileStatus(talonStatus);
		return talonStatus.btmBufferCnt;
	}

	@Override
	public boolean hasUnderrun() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void pushPoints(TrajectoryPoint[] points) {
		// TODO Auto-generated method stub
		
	}
}
