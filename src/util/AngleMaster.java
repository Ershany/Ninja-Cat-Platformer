package util;

public class AngleMaster {

	public static double calculateAngle(float xOrig, float yOrig, float xDest, float yDest) {
		return Math.atan2(yDest - yOrig, xDest - xOrig);
	}
	
}
