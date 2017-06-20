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
		double robotX, robotY;
		double robotAngle;
		
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
	}
}
