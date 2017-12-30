package hardware;

import java.util.ArrayList;
/**
 * Drivebase class for all of your driving needs
 * 
 * @author jackf
 *
 */

public abstract class DriveBase{
	protected boolean isActive = true;
	//these two should be mutually exclusive.
	private ArrayList<MotorController> controllers;
	private ArrayList<FeedbackMotorController> feedbackControllers;
	
	public DriveBase(){
		controllers = new ArrayList<MotorController>();
		feedbackControllers = new ArrayList<FeedbackMotorController>();
	}
	//override this with whatever stuff
	abstract public void drive(double... inputs);
	//add a motor controller to the list
	public void registerMotorController(MotorController mc){
		if(mc instanceof FeedbackMotorController){
			FeedbackMotorController fmc = (FeedbackMotorController)mc;
			feedbackControllers.add(fmc);
		}else{
			controllers.add(mc);
		}
	}
	
	//udpate should be called periodically.
	public void update(double dT){
		if (!isActive) {
			for (MotorController c : controllers) {
				c.setPower(0);
			}
			for(FeedbackMotorController c: feedbackControllers){
				c.setFeedbackActive(false);
				c.setPower(0);
			}
		}else{
			for(FeedbackMotorController c: feedbackControllers){
				if(c.getFeedbackActive()) {
					c.runFeedback(dT);
				}else {
					c.setPower(0);
				}
			}
		}
	};
	public void setActive(boolean active){
		//set isActive to false
		isActive = active;
		//update with dT 0 to set everything IMMEDIATELY to zero.
		update(0);
	};
	public boolean getActive(){ return isActive;}
}
