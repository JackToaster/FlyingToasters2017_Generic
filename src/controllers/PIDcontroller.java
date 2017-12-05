package controllers;

import utilities.Logging;

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
	private double kI, kD;
	private boolean dOnMeasurement = false;
	private double maxIntegral;
	private boolean limitIntegral = false;
	private double integral = 0;
	private double lastReading = 0;
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
	
	public PIDcontroller(double pGain, double iGain, double dGain, double maxI, boolean limitI) {
		this(pGain, iGain, dGain, 0);
		maxIntegral = maxI;
		limitIntegral = limitI;
	}
	
	public PIDcontroller(double pGain, double iGain, double dGain, double ffGain, double maxI, boolean limitI) {
		this(pGain, iGain, dGain, ffGain);
		maxIntegral = maxI;
		limitIntegral = limitI;
	}
	
	public void setILimit(double maxI){
		maxIntegral = maxI;
		limitIntegral = true;
	}
	
	public void limitI(boolean limit){
		limitIntegral = limit;
	}
	
	public void setDOnMeasurement(boolean onMeasurement){
		dOnMeasurement = onMeasurement;
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
		if (gains.length != 3 && gains.length != 4) {// check to see if there are the right number
			// of values
			Logging.logMessage("Invalid number of parameters for PIDcontroller.setGains", Logging.Priority.ERROR);

		} else {
			if(gains.length == 4){
				super.setGains(gains[0], gains[1]);
				kI = gains[2];
				kD = gains[3];
			}else{
				super.setGains(0, gains[0]);
				kI = gains[1];
				kD = gains[2];
			}
		}
	}
	
	
	@Override
	/**
	 * calculate the output of the PID loop
	 */
	public double run(double current, double deltaTime) {
		double error = current - setpoint;
		// continue integrating
		integral += error * deltaTime;
		if (limitIntegral) {
			if (Math.abs(integral) > maxIntegral / kI) {
				if (integral > 0) {
					integral = maxIntegral / kI;
				} else {
					integral = -maxIntegral / kI;
				}
			}
		}
		
		double deltaError;
		if(dOnMeasurement){
			// find the change in reading / time (dError / dT)
			deltaError = (current- lastReading) / deltaTime;
		}else{
			deltaError = (error - lastError) / deltaTime;
		}

		// calculate the proportional + FF part of the PID
		double proportionalValue = super.run(current, deltaTime);

		// calculate the integral + derivative parts
		double integralValue = -integral * kI;
		double derivativeValue = -deltaError * kD;
		
		// set the last error for next loop
		lastReading = current;
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
		lastReading = 0;
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
