package org.usfirst.frc.team3641.robot;

import com.ctre.CANTalon;

public class Talon implements AbstractTalon{
	private CANTalon talon;
	
	public Talon(int talonID){
		talon  = new CANTalon(talonID);
	}
	
	public void set(double power){
		talon.set(power);
	}
}
