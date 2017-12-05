package hardware;

import simulation.CANTalon;

//TODO use Talon[] instead of CANTalon[] to allow for current limiting talons

public class LinkedTalons implements MotorController {
	protected int numberOfTalons;
	protected CANTalon[] talons;
	protected double currentPower = 0;
	
	/**
	 * Creates a new set of linked talons.
	 * 
	 * @param talonIDs
	 *            Each of the IDs you want to control.
	 */
	public LinkedTalons(int... talonIDs) {
		
		numberOfTalons = talonIDs.length;
		talons = new CANTalon[numberOfTalons];

		for (int i = 0; i < numberOfTalons; i++)
			talons[i] = new CANTalon(talonIDs[i]);
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
	}

	@Override
	public double getPower() {
		return currentPower;
	}
	
	public void setCurrentLimit(int amps){
		for(CANTalon ct : talons){
			ct.setCurrentLimit(amps);
		}
	}
	
	public void EnableCurrentLimit(boolean enable){
		for(CANTalon ct : talons){
			ct.EnableCurrentLimit(enable);
		}
	}
}
