package controllers.motion_profiles;
import pathfinder.*;
public abstract class WheelProfileGenerator {
	public abstract Profile genPoints(Path p, double offset);
}

