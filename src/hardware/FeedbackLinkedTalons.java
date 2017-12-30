package hardware;

import simulation.CANTalon;
import simulation.CANTalon.FeedbackDevice;

import controllers.AbstractFeedbackController;
import utilities.Logging;
import utilities.Utilities.Conversions.Distance;

//TODO finish implementing linked talons
public class FeedbackLinkedTalons extends LinkedTalons implements FeedbackMotorController {
	private CANTalon feedbackTalon;
	private AbstractFeedbackController feedbackController;
	private boolean feedbackActive = false;
	/**
	 * Creates a new set of linked talons.
	 * 
	 * @param talonIDs
	 *            Each of the IDs you want to control.
	 */
	public FeedbackLinkedTalons(int feedbackTalonID, int... talonIDs) {
		super(talonIDs);
		feedbackTalon = new CANTalon(feedbackTalonID);
	}
	
	public FeedbackLinkedTalons(FeedbackDevice device, int feedbackTalonID, int... talonIDs){
		super(talonIDs);
		feedbackTalon = new CANTalon(feedbackTalonID);
		
	}

	@Override
	/**
	 * Set output power of all of the talons.
	 * 
	 * @param power
	 *            The power to set each of the talons to.
	 */
	public void setPower(double power) {
		currentPower = power;
		for (CANTalon talon : talons)
			talon.set(power);
		feedbackTalon.set(power);
	}

	@Override
	public double getPower() {
		return currentPower;
	}

	@Override
	public double getPosition() {
		return Distance.ENCODER_TICK.convert(feedbackTalon.getEncPosition(), Distance.M);
	}

	@Override
	public void setFeedbackDevice(FeedbackDevice device) {
		feedbackTalon.setFeedbackDevice(device);
	}

	@Override
	//set the feedback controller,
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
	public void setSetpoint(double setpoint) {
		feedbackController.setSetpoint(setpoint);		
	}

	@Override
	public double getSetpoint() {
		return feedbackController.getSetpoint();
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
	
	public void setCurrentLimit(int amps){
		super.setCurrentLimit(amps);
		feedbackTalon.setCurrentLimit(amps);
	}
	
	public void EnableCurrentLimit(boolean enable){
		super.EnableCurrentLimit(enable);
		feedbackTalon.EnableCurrentLimit(enable);
	}

	@Override
	public AbstractFeedbackController getFeedbackController() {
		return feedbackController;
	}
}
