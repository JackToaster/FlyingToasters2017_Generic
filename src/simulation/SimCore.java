package simulation;

import org.usfirst.frc.team3641.robot.Robot;

public class SimCore {
	//uncomment to run sim
	public static void main(String[] args){
		SimMotor simMotor = new SimMotor();
		IterativeRobot robot = new Robot();
		simMotor.addMotor(8, 5, 4.5);
		simMotor.addMotor(9, 5, 4.5);
		simMotor.addMotor(10, 5, 4.5);
		simMotor.addMotor(11, 5, 4.5);
		simMotor.addMotor(5, 5, 4.5);
		simMotor.addMotor(1, 5, 4.5);
		simMotor.addMotor(2, 5, 4.5);
		simMotor.addMotor(3, 5, 4.5);
		
		robot.robotInit();
		
		//robot.teleopInit();
		while(true){
			try{
				Thread.sleep(1);
			}catch(InterruptedException e){
				break;
			}
			robot.teleopPeriodic();
			simMotor.updateMotors(0.001);
		}
	}
}
