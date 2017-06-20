package org.usfirst.frc.team3641.robot;

/**
 * abstract interface for any feedback controller, whether it's just a
 * feedforward or a PID or something else.
 * 
 * @author jackf
 *
 */
public interface AbstractFeedbackController {
	void setGains(double... gains);

	void readFromPrefs(String name);

	double run(double error, double deltaTime);
	
	void reset();

	void logStatus();
}
