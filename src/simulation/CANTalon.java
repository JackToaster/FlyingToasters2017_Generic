package simulation;

public class CANTalon {
	public enum FeedbackDevice{
		AnalogEncoder,
		AnalogPot ,
		CtreMagEncoder_Absolute ,
		CtreMagEncoder_Relative ,
		EncFalling ,
		EncRising ,
		PulseWidth ,
		QuadEncoder 
	}
	private int id;
	
	public CANTalon(int motorID) {
		id = motorID;
	}
	
	public int getAnalogInPosition(){
		return getMotorInfo().getPos();
	}
	
	public int getDeviceID(){
		return id;
	}
	
	public int getEncPosition(){
		return getMotorInfo().getPos();
	}
	
	public void set(double outputValue){
		SimMotor.MotorInfo current = getMotorInfo();
		current.setThrottle(outputValue);
		setMotorInfo(current);
	}
	public void setFeedbackDevice(FeedbackDevice device){}
	private SimMotor.MotorInfo getMotorInfo(){
		return SimMotor.motors.get(id);
	}
	
	private void setMotorInfo(SimMotor.MotorInfo mi){
		SimMotor.motors.replace(id, mi);
	}
}
