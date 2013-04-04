package pathPainter;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.util.List;

import rotator.DotCircle;

public class DefaultPathPainter implements PathPainter {

	@Override
	public void paint(Graphics2D g, List<DotCircle> rList) {
		DotCircle r1, r2;
		for (int i = 0; i < rList.size() - 1; i++) {
			r1 = rList.get(i);
			r2 = rList.get(i + 1);
			
			g.setStroke(new BasicStroke(i*1.4f + 0.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

			for (int j = 0; j < r1.angles.length; j++) {
				g.drawLine(r1.positionX[j], r1.positionY[j], r2.positionX[j], r2.positionY[j]);
			}
		}
	}
	
	public String toString(){
		return DefaultPathPainter.class.getSimpleName();
	}
}
