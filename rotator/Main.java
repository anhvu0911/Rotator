package rotator;

import init.DefaultInitializer;
import init.DotCircleInitializer;
import init.IncrementalInitializer;
import init.PipeInitializer;
import init.RandomInitializer;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import pathPainter.BezierPath2Painter;
import pathPainter.BezierPath3Painter;
import pathPainter.BezierPathPainter;
import pathPainter.BinaryPathPainter;
import pathPainter.CubicBladePathPainter;
import pathPainter.CubicCentralPathPainter;
import pathPainter.CubicFlowerPetalPathPainter;
import pathPainter.CubicReverseFlowerPetalPathPainter;
import pathPainter.Default2PathPainter;
import pathPainter.DefaultPathPainter;
import pathPainter.FlowerPathPainter;
import pathPainter.PathPainter;
import pathPainter.PolygonPainter;

public class Main {

	private JFrame frame;
	private Rotator r; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Main();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Rotator");
		frame.setBounds(100, 100, 924, 730);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		r = new Rotator();
		r.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
		//---------------------------------
		// dot circle initialization list
		
		JLabel jlbl_InitType = new JLabel("Init Type");
		final DotCircleInitializer[] initList = {
				new DefaultInitializer(), 
				new PipeInitializer(),
				new RandomInitializer(),
				new IncrementalInitializer()};
		final JList jl_initType = new JList(initList);
		jl_initType.setSelectedIndex(0);
		jl_initType.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jl_initType.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {
					r.setInitializer(initList[jl_initType.getSelectedIndex()]);
					
			    }
			}
		});

		//---------------------------------
		// draw pattern list
		
		JLabel jlbl_NewLabel = new JLabel("Draw Type");
		final PathPainter[] pathPainterList = {
				new DefaultPathPainter(),
				new Default2PathPainter(),
				new BezierPathPainter(),
				new BezierPath2Painter(),
				new BezierPath3Painter(),
				new CubicBladePathPainter(), 
				new CubicCentralPathPainter(), 
				new CubicFlowerPetalPathPainter(), 
				new CubicReverseFlowerPetalPathPainter(), 
				new BinaryPathPainter(),
				new PolygonPainter(),
				new FlowerPathPainter()};
		final JList jl_drawType = new JList(pathPainterList);
		jl_drawType.setSelectedIndex(0);
		jl_drawType.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jl_drawType.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {
					r.setPathPainter(pathPainterList[jl_drawType.getSelectedIndex()]);
			    }
			}
		});

		//---------------------------------
		// Option for drawing
		
		final JCheckBox jchb_DrawDots = new JCheckBox("draw Dots");
		jchb_DrawDots.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				r.setDrawDots(jchb_DrawDots.isSelected());
			}
		});
		
		final JCheckBox jchb_DrawCircle = new JCheckBox("draw Circle", true);
		jchb_DrawCircle.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				r.setDrawCircle(jchb_DrawCircle.isSelected());
			}
		});
		
		final JCheckBox jchb_ReverseRotation = new JCheckBox("reverse rotation");
		jchb_ReverseRotation.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				r.reverseDirection();
			}
		});
		
		final JCheckBox jchb_SwitchRotation = new JCheckBox("switch rotation");
		jchb_SwitchRotation.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				r.enableAlternateDirection();
			}
		});
		
		final JSpinner js_NoOfCircles = new JSpinner();
		js_NoOfCircles.setValue(r.getNumberOfCircle());
		js_NoOfCircles.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				r.setNumberOfCircle(Integer.parseInt(js_NoOfCircles.getValue()+"") );				
			}
		});
		
		final JSpinner js_NoOfDot = new JSpinner();
		js_NoOfDot.setValue(r.getNumberOfDots());
		js_NoOfDot.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				r.setNumberOfDots(Integer.parseInt(js_NoOfDot.getValue()+"") );				
			}
		});

		//---------------------------------
		// Movie Control
		
		final JButton jbtn_Prev = new JButton("<");
		jbtn_Prev.setEnabled(false);
		jbtn_Prev.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				r.reverse();
				r.repaint();
			}			
		});
		
		final JButton jbn_Next = new JButton(">");
		jbn_Next.setEnabled(false);
		jbn_Next.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				r.update();
				r.repaint();
			}			
		});
		
		final JToggleButton jtbtn_Pause = new JToggleButton("Pause");
		jtbtn_Pause.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				boolean isPause = jtbtn_Pause.isSelected();
				r.setPause(isPause);
				jbtn_Prev.setEnabled(isPause);
				jbn_Next.setEnabled(isPause);
				jtbtn_Pause.setText(isPause?"Play":"Pause");				
			}
		});
		
		//---------------------------------
		// Miscellaneous
		
		JButton jbtn_Randomize = new JButton("Randomize");
		jbtn_Randomize.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int indexInitList = (int)(Math.random()*(initList.length-1));
				r.setInitializer(initList[indexInitList]);
				jl_initType.setSelectedIndex(indexInitList);

				int indexPathPainterList = (int)(Math.random()*(pathPainterList.length-1));
				r.setPathPainter(pathPainterList[indexPathPainterList]);
				jl_drawType.setSelectedIndex(indexPathPainterList);
				
				js_NoOfCircles.setValue((int)(Math.random()*11 + 3));
				js_NoOfDot.setValue((int)(Math.random()*11 + 3));
				jchb_DrawCircle.setSelected(Math.random() > 0.5);
				jchb_DrawDots.setSelected(Math.random() > 0.7);
				jchb_ReverseRotation.setSelected(Math.random() > 0.7);
				jchb_SwitchRotation.setSelected(Math.random() > 0.85);
				jtbtn_Pause.setSelected(false);
			}			
		});
		
		JButton jbtn_TakeScreenshot = new JButton("Take Screenshot");
		jbtn_TakeScreenshot.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				r.saveToImage();
			}			
		});
		
		JButton jbtn_Save = new JButton("Save");
		jbtn_Save.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				saveRotatorList();
			}			
		});
		
		JLabel lblDots = new JLabel("dots");
		
		JLabel lblCircles = new JLabel("circles");
		
		JScrollPane jsp_Init = new JScrollPane();
		jsp_Init.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jsp_Init.setViewportView(jl_initType);
		
		JScrollPane jsp_DrawType = new JScrollPane();
		jsp_DrawType.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jsp_DrawType.setViewportView(jl_drawType);
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(r, GroupLayout.PREFERRED_SIZE, 672, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(jbtn_TakeScreenshot)
							.addContainerGap())
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(jbtn_Randomize)
								.addContainerGap())
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(jchb_SwitchRotation)
									.addContainerGap())
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(jchb_ReverseRotation)
										.addContainerGap())
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(jsp_DrawType, GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
												.addComponent(jlbl_InitType)
												.addComponent(jlbl_NewLabel)
												.addComponent(jsp_Init, GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
											.addGap(55))
										.addGroup(groupLayout.createSequentialGroup()
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayout.createSequentialGroup()
													.addComponent(jchb_DrawCircle)
													.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
													.addComponent(js_NoOfCircles, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
												.addGroup(groupLayout.createSequentialGroup()
													.addComponent(jchb_DrawDots)
													.addPreferredGap(ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
													.addComponent(js_NoOfDot, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(lblDots)
												.addComponent(lblCircles))
											.addGap(66))
										.addGroup(groupLayout.createSequentialGroup()
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(jbtn_Save)
												.addGroup(groupLayout.createSequentialGroup()
													.addComponent(jbtn_Prev)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(jtbtn_Pause)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(jbn_Next)))
											.addGap(65))))))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(r, GroupLayout.PREFERRED_SIZE, 668, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(jlbl_InitType)
							.addGap(1)
							.addComponent(jsp_Init, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jlbl_NewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jsp_DrawType, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(jchb_DrawDots)
								.addComponent(lblDots)
								.addComponent(js_NoOfDot, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(jchb_DrawCircle)
								.addComponent(js_NoOfCircles, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblCircles))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jchb_ReverseRotation)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jchb_SwitchRotation)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jbtn_Randomize)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jbtn_TakeScreenshot)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(jbtn_Save)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(jbtn_Prev)
								.addComponent(jtbtn_Pause)
								.addComponent(jbn_Next))))
					.addContainerGap(13, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
	}

	/**
	 * Save to file
	 */
	public void saveRotatorList(){
		Calendar c = Calendar.getInstance();
		StringBuilder fileName = new StringBuilder("Rotator");
		fileName.append("_" + c.get(Calendar.YEAR));
		fileName.append("-" + (1+c.get(Calendar.MONTH)));
		fileName.append("-" + c.get(Calendar.DAY_OF_MONTH));
		fileName.append(" " + c.get(Calendar.HOUR_OF_DAY));
		fileName.append("-" + c.get(Calendar.MINUTE));
		fileName.append("-" + c.get(Calendar.SECOND));
		fileName.append("_" + r.getInitializer().getClass().getSimpleName());
		fileName.append("_" + r.getPathPainter().getClass().getSimpleName());
		fileName.append(".dat");
		
		File output = new File(fileName.toString());
		ObjectOutputStream os = null;
		try {
			os = new ObjectOutputStream(new FileOutputStream(output));
			os.writeObject(r);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
