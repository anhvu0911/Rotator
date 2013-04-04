package pathPainter;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.util.List;

import rotator.DotCircle;

public class BinaryPathPainter implements PathPainter {

	@Override
	public void paint(Graphics2D g, List<DotCircle> rList) {
		DotCircle r1, r2;
		for (int i = 0; i < rList.size() - 1; i++) {
			r1 = rList.get(i);
			r2 = rList.get(i + 1);

			g.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			for (int j = 0; j < r1.angles.length; j++) {
				g.drawLine(r1.positionX[j], r1.positionY[j], r2.positionX[j], r2.positionY[j]);
				
				if(j-1 >= 0){
					g.drawLine(r1.positionX[j], r1.positionY[j], r2.positionX[j-1], r2.positionY[j-1]);
				}else{
					g.drawLine(r1.positionX[j], r1.positionY[j], r2.positionX[r1.angles.length-1], r2.positionY[r1.angles.length-1]);
				}
			}
		}
	}

	public String toString(){
		return BinaryPathPainter.class.getSimpleName();
	}
}
