package org.usfirst.frc.team3641.robot;

/**
 * FeedForward controller class, returns a constant value with the opposite sign
 * as the error.
 * 
 * @author jackf
 *
 */
public class FeedForwardController implements AbstractFeedbackController {
	public double kFeedForward = 0;

	/**
	 * create a new feedforward controller with the proper gain
	 * 
	 * @param feedForwardGain
	 */
	public FeedForwardController(double feedForwardGain) {
		kFeedForward = feedForwardGain;
	}

	@Override
	/**
	 * set the gains for the feedforward controller, logs an error if the wrong
	 * number are given.
	 */
	public void setGains(double... gains) {
		if (gains.length != 1) { // check to see if there are the right number
									// of values
			Logging.logMessage("Invalid number of parameters for FeedForwardController.setGains",
					Logging.Priority.ERROR);
		} else {// set the value
			kFeedForward = gains[0];
		}
	}

	@Override
	/**
	 * read the gains from a preferences file
	 */
	public void readFromPrefs(String name) {
		// TODO add config reading

	}

	@Override
	/**
	 * returns the proper output based on the error
	 */
	public double run(double error, double deltaTime) {
		boolean sign = error > 0;
		if (sign)
			return -kFeedForward;
		else
			return kFeedForward;
	}
	
	@Override
	/**
	 * does literally nothing.
	 */
	public void reset(){}

	@Override
	/**
	 * log the current status of the controller
	 */
	public void logStatus() {
		Logging.logMessage("FeedForward controller running with gain " + kFeedForward, Logging.Priority.LOW);
	}
}
