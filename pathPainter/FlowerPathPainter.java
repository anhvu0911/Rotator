package pathPainter;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.util.List;

import rotator.DotCircle;

public class FlowerPathPainter implements PathPainter {

	@Override
	public void paint(Graphics2D g, List<DotCircle> rList) {

		// A circle at the center
		int centerX = rList.get(0).offset_x;
		int centerY = rList.get(0).offset_y;
		g.fillOval(centerX - 5, centerY - 5, 10, 10);
		
		for (int i = 0; i < rList.size(); i++) {
			g.setStroke(new BasicStroke(i + 0.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			DotCircle r = rList.get(i);

			for (int j = 0; j < r.positionX.length; j++) {
				g.drawLine(r.positionX[j], r.positionY[j], centerX, centerY);
			}
		}
	}
	
	public String toString(){
		return FlowerPathPainter.class.getSimpleName();
	}

}
