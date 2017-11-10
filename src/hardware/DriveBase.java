package hardware;

import java.util.HashMap;

import com.ctre.CANTalon.FeedbackDevice;

/**
 * Drivebase class for all of your driving needs
 * 
 * @author jackf
 *
 */
public interface DriveBase{
	void drive(double... inputs);
	void update();
	void setActive(boolean active);
	boolean getActive();
}
