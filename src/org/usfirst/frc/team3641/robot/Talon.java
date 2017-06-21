package org.usfirst.frc.team3641.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.MotionProfileStatus;
import com.ctre.CANTalon.TrajectoryPoint;

public class Talon implements AbstractTalon {
	private CANTalon talon;
	private CANTalon.MotionProfileStatus talonStatus;

	public Talon(int talonID) {
		talonStatus = new CANTalon.MotionProfileStatus();
		talon = new CANTalon(talonID);
		talon.changeMotionControlFramePeriod(5);
	}

	public void set(double power) {
		talon.set(power);
	}

	@Override
	/**
	 * set the pid of the talon.
	 */
	public void setPIDgains(double feedForward, double proportional, double integral, double derivative) {
		talon.setPID(proportional, integral, derivative);
		talon.setF(feedForward);
	}

	/**
	 * Get the encoder position from the feedback talon.
	 * 
	 * @return The encoder position in ticks.
	 */
	public int getEncPosition() {
		return talon.getEncPosition();
	}

	/**
	 * Set the current position.
	 * 
	 * @param position
	 *            The number of ticks to set the encoder to.
	 */
	public void setEncPosition(int position) {
		talon.setEncPosition(position);
	}

	public boolean hasUnderrun() {
		talon.getMotionProfileStatus(talonStatus);
		boolean underrun = talonStatus.hasUnderrun;
		talon.clearMotionProfileHasUnderrun();
		return underrun;
	}

	/**
	 * Push profile points to the talon.
	 */
	@Override
	public void pushPoints(MotionProfile.MPPoint[] points) {
		//create a new trajectory point to push to the talon
		CANTalon.TrajectoryPoint point = new CANTalon.TrajectoryPoint();
		
		//log a warning if the motion profile has underrun
		if(hasUnderrun()){
			Logging.logMessage("Motion profile has underrun!", Logging.Priority.WARN);
		}
		
		// loop through the points and push them to the talon's buffer.
		for (int i = 0; i < points.length; i++) {
			// position of the point
			point.position = points[i].position;
			// velocity of the point
			point.velocity = points[i].velocity;
			// time in ms to complete the point
			point.timeDurMs = points[i].time;
			// select the correct PIDF values
			point.profileSlotSelect = 0;
			// make sure to correct position as well as velocity.
			point.velocityOnly = false;
			
			//set the point to first if necessary
			point.zeroPos = i == 0;
			
			//set the point to last if necessary
			point.isLastPoint = i == points.length - 1;
			
			//push the mp to the talon
			talon.pushMotionProfileTrajectory(point);
		}
	}

	@Override
	public MotionProfileStatus getStatus() {
		talon.getMotionProfileStatus(talonStatus);
		return talonStatus;
	}
}
