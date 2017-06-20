package org.usfirst.frc.team3641.robot;

public interface Constants {

	/**
	 * Talons on the CAN bus
	 * 
	 * @author jackf
	 *
	 */
	public interface Talons {
		int DRIVEBASE_LEFT_1 = 12;
		int DRIVEBASE_LEFT_2 = 13;
		int DRIVEBASE_LEFT_3 = 14;
		int LEFT_ENCODER_TALON = DRIVEBASE_LEFT_3;

		int DRIVEBASE_RIGHT_1 = 1;
		int DRIVEBASE_RIGHT_2 = 2;
		int DRIVEBASE_RIGHT_3 = 3;
		int RIGHT_ENCODER_TALON = DRIVEBASE_RIGHT_3;

		int SHOOTER_RIGHT = 9;
		int SHOOTER_LEFT = 10;
		int CENTER_AGITATOR = 11;
		int TURRET = 0;
	}

	/**
	 * Measurements of various robot/field parts
	 * 
	 * @author jackf
	 *
	 */
	public interface Measurments {
		double DRIVE_WHEEL_DIAMETER = 0.0935;
		double DRIVE_WHEEL_CIRCUMFERENCE = Math.PI * DRIVE_WHEEL_DIAMETER;
		double DRIVE_ENCODER_TICKS_PER_TURN = 4096.0;

		double FIELD_WIDTH = 1111; // TODO set correct field dimensions
		double FIELD_LENGTH = 1111;
	}
}
