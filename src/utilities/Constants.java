package utilities;

public abstract class Constants {

	

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
