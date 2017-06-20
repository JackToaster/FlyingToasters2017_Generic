package org.usfirst.frc.team3641.robot;

import com.ctre.CANTalon;

public interface AbstractTalon {
	void set(double power);

	void setPIDgains(double feedForward, double proportional, double integral, double derivative);
	
	int getNumBufferPoints();
	
	boolean hasUnderrun();
	
	void pushPoints(CANTalon.TrajectoryPoint[] points);
}
