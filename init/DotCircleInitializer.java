package init;

import java.io.Serializable;
import java.util.List;

import rotator.DotCircle;

public interface DotCircleInitializer extends Serializable{
	List<DotCircle> init(int width, int height);
	List<DotCircle> init(int width, int height, int numberOfCircle, int numberOfDots);
}
