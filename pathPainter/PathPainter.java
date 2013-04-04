package pathPainter;

import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.List;

import rotator.DotCircle;

public interface PathPainter extends Serializable {
	void paint(Graphics2D g, List<DotCircle> rList);
}
