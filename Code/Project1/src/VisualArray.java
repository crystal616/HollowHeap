//*************************************************
//* A class which denotes and controls the whole painting screen.
//*************************************************

import java.util.ArrayList;

public class VisualArray implements ScreenCircleSize
{

	int MIN = CircleMin, MAX = CircleMax;
	int WIDTH = ScreenWidth, HEIGHT = ScreenHeight;
	int availableMax;	//For whole screen, the diameter of the largest circle it can hold now.

	ArrayList<VisualCircle> points;		//List of all existing circles.

	VisualCircle[][] circles;

	public VisualArray()
	{
		availableMax = MAX;
		circles = new VisualCircle[HEIGHT][WIDTH];	//A 2-D array, each item represents a point on screen.
		for (int i = 0; i < HEIGHT; i++)
		{
			for (int j = 0; j < WIDTH; j++)
			{
				circles[i][j] = new VisualCircle(i, j);
			}
		}

		points = new ArrayList<VisualCircle>();
	}

	public void addCircle(VisualCircle p)
	{
		points.add(p);
		for (int i = 0; i < circles.length; i++)	//For each point on the screen, compute if this new added
		{											//circle has changed its maxSize value.
			for (int j = 0; j < circles[i].length; j++)
			{
				circles[i][j].findMax(p);
			}
		}
	}

	public VisualCircle findPossibleCenter(int size)	//Find a point using which as the center, we can draw
	{													//a circle whose diameter is input size.
		VisualCircle result = null;

		findAvailableMax();		//The largest circle diameter that screen can hold now.
		if (availableMax == 0)	//The screen is full.
		{
			return result;
		}

		for (int i = 0; i < HEIGHT; i++)	//Screening from left upper corner to right lower corner, find out
		{									//a point which can exactly "fit" this circle.
			for (int j = 0; j < WIDTH; j++)
			{
				if (circles[i][j].maxSize == size)
				{
					circles[i][j].realSize = size;
					result = circles[i][j];
					return result;
				}
			}
		}

		if (result == null)		//If no point is just fit this circle, find a space which is bigger than needed.
		{
			for (int i = 0; i < HEIGHT; i++)
			{
				for (int j = 0; j < WIDTH; j++)
				{
					if (circles[i][j].maxSize >= size)
					{
						circles[i][j].realSize = size;
						result = circles[i][j];
						return result;
					}
				}
			}
		}
		//If no space fits this circle, return null.
		return result;
	}

	public void findAvailableMax()	//Find out the biggest circle diameter now screen can hold
	{
		availableMax = 0;
		for (int i = 0; i < HEIGHT; i++)
		{
			for (int j = 0; j < WIDTH; j++)
			{
				if (circles[i][j].maxSize > availableMax)
				{
					availableMax = circles[i][j].maxSize;
				}
			}
		}
	}

	public VisualCircle findCircle(int x, int y) //Return the point whose location is (x,y)
	{
		return circles[x][y];
	}
}