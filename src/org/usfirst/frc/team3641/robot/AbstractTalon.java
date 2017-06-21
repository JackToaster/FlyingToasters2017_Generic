package org.usfirst.frc.team3641.robot;

import com.ctre.CANTalon;

public interface AbstractTalon {
	void set(double power);

	void setPIDgains(double feedForward, double proportional, double integral, double derivative);
	
	public int getEncPosition();
	
	public void setEncPosition(int position);
	
	public CANTalon.MotionProfileStatus getStatus();
	
	void pushPoints(MotionProfile.MPPoint[] points);
}
