package controllers;
import pathfinder.*;
public abstract class WheelProfileGenerator {
	public abstract MotionProfile.Profile genPoints(Path p);
}

