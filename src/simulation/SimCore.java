package simulation;

import org.usfirst.frc.team3641.robot.Robot;

import simulation.PDPJNI;
import simulation.SimMotor;
import pathfinder.*;
public class SimCore {
	//skip this many frames between printing
	static final int skipPrintFrames = 20;
	//time (in seconds) of each step
	static final double stepTime = 0.01;
	
	//uncomment to run sim
	public static void main(String[] args){
		Waypoint start = new Waypoint(new Point(1,1), Math.PI / 2.0);
		Waypoint end = new Waypoint(new Point(5,1), Math.PI / 2.0);
		
		Path p = new Path(start, end, 10, 0.5);
		
		System.out.println(p);
		/*
		SimMotor simMotor = new SimMotor();
		IterativeRobot robot = new Robot();
		simMotor.addMotor(8, 5, 4.5,1);
		simMotor.addMotor(9, 5, 4.5,2);
		simMotor.addMotor(10,5, 4.5,3);
		simMotor.addMotor(11,5, 4.5,4);
		simMotor.addMotor(5, 5, 4.5,5);
		simMotor.addMotor(1, 5, 4.5,6);
		simMotor.addMotor(2, 5, 4.5,7);
		simMotor.addMotor(3, 5, 4.5,8);
		
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
		*/
	}
}
