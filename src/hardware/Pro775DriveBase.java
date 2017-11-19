package hardware;

import utilities.Logging;

public class Pro775DriveBase extends DriveBase{
	private LinkedTalons left;
	private LinkedTalons right;
	
	public enum Talon {
		LEFT0(8),
		LEFT1(9),
		LEFT2(10),
		LEFT3(11),
		RIGHT0(5),
		RIGHT1(1),
		RIGHT2(2),
		RIGHT3(3);
		public int id;
		Talon(int talonID){
			id = talonID;
		}
	}
	
	public Pro775DriveBase() {
		//create the linked talons for each side of the drive base
		left = new LinkedTalons(Talon.LEFT0.id, Talon.LEFT1.id, Talon.LEFT2.id, Talon.LEFT3.id);
		right = new LinkedTalons(Talon.RIGHT0.id, Talon.RIGHT1.id, Talon.RIGHT2.id, Talon.RIGHT3.id);
		//add the motor controllers to the list to be updated
		registerMotorController(left);
		registerMotorController(right);
	}

	@Override
	public void drive(double... inputs) {
		if(inputs.length != 2){
			Logging.e("Incorrect number of inputs to drive(double... inputs): " + inputs.length);
		}else{
			//tank drive
			left.setPower(inputs[0]);
			right.setPower(inputs[1]);
		}
	}

}
