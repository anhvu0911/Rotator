package rotator;

import java.io.Serializable;

/**
 * The circle with 3 dots, each at relative angle
 * 
 * @author vunguyen
 * 
 */
public class DotCircle implements Serializable{

	public int r = 0;
	public int numberOfDots = 3;
	public double[] angles;
	public int[] positionX;
	public int[] positionY;

	public int offset_x = 0;
	public int offset_y = 0;

	public DotCircle(int r, int initialAngle, int offset_x, int offset_y, int numberOfDots) {
		this.numberOfDots = numberOfDots;
		this.angles = new double[numberOfDots];
		this.positionX = new int[numberOfDots];
		this.positionY = new int[numberOfDots];
		this.r = r;
		this.offset_x = offset_x;
		this.offset_y = offset_y;
		
		int angle = (int) (360 / numberOfDots);
		for (int i = 0; i < numberOfDots; i++) {
			angles[i] = angle * i + initialAngle;
		}
		calculateXY();
	}

	public void calculateXY() {
		for (int i = 0; i < numberOfDots; i++) {
			positionX[i] = offset_x + (int) (r * Math.cos(Math.toRadians(angles[i])));
			positionY[i] = offset_y + (int) (r * Math.sin(Math.toRadians(angles[i])));
		}
	}

	public void updateAngle(double angle) {
		for (int i = 0; i < numberOfDots; i++) {
			angles[i] += angle;
			if (angles[i] > 360){
				angles[i] -= 360;
			}
		}
		calculateXY();
	}
}

