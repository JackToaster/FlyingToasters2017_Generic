package pathfinder;

public class Waypoint {
	public Point position;
	public double rotation;
	public double velocity;
	public double time;
	public Waypoint(Point waypointPosition, double wpRotation) {
		position = waypointPosition;
		rotation = wpRotation;
		time = 0;
	}

	public void setPosition(double xPosition, double yPosition) {
		position.x = xPosition;
		position.y = yPosition;
	}

	public void setRotation(double wpRotation) {
		rotation = wpRotation;
	}

	public void translateWaypoint(double xOffset, double yOffset) {
		position.add(new Point(xOffset, yOffset));
	}

	public double pointTowards(Point target) {
		double xOffset = target.x - position.x;
		double yOffset = target.y - position.y;
		if (xOffset == 0) {
			if (yOffset > 0) {
				rotation = Math.PI / 2.0;
			} else {
				rotation = 3.0 * Math.PI / 2.0;
			}
		} else {
			rotation = Math.atan(yOffset / xOffset);
			if (yOffset < 0) {
				rotation += 2.0 * Math.PI;
			}
		}
		return rotation;
	}
	
	public Point getPoint(){
		return position;
	}
	
	@Override
	public String toString(){
		return "X: " + position.x + ", Y: " + position.y + ", Angle: " + rotation + ", velocity: " + velocity + ", time: " + time;
	}
}
