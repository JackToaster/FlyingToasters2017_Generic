package hardware;

public interface MotorController {
	void setPower(double power);
	double getPower();
	
	void setCurrentLimit(int amps);
  void EnableCurrentLimit(boolean enable);
}
