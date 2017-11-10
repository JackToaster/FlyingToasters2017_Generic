package controllers;

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
	
	void setSetpoint(double setpoint);
	
	double getSetpoint();
	
	double run(double current, double deltaTime);
	
	void reset();

	void logStatus();
}
