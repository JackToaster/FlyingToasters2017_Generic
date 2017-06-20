package org.usfirst.frc.team3641.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

public class LinkedTalons implements AbstractTalon {
	private int numberOfTalons;
	private CANTalon[] talons;
	private CANTalon feedbackTalon = null;
	
	/**
	 * Creates a new set of linked talons.
	 * 
	 * @param talonIDs
	 *            Each of the IDs you want to control.
	 */
	public LinkedTalons(int... talonIDs) {
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
	public void setBreakMode(boolean on) {
		for (CANTalon talon : talons)
			talon.enableBrakeMode(on);
	}

}
