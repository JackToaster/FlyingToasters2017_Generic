package org.usfirst.frc.team3641.robot;


import simulation.IterativeRobot;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import hardware.DriveBase;
import hardware.Pro775DriveBase;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	//SendableChooser<String> chooser = new SendableChooser<>();

	Pro775DriveBase driveBase;
	
	//PS4 ps4 = new PS4(0);
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		//chooser.addDefault("Default Auto", defaultAuto);
		//chooser.addObject("My Auto", customAuto);
		//SmartDashboard.putData("Auto choices", chooser);
		driveBase = new Pro775DriveBase();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		//autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
	}

	
	@Override
	public void autonomousPeriodic() {
		standardPeriodic();
		switch (autoSelected) {
		default:
			// Put default auto code here
			break;
		}
	}
	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		standardPeriodic();
		switch (autoSelected) {
		default:
			// Put default auto code here
			break;
		}
	}

	
	@Override
	public void teleopInit() {
		//driveBase.drive(ps4.getAxis(PS4.Axis.LEFT_Y), ps4.getAxis(PS4.Axis.RIGHT_Y));
		driveBase.feedbackTestinit();
	}
	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		standardPeriodic();
		//driveBase.drive(ps4.getAxis(PS4.Axis.LEFT_Y), ps4.getAxis(PS4.Axis.RIGHT_Y));
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}

	/**
	 * This method is always called periodically
	 */
	public void standardPeriodic() {
		driveBase.update(0.05);
	}
}
