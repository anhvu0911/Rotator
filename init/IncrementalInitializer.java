package init;

import java.util.ArrayList;
import java.util.List;

import rotator.DotCircle;

public class IncrementalInitializer implements DotCircleInitializer {

	@Override
	public List<DotCircle> init(int width, int height) {
		List<DotCircle> rList = new ArrayList<DotCircle>(); 
		
		for(int i = 0; i < 5; i++){
			rList.add(new DotCircle((int)(Math.random() * 310), (int)(Math.random() * 360), width / 2, height / 2, i+3));
		}
		return rList;
	}

	@Override
	public List<DotCircle> init(int width, int height, int numberOfCircle, int numberOfDots) {
		List<DotCircle> rList = new ArrayList<DotCircle>();
		
		for(int i = 0; i < numberOfCircle; i++){
			rList.add(new DotCircle((int)(Math.random() * 310), (int)(Math.random() * 360), width / 2, height / 2, i+3));
		}
		return rList;
	}

	public String toString() {
		return IncrementalInitializer.class.getSimpleName();
	}

}
