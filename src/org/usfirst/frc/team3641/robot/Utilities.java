package org.usfirst.frc.team3641.robot;

public class Utilities {
	/**
	 * Conversion interface to convert various units.
	 * 
	 * @author jackf
	 *
	 */
	public interface Conversions extends Constants.Measurments {
		enum Distance{
			KM (1000.0),
			M (1.0),
			CM (0.01),
			FT (0.3048),
			IN (0.0254),
			ROBOT_LENGTH (IN.length * 36),
			ROBOT_WIDTH (IN.length * 32),
			WHEEL_ROTATION (DRIVE_WHEEL_CIRCUMFERENCE),
			ENCODER_TICK (WHEEL_ROTATION.length / 1024);
			
			private final double length;
			Distance(double len){
				this.length = len;
			}

			/**
			 * converts the unit to another distance unit.
			 * 
			 * @param a2
			 *            the unit to convert to
			 * @return the converted measurement
			 */
			public double convert(Distance d2) {
				return d2.length / this.length;
			}
		}
		
		enum Velocity{
			M_S (1.0),
			CM_S (100),
			FT_S (1 / 0.3048),
			IN_S (1 / 0.0254),
			WHEEL_RPS (1 / DRIVE_WHEEL_CIRCUMFERENCE),
			ENCODER_TPS (WHEEL_RPS.velocity * DRIVE_ENCODER_TICKS_PER_TURN),
			
			M_MIN (M_S.velocity * 60),
			CM_MIN (CM_S.velocity * 60),
			FT_MIN (FT_S.velocity * 60),
			IN_MIN (IN_S.velocity * 60),
			WHEEL_RPM (WHEEL_RPS.velocity * 60),
			ENCODER_TPM (ENCODER_TPS.velocity * 60),
			MPH (2.23694),
			BEARD_VELOCITY (5e-9); //speed at which a beard grows, in m/s
			
			
			private final double velocity;

			Velocity(double vel) {
				this.velocity = vel;
			}

			/**
			 * converts the unit to another velocity unit.
			 * 
			 * @param v2
			 *            the unit to convert to
			 * @return conversion multiplier
			 */
			public double convert(Velocity v2) {
				return v2.velocity / this.velocity;
			}
		}
		
		enum Angle{
			ROTATION (1),
			DEGREE (1/360),
			RADIAN (1/6.28318530718);

			private final double angle;

			Angle(double a) {
				this.angle = a;
			}

			/**
			 * converts the unit to another angle unit.
			 * 
			 * @param a2
			 *            the unit to convert to
			 * @return the converted measurement
			 */
			public double convert(Angle a2) {
				return a2.angle / this.angle;
			}
		}
	}
	
	/**
	 * class to store position and rotation and get bounding points of the
	 * robot's frame.
	 * 
	 * @author jackf
	 *
	 */
	public static class robotPosition implements Constants.Measurments {
		
		//robot's coordinates and angle
		Point robotPosition;
		double robotAngle;
		
		/**
		 * create a robotPosition with the given position and angle
		 * @param x the x position of the robot
		 * @param y the y position of the robot
		 * @param theta the angle of the robot
		 */
		public robotPosition(double x, double y, double theta){
			robotPosition = new Point(x,y);
			robotAngle = theta;
		}
		
		/**
		 * create a robotPosition with the given position and angle
		 * @param position the position of the robot
		 * @param theta the angle of the robot
		 */
		public robotPosition(Point position, double theta){
			robotPosition = position;
			robotAngle = theta;
		}
		
		/**
		 * create a robotPosition at (0,0) with angle 0.
		 */
		public robotPosition(){
			this(0,0,0);
		}
		
		/**
		 * move the robot based on a distance and rotation.
		 * @param distance the distance the robot moved
		 * @param angle the angle the robot is facing after moving
		 */
		public void integratePosition(double distance, double angle){
			Point offset = new Point().polarPoint(distance, angle);
			robotPosition = robotPosition.add(offset);
		}
		
		/**
		 * position of field origin
		 * @author jackf
		 *
		 */
		public enum FieldOrigin {
			CENTER (FIELD_WIDTH/2, FIELD_LENGTH/2),
			CORNER (0,0);
			
			double originX, originY;
			
			FieldOrigin(double x, double y){
				originX = x;
				originY = y;
			}
		}
		public class Point {
			// X and Y coordinates of the point
			double x, y;

			/**
			 * create a point with the given X and Y coordinates
			 * 
			 * @param x
			 * @param y
			 */
			public Point(double x, double y) {
				this.x = x;
				this.y = y;
			}

			/**
			 * create a point at (0,0)
			 */
			public Point() {
				this(0, 0);
			}

			/**
			 * create a point at the same coordinates of another point
			 * 
			 * @param p
			 *            the point to set this point equal to
			 */
			public Point(Point p) {
				x = p.x;
				y = p.y;
			}

			/**
			 * create a point
			 * 
			 * @param r
			 *            the distance from the origin
			 * @param theta
			 *            the angle in radians from the positive X-axis, going
			 *            counter-clockwise.
			 * @return the point
			 */
			public Point polarPoint(double r, double theta) {
				x = r * Math.cos(theta);
				y = r * Math.sin(theta);
				return new Point(x, y);
			}

			/**
			 * add two points together
			 * 
			 * @param p2
			 *            the point to add to this one
			 * @return the sum of the two points
			 */
			public Point add(Point p2) {
				return new Point(x + p2.x, y + p2.y);
			}

			/**
			 * return the squared distance between two points. Much faster than
			 * distance() because it doesn't require square root.
			 * 
			 * @param p2
			 *            the point to find the distance to
			 * @return the square of the distance
			 */
			public double squareDistance(Point p2) {
				// find the distance in the X and Y directions
				double xDist = x - p2.x;
				double yDist = y - p2.y;
				// return the sum of the squares of the X and Y distance
				return xDist * xDist + yDist * yDist;
			}

			/**
			 * calculates the distance to p2. Slower than squareDistance because it
			 * requires Math.sqrt().
			 * 
			 * @param p2
			 *            the other point
			 * @return the distance to p2
			 */
			public double distance(Point p2) {
				// return the square root of the square distance
				return Math.sqrt(squareDistance(p2));
			}
		}
	}
}
