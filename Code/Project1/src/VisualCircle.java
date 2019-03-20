//********************************************
//* This object denotes one point on the painting screen.
//* This point can be used as a center to draw a circle. 
//********************************************

import java.awt.Color;

public class VisualCircle implements ScreenCircleSize
{
	int MIN = CircleMin, MAX = CircleMax;
	int WIDTH = ScreenWidth, HEIGHT = ScreenHeight;

	int row, column;	//Point location on screen.
	int maxSize;		//The diameter of the largest circle that can be drawn using this point as the center.
	int realSize;		//The real diameter of circle drawn using this point as the center.
	Color color;		//The circle color.

	public VisualCircle(int x, int y)
	{
		this.row = x;
		this.column = y;

		int t = this.findMin();	//When there is no circle existing, compute the maxSize.
		maxSize = t * 2;
		if (maxSize > MAX)
			maxSize = MAX;
		if (maxSize < MIN)
			maxSize = 0;

		realSize = 0;
		
		color = Color.white;
	}

	private int findMin()	//Find the minimum distance from this point to 4 screen "edges"
	{
		int temp = row;
		if (temp > column)
			temp = column;
		if (temp > WIDTH - column)
			temp = WIDTH - column;
		if (temp > HEIGHT - row)
			temp = HEIGHT - row;

		return temp;
	}

	public void findMax(VisualCircle p)	//After a circle p is drawn, find the new maxSize.
	{

		if (this.maxSize == 0)	//If this point's maxSize is already 0, we no longer need compute it.
			return;

		int x = p.column;
		int y = p.row;
		//Computer the distance from this point to p's center
		double temp = Math.sqrt(Math.pow((x - this.column), 2) + Math.pow((y - this.row), 2));
		//The possible largest radius is this distance minus p's radius.
		temp = temp - p.realSize / 2;

		int temp2 = ((int) temp) * 2;

		if (temp2 < MIN)	//Find out the maxSize of this point
		{
			this.maxSize = 0;
			return;
		}

		if (this.maxSize > temp2)
		{
			this.maxSize = temp2;
		}
	}
}