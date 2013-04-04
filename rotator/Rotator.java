package rotator;

import init.DefaultInitializer;
import init.DotCircleInitializer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import pathPainter.DefaultPathPainter;
import pathPainter.PathPainter;
import pathPainter.PolygonPainter;

public class Rotator extends JPanel implements Runnable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 690;
	public static final int HEIGHT = 690;
	
	private int numberOfCircle = 6;
	private int numberOfDots = numberOfCircle;
	
	private int length = 10;
	private DotCircleInitializer initializer = new DefaultInitializer();
	private List<DotCircle> rotatorList = initializer.init(WIDTH, HEIGHT);
	private PathPainter pathPainter = new DefaultPathPainter();
	private int direction = 1;
	private int alternateDirection = 1;

	private boolean drawDots = false;
	private boolean drawCircle = true;
	
	private boolean pause = false;

	//===============================================================================
	// CONSTRUCTOR
	//===============================================================================
	public Rotator() {
		this.setSize(WIDTH, HEIGHT);
		this.setFocusable(true);
		this.setDoubleBuffered(true);
		
		loadRotatorList();
		
		this.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
//				init = new IncrementalInitializer();
//				pp = new PolygonPainter();	
		    	
//				initializer = new RandomInitializer();
//				pathPainter = new CubicPathPainter();
				
				rotatorList = initializer.init(WIDTH, HEIGHT, numberOfCircle, numberOfDots);	
		    }
		});
		
		(new Thread(this)).start();
	}

	//===============================================================================
	// GETTERS + SETTERS
	//===============================================================================
	
	public void setPause(boolean pause) {
		this.pause = pause;
	}

	public int getNumberOfCircle() {
		return numberOfCircle;
	}

	public void setNumberOfCircle(int numberOfCircle) {
		this.numberOfCircle = numberOfCircle;
		rotatorList = initializer.init(WIDTH, HEIGHT, numberOfCircle, numberOfDots);
	}

	public int getNumberOfDots() {
		return numberOfDots;
	}

	public void setNumberOfDots(int numberOfDots) {
		this.numberOfDots = numberOfDots;
		rotatorList = initializer.init(WIDTH, HEIGHT, numberOfCircle, numberOfDots);
	}
	
	public DotCircleInitializer getInitializer() {
		return initializer;
	}

	public void setInitializer(DotCircleInitializer initializer) {
		this.initializer = initializer;
		rotatorList = initializer.init(WIDTH, HEIGHT);
		numberOfCircle = rotatorList.size();
		numberOfDots = rotatorList.get(0).numberOfDots;
	}

	public PathPainter getPathPainter() {
		return pathPainter;
	}

	public void setPathPainter(PathPainter pathPainter) {
		this.pathPainter = pathPainter;
	}

	public int getAlternateDirection() {
		return alternateDirection;
	}

	public void enableAlternateDirection() {
		alternateDirection = -alternateDirection;
	}
	
	public int getDirection() {
		return direction;
	}

	public void reverseDirection() {
		direction = -direction;
	}

	public boolean isDrawDots() {
		return drawDots;
	}

	public void setDrawDots(boolean drawDots) {
		this.drawDots = drawDots;
	}

	public boolean isDrawCircle() {
		return drawCircle;
	}

	public void setDrawCircle(boolean drawCircle) {
		this.drawCircle = drawCircle;
	}

	public List<DotCircle> getRotatorList() {
		return rotatorList;
	}

	//===============================================================================
	// METHODS
	//===============================================================================

	/**
	 * update the dot Circle angle, position
	 */
	public void update() {
		double a = -1;
		int b = direction;
		for (DotCircle r : rotatorList) {
			r.updateAngle(a*b);
			a -= 0.5;
			b *= alternateDirection;
		}
	}
	
	/**
	 * reverse of Update, back 1 step, use for NEXT-PREV-PAUSE button
	 */
	public void reverse() {
		double a = 1;
		int b = direction;
		for (DotCircle r : rotatorList) {
			r.updateAngle(a*b);
			a += 0.5;
			b *= alternateDirection;
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Image img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB); 
		Graphics2D g2d = (Graphics2D)img.getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.BLACK);
		
		if(drawCircle || drawDots){
			DotCircle r1, r2;
			for (int i = 0; i < rotatorList.size() - 1; i++) {
				r1 = rotatorList.get(i);
				r2 = rotatorList.get(i + 1);
				
				if(drawCircle){
					if(pathPainter instanceof PolygonPainter){
						g2d.setStroke(new BasicStroke(i*1.4f + 0.5f));
					}else{
						g2d.setStroke(new BasicStroke(1));
					}
					g2d.drawOval(r1.offset_x-r1.r, r1.offset_y-r1.r, 2*r1.r,2*r1.r);
					g2d.drawOval(r2.offset_x-r2.r, r2.offset_y-r2.r, 2*r2.r,2*r2.r);
				}
				
				if(drawDots){
					g2d.setStroke(new BasicStroke(i*1.4f + 0.5f));
	
					for (int j = 0; j < r1.angles.length; j++) {
						g2d.fillOval(r1.positionX[j] - length / 2, r1.positionY[j] - length / 2, length, length);
						g2d.fillOval(r2.positionX[j] - length / 2, r2.positionY[j] - length / 2, length, length);
					}
				}
			}
		}
		
		pathPainter.paint((Graphics2D) g2d, rotatorList);
		
		g.drawImage(img,0,0,WIDTH,HEIGHT,this);
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(40);
				if (!pause) {
					update();
					this.repaint();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Save to a png image
	 */
	public void saveToImage(){
		BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) img.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.BLACK);
		
		if(drawCircle || drawDots){
			DotCircle r1, r2;
			for (int i = 0; i < rotatorList.size() - 1; i++) {
				r1 = rotatorList.get(i);
				r2 = rotatorList.get(i + 1);
				
				if(drawCircle){
					if(pathPainter instanceof PolygonPainter){
						g.setStroke(new BasicStroke(i*1.4f + 0.5f));
					}else{
						g.setStroke(new BasicStroke(1));
					}
					g.drawOval(r1.offset_x-r1.r, r1.offset_y-r1.r, 2*r1.r,2*r1.r);
					g.drawOval(r2.offset_x-r2.r, r2.offset_y-r2.r, 2*r2.r,2*r2.r);
				}
				
				if(drawDots){
					g.setStroke(new BasicStroke(i*1.4f + 0.5f));
	
					for (int j = 0; j < r1.angles.length; j++) {
						g.fillOval(r1.positionX[j] - length / 2, r1.positionY[j] - length / 2, length, length);
						g.fillOval(r2.positionX[j] - length / 2, r2.positionY[j] - length / 2, length, length);
					}
				}
			}
		}
		
		pathPainter.paint((Graphics2D) g, rotatorList);
		
		Calendar c = Calendar.getInstance();
		String fileType = "png";
		StringBuilder fileName = new StringBuilder("Rotator");
		fileName.append("_" + c.get(Calendar.YEAR));
		fileName.append("-" + (1+c.get(Calendar.MONTH)));
		fileName.append("-" + c.get(Calendar.DAY_OF_MONTH));
		fileName.append(" " + c.get(Calendar.HOUR_OF_DAY));
		fileName.append("-" + c.get(Calendar.MINUTE));
		fileName.append("-" + c.get(Calendar.SECOND));
		fileName.append("_" + initializer.getClass().getSimpleName());
		fileName.append("_" + pathPainter.getClass().getSimpleName());
		fileName.append("." + fileType);
		
		File output = new File(fileName.toString());
		try {
			ImageIO.write(img, fileType, output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Load the most current saved rotator
	 * @return
	 */
	public boolean loadRotatorList(){
		File f = new File(".");
		File[] inputList = f.listFiles(new FilenameFilter(){
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".dat");
			}
		});
		
		if(inputList.length == 0){
			return false;
		}
		
		// Sort the most current saved first
		Arrays.sort(inputList, new Comparator<File>(){
			@Override
			public int compare(File o1, File o2) {
				return (int) (o2.lastModified() - o1.lastModified());
			}
		});
		ObjectInputStream is = null;
		try {
			is = new ObjectInputStream(new FileInputStream(inputList[0]));
			Rotator r = (Rotator) is.readObject();
			
			numberOfCircle = r.getNumberOfCircle();
			numberOfDots = r.getNumberOfDots();
			
			rotatorList = r.getRotatorList();
			initializer = r.getInitializer();
			pathPainter = r.getPathPainter();
			direction = r.getDirection();
			alternateDirection = r.getAlternateDirection();

			drawDots = r.isDrawDots();
			drawCircle = r.isDrawCircle();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}

