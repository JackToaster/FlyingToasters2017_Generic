package simulation;
import java.util.HashMap;
import utilities.Logging;

public class PDPJNI {
	
	final static int PDPModule = 16;

	private static HashMap<Byte,Integer> motorMapping = new HashMap<Byte, Integer>();
	
	public PDPJNI(){
		
	}
	
	public static void addMotor(byte channel, int motorIndex){
		motorMapping.put((Byte) channel, (Integer) motorIndex);
	}
	public static double getPDPChannelCurrent(byte channel, int module) {
		if(module == PDPModule){
			int motorNum = (int) motorMapping.get((Byte) channel);
			return SimMotor.motors.get(motorNum).getCurrent();
		}else{
			Logging.w("Wrong module number for PDP, correct is " + PDPModule);
			return 0;
		}
	}
	
	public static double getPDPTotalCurrent(int module){
		if(module == PDPModule){
			double total = 0;
			for(Integer i : motorMapping.values()){
				total += SimMotor.motors.get((int) i).getCurrent();
			}
			return total;
		}else{
			Logging.w("Wrong module number for PDP, correct is " + PDPModule);
			return 0;
		}
	}
	
	@Override
	public String toString(){
		return motorMapping.toString();
	}
}
