package hardware;

import com.ctre.CANTalon.FeedbackDevice;

import controllers.AbstractFeedbackController;

public interface FeedbackMotorController extends MotorController {
	double getPosition();
	void setFeedbackDevice(FeedbackDevice device);
	void setFeedbackController(AbstractFeedbackController controller);
	void setFeedbackActive(boolean active);
	boolean getFeedbackActive();
	void setSetpoint(double setpoint);
	double getSetpoint();
	void runFeedback(double deltaTime);
}
