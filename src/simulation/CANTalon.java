package simulation;

public class CANTalon {
	private boolean enableCurrentLimit = false;
	private int currentLimit = 0;
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
	
	//returns the output current in amps
	public double getOutputCurrent(){
		double i = SimMotor.motors.get(id).getCurrent();
		if(enableCurrentLimit){
			if(i > currentLimit){
				return currentLimit;
			}else{
				return i;
			}
		}else{
			return i;
		}
		
	}
	
	public void setVoltageRampRate(double rampRate){}
	
	public void setCurrentLimit(int amps){
		currentLimit = amps;
	}
	
	public void EnableCurrentLimit(boolean enable){
		enableCurrentLimit = enable;
	}
	
	private void setMotorInfo(SimMotor.MotorInfo mi){
		SimMotor.motors.put(id, mi);
	}
}
