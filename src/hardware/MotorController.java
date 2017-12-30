package hardware;

//TODO implement current limits
public interface MotorController {
	void setPower(double power);
	double getPower();
	
	void setCurrentLimit(int amps);
	void EnableCurrentLimit(boolean enable);
}
