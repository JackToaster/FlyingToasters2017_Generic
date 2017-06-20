package org.usfirst.frc.team3641.robot;

/**
 * PID controller class. Most basic possible, no deadbands or anything silly
 * like that. If you want fancy stuff like deadbands, create a new PID class
 * that extends this one.
 *
 * @author jackf
 *
 */

// TODO add fancy class that does all the stuff the old PID did
public class PIDcontroller extends ProportionalController {
	public double kI, kD;
	private double integral = 0;;
	private double lastError = 0;

	/**
	 * create a PID controller with no feedforward
	 * 
	 * @param pGain
	 * @param iGain
	 * @param dGain
	 */
	public PIDcontroller(double pGain, double iGain, double dGain) {
		this(pGain, iGain, dGain, 0);
	}

	/**
	 * create a PID controller
	 * 
	 * @param pGain
	 * @param iGain
	 * @param dGain
	 * @param ffGain
	 */
	public PIDcontroller(double pGain, double iGain, double dGain, double ffGain) {
		super(pGain, ffGain);
		kI = iGain;
		kD = dGain;
	}

	@Override
	/**
	 * set the gains for the proportional controller, logs an error if the wrong
	 * number are given.
	 */
	public void setGains(double... gains) {
		if (gains.length != 3) {// check to see if there are the right number
			// of values
			Logging.logMessage("Invalid number of parameters for PIDcontroller.setGains", Logging.Priority.ERROR);

		} else {
			super.setGains(gains[0], gains[1]);
			kI = gains[2];
			kD = gains[3];
		}
	}

	@Override
	/**
	 * calculate the output of the PID loop
	 */
	public double run(double error, double deltaTime) {
		// continue integrating
		integral += error * deltaTime;

		// find the change in error / time (dError / dT)
		double deltaError = (error - lastError) / deltaTime;

		// calculate the proportional + FF part of the PID
		double proportionalValue = super.run(error, deltaTime);

		// calculate the integral + derivative parts
		double integralValue = integral * kI;
		double derivativeValue = deltaError * kD;

		// set the last error for next loop
		lastError = error;

		// return the value
		return proportionalValue + integralValue + derivativeValue;
	}

	@Override
	/**
	 * reset the integral & derivative terms
	 */
	public void reset() {
		integral = 0;
		lastError = 0;
	}

	@Override
	/**
	 * log the current status of the controller
	 */
	public void logStatus() {
		Logging.logMessage("Proportional controller running with gains: kP=" + kP + ", kI=" + kI + ", kD=" + kD
				+ ", kFeedForward=" + kFeedForward, Logging.Priority.LOW);
	}
}
