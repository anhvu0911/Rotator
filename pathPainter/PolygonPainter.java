package pathPainter;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.util.List;

import rotator.DotCircle;

public class PolygonPainter implements PathPainter {

	@Override
	public void paint(Graphics2D g, List<DotCircle> rList) {
		
		// Somehow cannot apply setStroke to drawPolygon
		// g.drawPolygon(r1.positionX, r1.positionY, r1.positionX.length);
		for (int j = 0; j < rList.size(); j++) {
			g.setStroke(new BasicStroke(j * 2 + 1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			DotCircle r = rList.get(j);

			for (int i = 0; i < r.angles.length; i++) {
				if (i + 1 < r.angles.length) {
					g.drawLine(r.positionX[i], r.positionY[i], r.positionX[i + 1], r.positionY[i + 1]);
				} else {
					g.drawLine(r.positionX[0], r.positionY[0], r.positionX[r.angles.length - 1], r.positionY[r.angles.length - 1]);
				}
			}
		}
	}

	public String toString() {
		return PolygonPainter.class.getSimpleName();
	}

}