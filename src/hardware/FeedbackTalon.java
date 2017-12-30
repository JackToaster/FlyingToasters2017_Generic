package hardware;

import simulation.CANTalon.FeedbackDevice;

import controllers.AbstractFeedbackController;
import utilities.Logging;
import utilities.Utilities;

public class FeedbackTalon extends Talon implements FeedbackMotorController, Utilities.Conversions {
	
	private AbstractFeedbackController feedbackController;
	private boolean feedbackActive = false;
	
	public FeedbackTalon(int talonID) {
		super(talonID);
	}
	
	public FeedbackTalon(int talonID, FeedbackDevice device){
		super(talonID);
		setFeedbackDevice(device);
	}
	@Override
	public double getPosition() {
		return Distance.ENCODER_TICK.convert(talon.getEncPosition(), Distance.M);
	}

	@Override
	public void setFeedbackController(AbstractFeedbackController controller) {
		feedbackController = controller;
	}

	@Override
	public void setFeedbackActive(boolean active) {
		feedbackActive = active;
	}

	@Override
	public boolean getFeedbackActive() {
		return feedbackActive;
	}

	@Override
	public void runFeedback(double deltaTime) {
		if(feedbackActive){
			double output = feedbackController.run(getPosition(), deltaTime);
			setPower(output);
		}else{
			Logging.w("runFeedback run with feedback inactive");
		}
	}

	@Override
	public void setSetpoint(double setpoint) {
		feedbackController.setSetpoint(setpoint);		
	}

	@Override
	public double getSetpoint() {
		return feedbackController.getSetpoint();
	}

	@Override
	public void setFeedbackDevice(FeedbackDevice device) {
		talon.setFeedbackDevice(device);
	}

	@Override
	public AbstractFeedbackController getFeedbackController() {
		return feedbackController;
	}

}
