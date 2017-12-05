package hardware;

import simulation.CANTalon;

public class Talon implements MotorController {
	protected CANTalon talon;
	protected double currentPower;
	public Talon(int talonID) {
		talon = new CANTalon(talonID);
		currentPower = 0;
	}


	@Override
	public void setPower(double power) {
		currentPower = power;
		talon.set(currentPower);
	}

	@Override
	public double getPower() {
		return currentPower;
	}
	
	@Override
	public void setCurrentLimit(int amps){
		talon.setCurrentLimit(amps);
	}
	
	@Override
	public void EnableCurrentLimit(boolean enable){
		talon.EnableCurrentLimit(enable);
	}
}
