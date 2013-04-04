package init;

import java.util.ArrayList;
import java.util.List;

import rotator.DotCircle;

public class PipeInitializer implements DotCircleInitializer {

	@Override
	public List<DotCircle> init(int width, int height) {
		List<DotCircle> rList = new ArrayList<DotCircle>();
		
		int numberOfCircle = 10;
		for(int i = 0; i < numberOfCircle; i++){			
			rList.add(new DotCircle(i*40+20, i*10 + 10, 
					width / numberOfCircle*(i+1), 
					height / numberOfCircle*(i+1), 
					numberOfCircle));	
		}
		return rList;
	}

	@Override
	public List<DotCircle> init(int width, int height, int numberOfCircle, int numberOfDots) {
		List<DotCircle> rList = new ArrayList<DotCircle>();
		
		for(int i = 0; i < numberOfCircle; i++){			
			rList.add(new DotCircle(i*40+20, i*10 + 10, 
					width / numberOfCircle*(i+1), 
					height / numberOfCircle*(i+1), 
					numberOfDots));	
		}
		return rList;
	}

	public String toString() {
		return PipeInitializer.class.getSimpleName();
	}

}
