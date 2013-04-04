package init;

import java.util.ArrayList;
import java.util.List;

import rotator.DotCircle;

public class DefaultInitializer implements DotCircleInitializer {

	@Override
	public List<DotCircle> init(int width, int height) {
		List<DotCircle> rList = new ArrayList<DotCircle>();

		for(int i = 0; i < 6; i++){			
			rList.add(new DotCircle(i*40+20, i*10 + 10, width / 2, height / 2, 6));
		}
		return rList;
	}

	@Override
	public List<DotCircle> init(int width, int height, int numberOfCircle, int numberOfDots) {
		List<DotCircle> rList = new ArrayList<DotCircle>();

		for(int i = 0; i < numberOfCircle; i++){			
			rList.add(new DotCircle(i*40+20, i*10 + 10, width / 2, height / 2, numberOfDots));
		}
		return rList;
	}

	public String toString() {
		return DefaultInitializer.class.getSimpleName();
	}

}
