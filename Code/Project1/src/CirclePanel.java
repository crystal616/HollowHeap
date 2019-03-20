
//**********************************************
//* The GUI. All used panels, label, buttons and listeners are defined in this class.
//* Panels: PaintPanel, represents the painting area/screen.
//*			ButtonPanel, holds all buttons like start, colors, size, etc.
//*			ShowPanel, shows initial circles and all next priority circles of 4 heaps
//**********************************************

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class CirclePanel extends JPanel implements ScreenCircleSize
{
	int MIN = CircleMin, MAX = CircleMax;
	int WIDTH = ScreenWidth, HEIGHT = ScreenHeight;

	public VisualArray vCircles = new VisualArray();
	Random generator = new Random();
	private boolean MinMaxOn, ColorSize, StartOn = true, FinishOn, LargeToSmall = false;

	PaintPanel p1;
	ButtonPanel p2;
	ShowPanel p5;
	JPanel p3, p4;
	private JLabel hint;
	private JButton start, max, min, red, green, blue, size, finish;
	private StartListener starts = new StartListener();
	private MaxMinListener MaxMin = new MaxMinListener();
	private ColorListener colors = new ColorListener();
	private SizeListener sizes = new SizeListener();
	private FinishListener finishs = new FinishListener();
	private DotListener dot = new DotListener();
	private LocationListener locations = new LocationListener();
	private int choose;
	LinkedList<Circle> showPoints = new LinkedList<Circle>();

	TrackArray track = new TrackArray(); // Initialize the track CircleArray
	MultiRoots redHeap, greenHeap, blueHeap;
	SingleRoot sizeHeap;

	public CirclePanel() // Add paint panel, button panel and show panel.
	{
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(WIDTH + 160 + 210, HEIGHT));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		p1 = new PaintPanel(vCircles);
		add(p1);
		add(Box.createRigidArea(new Dimension(5, HEIGHT)));
		p2 = new ButtonPanel(p1);
		add(p2);
		add(Box.createRigidArea(new Dimension(5, HEIGHT)));

		p5 = new ShowPanel();
		add(p5);
	}

	public void paintComponent(Graphics page)
	{
		super.paintComponent(page);
	}

	// Create a PaintPanel class
	private class PaintPanel extends JPanel implements ScreenCircleSize
	{
		int WIDTH = ScreenWidth, HEIGHT = ScreenHeight;
		private ArrayList<VisualCircle> points; // A list of all existing circles.

		public PaintPanel(VisualArray vCircles)
		{
			setBackground(Color.white);
			setPreferredSize(new Dimension(WIDTH, HEIGHT));
			points = vCircles.points;
			addMouseListener(locations); // This listener is used to respond to initial circle location choosing.
		}

		public void paintComponent(Graphics page)
		{
			super.paintComponent(page);

			for (VisualCircle spot : points)
			{
				int x = spot.column;
				int y = spot.row;
				int d = spot.realSize;
				page.setColor(spot.color);
				page.fillOval(x - d / 2, y - d / 2, d, d);
			}
			page.setFont(new Font("Arial", Font.BOLD, 20));
			page.setColor(Color.BLACK);
			page.drawString("Count: " + points.size(), 5, 35); // Count all added circles.
		}

	}

	// Create a private class ButtonPanel.
	private class ButtonPanel extends JPanel implements ScreenCircleSize
	{
		public PaintPanel paintp;

		public ButtonPanel(PaintPanel paintp)
		{
			this.paintp = paintp;

			setBackground(Color.white);
			setPreferredSize(new Dimension(150, ScreenHeight));
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

			p3 = new JPanel(); // This panel is used to give user some information.
			p3.setBackground(Color.yellow);
			p3.setPreferredSize(new Dimension(150, ScreenHeight / 4));
			p3.setLayout(new GridLayout(1, 1));

			hint = new JLabel("<html>" + "Press Start to start!" + "<html>");
			hint.setFont(new Font("Arial", Font.BOLD, 24));
			hint.setForeground(Color.red);
			p3.add(hint);

			start = new JButton("Start"); // Start button is used to start program.
			start.setEnabled(true);
			start.setFont(new Font("Arial", Font.BOLD, 18));
			start.addActionListener(starts); // This listener is used to generate initial circles.

			// Button max and min are used to let user choose order: larger to smaller, or
			// smaller to larger.
			max = new JButton("<html>" + "Larger to Smaller" + "<html>");
			max.setEnabled(true);
			max.setFont(new Font("Arial", Font.BOLD, 18));
			max.addActionListener(MaxMin); // This listener is used to generate 4 heaps.

			min = new JButton("<html>" + "Smaller to Larger" + "<html>");
			min.setEnabled(false);
			min.setFont(new Font("Arial", Font.BOLD, 18));
			min.addActionListener(MaxMin);

			// Choose to add next red circle.
			red = new JButton("<html>" + "Next Red Circle" + "<html>");
			red.setEnabled(false);
			red.setFont(new Font("Arial", Font.BOLD, 18));
			red.addActionListener(colors);
			
			// Choose to add next green circle.
			green = new JButton("<html>" + "Next Green Circle" + "<html>");
			green.setEnabled(false);
			green.setFont(new Font("Arial", Font.BOLD, 18));
			green.addActionListener(colors);
			
			//Choose to add next blue circle.
			blue = new JButton("<html>" + "Next Blue Circle" + "<html>");
			blue.setEnabled(false);
			blue.setFont(new Font("Arial", Font.BOLD, 18));
			blue.addActionListener(colors);

			//Choose to add next size circle.
			size = new JButton("<html>" + "Next Size" + "<html>");
			size.setEnabled(false);
			size.setFont(new Font("Arial", Font.BOLD, 18));
			size.addActionListener(sizes);

			//When user wants to stop.
			finish = new JButton("Finish!");
			finish.setEnabled(false);
			finish.setFont(new Font("Arial", Font.BOLD, 18));
			finish.addActionListener(finishs);

			add(p3);

			//Show Panel. Shrunken circles are printed. 
			p4 = new JPanel();
			p4.setBackground(Color.white);
			p4.setPreferredSize(new Dimension(150, 4 * ScreenHeight / 5));
			p4.setLayout(new GridLayout(8, 1));
			p4.add(start);
			p4.add(max);
			p4.add(min);
			p4.add(red);
			p4.add(blue);
			p4.add(green);
			p4.add(size);
			p4.add(finish);

			add(p4);
		}

		public void paintComponent(Graphics page)
		{
			super.paintComponent(page);
			start.setEnabled(StartOn);
			max.setEnabled(MinMaxOn);
			min.setEnabled(MinMaxOn);
			red.setEnabled(ColorSize);
			blue.setEnabled(ColorSize);
			green.setEnabled(ColorSize);
			size.setEnabled(ColorSize);
			finish.setEnabled(FinishOn);
		}
	}

	private class LocationListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent event)
		{
			Point p = event.getPoint();
			int row = p.y;
			int column = p.x;
			VisualCircle add;

			if (vCircles.findCircle(row, column).maxSize >= showPoints.get(choose).size)
			{
				add = new VisualCircle(row, column);
				add.color = showPoints.get(choose).color;
				add.realSize = showPoints.get(choose).size;
				vCircles.addCircle(add);
				p1.repaint();
				showPoints.set(choose, null);
				p5.repaint();
			} else
			{
				hint.setText("<html>" + "This space is too small for the circle! Choose another location!" + "<html>");
			}

			int count = 0;
			for (int i = 0; i < 5; i++)
			{
				if (showPoints.get(i) != null)
					count++;
			}
			if (count == 0)
			{
				hint.setText("<html>" + "Choose the order you want to add circles, large to small or small to large"
						+ "<html>");
			}
		}
	}

	private class MaxMinListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			MinMaxOn = false;
			StartOn = false;
			FinishOn = true;
			ColorSize = true;
			Object source = event.getSource();
			vCircles.findAvailableMax();
			int availableMax = vCircles.availableMax;

			if (source == max)
			{
				LargeToSmall = true;
				initializeMax(availableMax);
			} else
			{
				LargeToSmall = false;
				initializeMin(availableMax);
			}
			
			hint.setText("<html>" + "Choose your next circle!" + "<html>");
			p2.repaint();
		}
	}

	private Circle findSizePop()
	{
		HeapNode pop;
		if (LargeToSmall)
		{
			pop = ((SingleMax) sizeHeap).deleteNext();
		} else
		{
			pop = ((SingleMin) sizeHeap).deleteNext();
		}

		int location = pop.arrayLocation;
		Circle circle = track.get(location);
		Color c = circle.color;
		HeapNode n = track.getColor(location);

		if (c == Color.red)
		{
			if (LargeToSmall)
			{
				((MultiMax) redHeap).delete(n);
			} else
			{
				((MultiMin) redHeap).delete(n);
			}
		} else if (c == Color.blue)
		{
			if (LargeToSmall)
			{
				((MultiMax) blueHeap).delete(n);
			} else
			{
				((MultiMin) blueHeap).delete(n);
			}
		} else
		{
			if (LargeToSmall)
			{
				((MultiMax) greenHeap).delete(n);
			} else
			{
				((MultiMin) greenHeap).delete(n);
			}
		}

		track.delete(location);
		return circle;
	}

	private class SizeListener implements ActionListener
	{

		public void actionPerformed(ActionEvent event)
		{
			Circle pop = findSizePop();
			int size = pop.size;
			Color c = pop.color;
			VisualCircle target = vCircles.findPossibleCenter(size);
			int availableMax = vCircles.availableMax;
			if (availableMax == 0)
			{
				screenIsFull();
			}
			checkEmptyHeap(c, availableMax);

			while (target == null)
			{
				pop = findSizePop();
				size = pop.size;
				c = pop.color;
				target = vCircles.findPossibleCenter(size);
				availableMax = vCircles.availableMax;
				if (availableMax == 0)
				{
					screenIsFull();
				}
				checkEmptyHeap(c, availableMax);
			}

			target.color = c;
			vCircles.addCircle(target);

			vCircles.findAvailableMax();
			availableMax = vCircles.availableMax;
			removeTooLargeCircle(availableMax);

			if (LargeToSmall)
			{
				showPoints.clear();
				showPoints.addFirst(null);
				showPoints.add(track.get(redHeap.max.arrayLocation));
				showPoints.add(track.get(blueHeap.max.arrayLocation));
				showPoints.add(track.get(greenHeap.max.arrayLocation));
				showPoints.add(track.get(sizeHeap.max.arrayLocation));
				p5.repaint();
			} else
			{
				showPoints.clear();
				showPoints.addFirst(null);
				showPoints.add(track.get(redHeap.min.arrayLocation));
				showPoints.add(track.get(blueHeap.min.arrayLocation));
				showPoints.add(track.get(greenHeap.min.arrayLocation));
				showPoints.add(track.get(sizeHeap.min.arrayLocation));
				p5.repaint();
			}
			p1.repaint();
			hint.setText("<html>" + "Choose your next circle!" + "<html>");
			
		}
	}

	private void checkEmptyHeap(Color c, int availableMax)
	{
		if (c == Color.red)
		{
			if (redHeap.root.isEmpty())
			{
				addRedCircles(availableMax);
			}
		}
		if (c == Color.blue)
		{
			if (blueHeap.root.isEmpty())
			{
				addBlueCircles(availableMax);
			}
		}
		if (c == Color.green)
		{
			if (greenHeap.root.isEmpty())
			{
				addGreenCircles(availableMax);
			}
		}
	}

	private void addRedCircles(int availableMax)
	{
		Circle c;
		HeapNode n, m;
		int size, location;
		for (int i = 0; i < 10; i++)
		{
			c = new Circle(Color.red, availableMax);
			size = c.size;
			location = track.current;
			n = new HeapNode(size, location);
			m = new HeapNode(size, location);
			if (LargeToSmall)
			{
				((MultiMax) redHeap).add(n);
				((SingleMax) sizeHeap).add(m);
			} else
			{
				((MultiMin) redHeap).add(n);
				((SingleMin) sizeHeap).add(m);
			}
			track.add(c, n, m);
		}

	}

	private void addBlueCircles(int availableMax)
	{
		Circle c;
		HeapNode n, m;
		int size, location;
		for (int i = 0; i < 10; i++)
		{
			c = new Circle(Color.blue, availableMax);
			size = c.size;
			location = track.current;
			n = new HeapNode(size, location);
			m = new HeapNode(size, location);
			if (LargeToSmall)
			{
				((MultiMax) blueHeap).add(n);
				((SingleMax) sizeHeap).add(m);
			} else
			{
				((MultiMin) blueHeap).add(n);
				((SingleMin) sizeHeap).add(m);
			}
			track.add(c, n, m);
		}
	}

	private void addGreenCircles(int availableMax)
	{
		Circle c;
		HeapNode n, m;
		int size, location;
		for (int i = 0; i < 10; i++)
		{
			c = new Circle(Color.green, availableMax);
			size = c.size;
			location = track.current;
			n = new HeapNode(size, location);
			m = new HeapNode(size, location);
			if (LargeToSmall)
			{
				((MultiMax) greenHeap).add(n);
				((SingleMax) sizeHeap).add(m);
			} else
			{
				((MultiMin) greenHeap).add(n);
				((SingleMin) sizeHeap).add(m);
			}
			track.add(c, n, m);
		}
	}

	private class FinishListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}

	}

	private class StartListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			StartOn = false;
			MinMaxOn = true;
			p2.repaint();
			hint.setText("<html>" + "Select locations for all circles, one by one." + "<html>");

			for (int i = 0; i < 5; i++)
			{
				Circle n = new Circle();
				showPoints.add(n);
			}
			p5.repaint();

		}
	}

	private Circle findColorPop(Color c)
	{
		HeapNode pop;
		if (c == Color.red)
		{
			if (LargeToSmall)
			{
				pop = ((MultiMax) redHeap).deleteNext();
			} else
			{
				pop = ((MultiMin) redHeap).deleteNext();
			}
		} else if (c == Color.blue)
		{
			if (LargeToSmall)
			{
				pop = ((MultiMax) blueHeap).deleteNext();
			} else
			{
				pop = ((MultiMin) blueHeap).deleteNext();
			}
		} else
		{
			if (LargeToSmall)
			{
				pop = ((MultiMax) greenHeap).deleteNext();
			} else
			{
				pop = ((MultiMin) greenHeap).deleteNext();
			}
		}

		int location = pop.arrayLocation;
		Circle circle = track.get(location);
		HeapNode n = track.getSize(location);

		if (LargeToSmall)
		{
			((SingleMax) sizeHeap).delete(n);
		} else
		{
			((SingleMin) sizeHeap).delete(n);
		}

		track.delete(location);
		int availableMax = vCircles.availableMax;
		if (availableMax == 0)
		{
			screenIsFull();
		}
		checkEmptyHeap(c, availableMax);
		return circle;
	}

	private void screenIsFull()
	{
		hint.setText("<html>" + "Screen is Full! Please press Finish button!" + "<html>");
		ColorSize = false;
		p2.repaint();
		return;
	}

	private class ColorListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			Circle pop;

			Object source = event.getSource();

			if (source == red)
			{
				pop = findColorPop(Color.red);
			} else if (source == blue)
			{
				pop = findColorPop(Color.blue);
			} else
			{
				pop = findColorPop(Color.green);
			}

			int size = pop.size;
			Color c = pop.color;
			VisualCircle target = vCircles.findPossibleCenter(size);
			int availableMax = vCircles.availableMax;
			if (availableMax == 0)
			{
				screenIsFull();
			}

			while (target == null)
			{
				if (source == red)
				{
					pop = findColorPop(Color.red);
				} else if (source == blue)
				{
					pop = findColorPop(Color.blue);
				} else
				{
					pop = findColorPop(Color.green);
				}
				size = pop.size;
				c = pop.color;
				target = vCircles.findPossibleCenter(size);
				availableMax = vCircles.availableMax;
				if (availableMax == 0)
				{
					screenIsFull();
				}
			}

			target.color = c;
			vCircles.addCircle(target);

			vCircles.findAvailableMax();
			availableMax = vCircles.availableMax;
			removeTooLargeCircle(availableMax);

			if (LargeToSmall)
			{
				showPoints.clear();
				showPoints.addFirst(null);
				showPoints.add(track.get(redHeap.max.arrayLocation));
				showPoints.add(track.get(blueHeap.max.arrayLocation));
				showPoints.add(track.get(greenHeap.max.arrayLocation));
				showPoints.add(track.get(sizeHeap.max.arrayLocation));
				p5.repaint();
			} else
			{
				showPoints.clear();
				showPoints.addFirst(null);
				showPoints.add(track.get(redHeap.min.arrayLocation));
				showPoints.add(track.get(blueHeap.min.arrayLocation));
				showPoints.add(track.get(greenHeap.min.arrayLocation));
				showPoints.add(track.get(sizeHeap.min.arrayLocation));
				p5.repaint();
			}
			p1.repaint();
			hint.setText("<html>" + "Choose your next circle!" + "<html>");
		}
	}

	private void removeTooLargeCircle(int availableMax)
	{
		int count = 1;
		HeapNode n, m;

		if (LargeToSmall)
		{
			while (count != 0)
			{
				count = 0;
				if (redHeap.max.size > availableMax)
				{
					n = ((MultiMax) redHeap).deleteNext();
					int location = n.arrayLocation;
					m = track.getSize(location);
					((SingleMax) sizeHeap).delete(m);
					track.delete(location);
					count++;
					checkEmptyHeap(Color.red, availableMax);
				}

				if (greenHeap.max.size > availableMax)
				{
					n = ((MultiMax) greenHeap).deleteNext();
					int location = n.arrayLocation;
					m = track.getSize(location);
					((SingleMax) sizeHeap).delete(m);
					track.delete(location);
					count++;
					checkEmptyHeap(Color.green, availableMax);
				}

				if (blueHeap.max.size > availableMax)
				{
					n = ((MultiMax) blueHeap).deleteNext();
					int location = n.arrayLocation;
					m = track.getSize(location);
					((SingleMax) sizeHeap).delete(m);
					track.delete(location);
					count++;
					checkEmptyHeap(Color.blue, availableMax);
				}
			}
		} else
		{
			while (count != 0)
			{
				count = 0;
				if (redHeap.min.size > availableMax)
				{
					n = ((MultiMin) redHeap).deleteNext();
					int location = n.arrayLocation;
					m = track.getSize(location);
					((SingleMin) sizeHeap).delete(m);
					track.delete(location);
					count++;
					checkEmptyHeap(Color.red, availableMax);
				}

				if (greenHeap.min.size > availableMax)
				{
					n = ((MultiMin) greenHeap).deleteNext();
					int location = n.arrayLocation;
					m = track.getSize(location);
					((SingleMin) sizeHeap).delete(m);
					track.delete(location);
					count++;
					checkEmptyHeap(Color.green, availableMax);
				}

				if (blueHeap.min.size > availableMax)
				{
					n = ((MultiMin) blueHeap).deleteNext();
					int location = n.arrayLocation;
					m = track.getSize(location);
					((SingleMin) sizeHeap).delete(m);
					track.delete(location);
					count++;
					checkEmptyHeap(Color.blue, availableMax);
				}
			}
		}
	}

	private void initializeMax(int availableMax)
	{
		redHeap = new MultiMax();
		greenHeap = new MultiMax();
		blueHeap = new MultiMax();
		sizeHeap = new SingleMax();

		Circle c;
		HeapNode n, m;
		int location, size;

		for (int i = 0; i < 10; i++)
		{
			c = new Circle(Color.RED, availableMax);
			size = c.size;
			location = track.current;
			n = new HeapNode(size, location);
			((MultiMax) redHeap).add(n);
			m = new HeapNode(size, location);
			((SingleMax) sizeHeap).add(m);
			track.add(c, n, m);
		}

		for (int i = 0; i < 10; i++)
		{
			c = new Circle(Color.blue, availableMax);
			size = c.size;
			location = track.current;
			n = new HeapNode(size, location);
			((MultiMax) blueHeap).add(n);
			m = new HeapNode(size, location);
			((SingleMax) sizeHeap).add(m);
			track.add(c, n, m);
		}
		for (int i = 0; i < 10; i++)
		{
			c = new Circle(Color.green, availableMax);
			size = c.size;
			location = track.current;
			n = new HeapNode(size, location);
			((MultiMax) greenHeap).add(n);
			m = new HeapNode(size, location);
			((SingleMax) sizeHeap).add(m);
			track.add(c, n, m);
		}

		showPoints.clear();
		showPoints.addFirst(null);
		showPoints.add(track.get(redHeap.max.arrayLocation));
		showPoints.add(track.get(blueHeap.max.arrayLocation));
		showPoints.add(track.get(greenHeap.max.arrayLocation));
		showPoints.add(track.get(sizeHeap.max.arrayLocation));
		p5.repaint();
		hint.setText("<html>" + "Choose next circle by press relative button" + "<html>");
	}

	private void initializeMin(int availableMax)
	{
		redHeap = new MultiMin();
		greenHeap = new MultiMin();
		blueHeap = new MultiMin();
		sizeHeap = new SingleMin();

		HeapNode n, m;
		Circle c;
		int size, location;

		for (int i = 0; i < 10; i++)
		{
			c = new Circle(Color.RED, availableMax);
			size = c.size;
			location = track.current;
			n = new HeapNode(size, location);
			((MultiMin) redHeap).add(n);
			m = new HeapNode(size, location);
			((SingleMin) sizeHeap).add(m);
			track.add(c, n, m);
		}

		for (int i = 0; i < 10; i++)
		{
			c = new Circle(Color.blue, availableMax);
			size = c.size;
			location = track.current;
			n = new HeapNode(size, location);
			((MultiMin) blueHeap).add(n);
			m = new HeapNode(size, location);
			((SingleMin) sizeHeap).add(m);
			track.add(c, n, m);
		}
		for (int i = 0; i < 10; i++)
		{
			c = new Circle(Color.green, availableMax);
			size = c.size;
			location = track.current;
			n = new HeapNode(size, location);
			((MultiMin) greenHeap).add(n);
			m = new HeapNode(size, location);
			((SingleMin) sizeHeap).add(m);
			track.add(c, n, m);
		}

		showPoints.clear();
		showPoints.addFirst(null);
		showPoints.add(track.get(redHeap.min.arrayLocation));
		showPoints.add(track.get(blueHeap.min.arrayLocation));
		showPoints.add(track.get(greenHeap.min.arrayLocation));
		showPoints.add(track.get(sizeHeap.min.arrayLocation));
		p5.repaint();
	}

	private class ShowPanel extends JPanel implements ScreenCircleSize
	{

		public ShowPanel()
		{
			setBackground(Color.WHITE);
			setPreferredSize(new Dimension(210, ScreenHeight));

			addMouseListener(dot);
		}

		public void paintComponent(Graphics page)
		{
			super.paintComponent(page);
			page.setFont(new Font("TimesRoman", Font.BOLD, 16));
			for (int i = 0; i < showPoints.size(); i++)
			{
				if (showPoints.get(i) != null)
				{
					int x = 15;
					int y = 15 + i * ScreenHeight / 5;

					int d = showPoints.get(i).size / 3;
					page.setColor(showPoints.get(i).color);
					page.fillOval(100 - d / 2, i * ScreenHeight / 5 + ScreenHeight / 10 - d / 2, d, d);
					page.drawString("Size: " + showPoints.get(i).size, x, y);
				}
			}
		}
	}

	private class DotListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent event)
		{
			Point p = event.getPoint();

			int y = p.y;
			if (y < HEIGHT / 5)
				choose = 0;
			else if (y < 2 * HEIGHT / 5)
				choose = 1;
			else if (y < 3 * HEIGHT / 5)
				choose = 2;
			else if (y < 4 * HEIGHT / 5)
				choose = 3;
			else
				choose = 4;

			hint.setText("<html>" + "Choose location for this circle" + "<html>");

		}
	}
}
