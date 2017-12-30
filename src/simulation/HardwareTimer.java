package simulation;

public class HardwareTimer {
	static double timeStamp = 1234;
	public HardwareTimer() {}
	public static void incrementTimer(double amount) {
		timeStamp += amount;
	}
	public double getFPGATimestamp() {
		return timeStamp;
	}
}