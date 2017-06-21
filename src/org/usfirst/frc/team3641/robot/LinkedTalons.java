package org.usfirst.frc.team3641.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.MotionProfileStatus;
import com.ctre.CANTalon.TrajectoryPoint;

public class LinkedTalons implements AbstractTalon {
	private int numberOfTalons;
	private CANTalon[] talons;
	private CANTalon feedbackTalon = null;
	private CANTalon.MotionProfileStatus talonStatus;
	
	/**
	 * Creates a new set of linked talons.
	 * 
	 * @param talonIDs
	 *            Each of the IDs you want to control.
	 */
	public LinkedTalons(int... talonIDs) {
		//
		talonStatus = new CANTalon.MotionProfileStatus();
		
		numberOfTalons = talonIDs.length;
		talons = new CANTalon[numberOfTalons];

		for (int i = 0; i < numberOfTalons; i++)
			talons[i] = new CANTalon(talonIDs[i]);
	}

	/**
	 * Set output power of all of the talons.
	 * 
	 * @param power
	 *            The power to set each of the talons to.
	 */
	public void set(double power) {
		for (CANTalon talon : talons)
			talon.set(-power);
	}

	/**
	 * Add a feedback device.
	 * 
	 * @param TalonID
	 *            The ID of the talon you connect the feedback device to.
	 * @param device
	 *            The type of feedback device to use.
	 */
	public void setFeedbackDevice(int TalonID, FeedbackDevice device) {
		feedbackTalon = null;
		for (CANTalon talon : talons)
			if (talon.getDeviceID() == TalonID)
				feedbackTalon = talon;
		if (feedbackTalon == null)
			throw new IllegalArgumentException("There is not Talon with an ID of " + TalonID + " in this object.");
		else
			feedbackTalon.setFeedbackDevice(device);
	}

	/**
	 * Get the encoder position from the feedback talon.
	 * 
	 * @return The encoder position in ticks.
	 */
	public int getEncPosition() {
		return feedbackTalon.getEncPosition();
	}

	/**
	 * Set the current position.
	 * 
	 * @param position
	 *            The number of ticks to set the encoder to.
	 */
	public void setEncPosition(int position) {
		feedbackTalon.setEncPosition(position);
	}

	/**
	 * Turns on or off break mode for all the talons.
	 * 
	 * @param on
	 *            True for on, false for off.
	 */
	public void setBrakeMode(boolean on) {
		for (CANTalon talon : talons)
			talon.enableBrakeMode(on);
	}

	@Override
	public void setPIDgains(double feedForward, double proportional, double integral, double derivative) {
		// TODO Auto-generated method stub
		
	}

	
	public boolean hasUnderrun() {
		feedbackTalon.getMotionProfileStatus(talonStatus);
		boolean underrun = talonStatus.hasUnderrun;
		feedbackTalon.clearMotionProfileHasUnderrun();
		return underrun;
	}

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
			feedbackTalon.pushMotionProfileTrajectory(point);
		}
	}

	@Override
	public MotionProfileStatus getStatus() {
		feedbackTalon.getMotionProfileStatus(talonStatus);
		return talonStatus;// TODO Auto-generated method stub
	}

}
