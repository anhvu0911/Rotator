package pathPainter;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.CubicCurve2D;
import java.util.List;

import rotator.DotCircle;

public class CubicReverseFlowerPetalPathPainter implements PathPainter {
	private int red = (int) (Math.random()*255);
	private int green = (int) (Math.random()*255);
	private int blue = (int) (Math.random()*255);
	private int alpha = (int) (Math.random()*50+100);

	@Override
	public void paint(Graphics2D g, List<DotCircle> rList) {
//		g.setColor(new Color(red, green, blue, alpha));
		DotCircle r1, r2;
		for (int i = 0; i < rList.size() - 1; i++) {
			r1 = rList.get(i);
			r2 = rList.get(i + 1);
			
			g.setStroke(new BasicStroke(i*1.4f + 0.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));			
			for (int j = 0; j < r1.angles.length; j++) {
				
				CubicCurve2D curve = new CubicCurve2D.Double();
				
				// Reverse-flower petal
				if(j+1 < r2.angles.length){
					curve.setCurve(r2.positionX[j], r2.positionY[j], r1.positionX[j], r1.positionY[j], r1.positionX[j], r1.positionY[j], r2.positionX[j+1], r2.positionY[j+1]);
				}else{
					curve.setCurve(r2.positionX[j], r2.positionY[j],r1.positionX[j], r1.positionY[j],  r1.positionX[j], r1.positionY[j], r2.positionX[0], r2.positionY[0]);
				}


				g.draw(curve);
//				g.fill(curve);
			}
		}
	}
	
	public String toString(){
		return CubicReverseFlowerPetalPathPainter.class.getSimpleName();
	}
	

}
