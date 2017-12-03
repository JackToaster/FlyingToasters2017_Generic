package simulation;

import org.usfirst.frc.team3641.robot.Robot;

import simulation.PDPJNI;

public class SimCore {
	//skip this many frames between printing
	static final int skipPrintFrames = 20;
	//time (in seconds) of each step
	static final double stepTime = 0.01;
	
	//uncomment to run sim
	public static void main(String[] args){
		
		
		SimMotor simMotor = new SimMotor();
		IterativeRobot robot = new Robot();
		simMotor.addMotor(8, 5, 4.5,15,1);
		simMotor.addMotor(9, 5, 4.5,15,2);
		simMotor.addMotor(10,5, 4.5,15,3);
		simMotor.addMotor(11,5, 4.5,15,4);
		simMotor.addMotor(5, 5, 4.5,15,5);
		simMotor.addMotor(1, 5, 4.5,15,6);
		simMotor.addMotor(2, 5, 4.5,15,7);
		simMotor.addMotor(3, 5, 4.5,15,8);
		
		robot.robotInit();
		
		robot.teleopInit();
		
		int frameCount = 0;
		while(true){
			try{
				Thread.sleep((long) (stepTime * 1000));
			}catch(InterruptedException e){
				break;
			}
			robot.teleopPeriodic();
			simMotor.updateMotors(stepTime);
			frameCount++;
			if(frameCount > skipPrintFrames) {
				frameCount = 0;
				System.out.println(simMotor.motors);
				System.out.println("Total current: " + PDPJNI.getPDPTotalCurrent(16));
			}
		}
	}
}
