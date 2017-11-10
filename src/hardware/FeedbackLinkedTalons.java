package hardware;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

import controllers.AbstractFeedbackController;

//TODO finish implementing linked talons
public class FeedbackLinkedTalons extends LinkedTalons implements FeedbackMotorController {
	private CANTalon feedbackTalon;
	
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFeedbackDevice(FeedbackDevice device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFeedbackController(AbstractFeedbackController controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFeedbackActive(boolean active) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getFeedbackActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setSetpoint(double setpoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getSetpoint() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void runFeedback(double deltaTime) {
		// TODO Auto-generated method stub
		
	}
}
