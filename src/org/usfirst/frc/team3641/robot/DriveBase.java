package org.usfirst.frc.team3641.robot;

import java.util.HashMap;

import com.ctre.CANTalon.FeedbackDevice;

/**
 * Drivebase class for all of your driving needs
 * 
 * @author jackf
 *
 */
public class DriveBase implements Utilities.Conversions, Constants.Talons {
	
	enum State {
		NONE(null), FOLLOW_PROFILE(NONE), DRIVE_ARCADE(NONE);

		private final State nextState;

		State(State next) {
			nextState = next;
		}

		public State increment() {
			if (nextState != null)
				return nextState;
			else
				return this;
		}
	}

	State currentState;
	
	
	public static HashMap<String, AbstractTalon> talons;

	/**
	 * create a drive base. Defaults to NONE state.
	 */
	public DriveBase() {
		//call constructor with none state
		this(State.NONE);
	}

	/**
	 * create a new drive base starting in a state
	 * 
	 * @param s
	 */
	public DriveBase(State s) {
		//set current state
		currentState = s;
		
		//set up linked talons
		LinkedTalons left = new LinkedTalons(DRIVEBASE_LEFT_1, DRIVEBASE_LEFT_2);
		left.setFeedbackDevice(LEFT_ENCODER_TALON, FeedbackDevice.CtreMagEncoder_Absolute);

		LinkedTalons right = new LinkedTalons(DRIVEBASE_RIGHT_1, DRIVEBASE_RIGHT_2);
		right.setFeedbackDevice(RIGHT_ENCODER_TALON, FeedbackDevice.CtreMagEncoder_Absolute);
		
		talons.put("left", left);
		talons.put("right", right);
	}

	/**
	 * Drives The robot with tank drive.
	 * 
	 * @param leftPower
	 *            Power for the left half of the drive train (your left stick)
	 * @param rightPower
	 *            Power for the right half of the drive train (your right stick)
	 */
	public static void driveTank(double leftPower, double rightPower) {

		double maxPower;

		// set the max power to the max of the two powers
		maxPower = Math.max(leftPower, rightPower);

		if (maxPower > 1) {
			leftPower /= maxPower;
			rightPower /= maxPower;
		}

		Logging.logMessage("Left Power: " + leftPower + "; Right Power: " + rightPower, Logging.Priority.LOW);

		talons.get("left").set(leftPower);
		talons.get("right").set(-rightPower);
	}
	
	
}
