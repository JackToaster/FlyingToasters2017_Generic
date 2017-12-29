package motion_profiles;

public class Profile {
		private MPPoint[] trajectory;
		
		public Profile(int length){
			trajectory = new MPPoint[length];
		}
		
		public void setPoints(MPPoint... points){
			if(points.length < 2){
				Logging.e("Useless motion profile - less than 2 points");
			}else{
				trajectory = points;
			}
		}
		
		public void setPoint(int index, MPPoint point){
			trajectory[index] = point;
		}
		public MPPoint getPoint(int index){
			return trajectory[index];
		}
		
		public MPPoint getInterpolatedPoint(double time){
			int upperIndex = 0;
			while(getPoint(upperIndex).time < time){
				upperIndex++;
			}
			if(upperIndex > 0){
				int lowerIndex = upperIndex - 1;
				
				MPPoint upper = getPoint(upperIndex);
				MPPoint lower = getPoint(lowerIndex);
				
				//find what fraction of the way from upper to lower the time is
				double alpha = (time - lower.time) / (upper.time - lower.time); 
				
				return lower.lerp(upper, alpha);
			}else{
				return getPoint(0);
			}
		}
		
		// returns the first point
		public MPPoint start() {
			return getPoint(0);
		}

		// returns the last point
		public MPPoint end() {
			return getPoint(trajectory.length - 1);
		}

		// returns the time of the last point
		public double getEndTime() {
			return end().time;
		}
	}