package controllers;

import java.util.ArrayList;

import hardware.DriveBase;
import utilities.Constants;
import utilities.Utilities;

/**
 * class for generation and excecution of motion profiles.
 * @author jackf
 *
 */
public class MotionProfile implements Constants.Talons{
	public ArrayList<Utilities.robotPosition> path;
	public ArrayList<MPPoint> leftTraj;
	public ArrayList<MPPoint> rightTraj;
	
	public int stepTime = 10;
	
	public DriveBase driveBase;
	
	/**
	 * Motion profile point class, has a position, velocity, and time (in ms)
	 * 
	 * @author jackf
	 *
	 */
	public class MPPoint {
		public double position;
		public double velocity;
		public int time = stepTime;
		
		public boolean isStart = false, isEnd = false;

		public MPPoint(double vel, double pos, int t) {
			velocity = vel;
			position = pos;
			time = t;
		}
		
		public MPPoint(double vel, double pos, int t, boolean start, boolean end){
			this(vel,pos,t);
			isStart = start;
			isEnd = end;
		}
		
		public void setEnd(){
			isEnd = true;
		}
		
		public void setStart(){
			isStart = true;
		}
	}
	
//	/**
//	 * push the next set of point to the motion profile buffer in the talon.
//	 * Should be called whenever the buffer gets too low.
//	 * 
//	 * @param startIndex
//	 *            the profile point index to start with
//	 *            
//	 */
//	public void pushPoints(int startIndex, int number) {
//		//array of points for left and right wheels
//		MPPoint[] leftPoints = new MPPoint[number];
//		MPPoint[] rightPoints = new MPPoint[number];
//		
//		//add points to array
//		for(int i = startIndex; i < startIndex + number; i++){
//			leftPoints[i] = leftTraj.get(i);
//			rightPoints[i] = rightTraj.get(i);
//		}
//		
//		
//		//push the points to the buffers
//		DriveBase.talons.get(LEFT_ENCODER_TALON).pushPoints(leftPoints);
// 	}
}
