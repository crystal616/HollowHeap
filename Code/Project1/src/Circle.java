//******************************************
//* This class defines an object type, Circle.
//******************************************

import java.awt.Color;
import java.util.Random;

public class Circle implements ScreenCircleSize
{
	int MIN, MAX;	//Limit of circle diameter
	int size;
	Color color;

	Random generator = new Random();

	public Circle(Color color)	//Generate a circle, color is given, size is randomly chosen
	{
		MIN = CircleMin;
		MAX = CircleMax;
		this.color = color;
		size = generator.nextInt(MAX - MIN + 1) + MIN;
	}

	public Circle()		//Generate a circle, color and size are both randomly chosen
	{
		MIN = CircleMin;
		MAX = CircleMax;
		size = generator.nextInt(MAX - MIN + 1) + MIN;

		int select = generator.nextInt(3);
		switch (select)
		{
		case 0:
			color = Color.red;
			break;
		case 1:
			color = Color.green;
			break;
		default:
			color = Color.blue;
			break;
		}
	}

	public Circle(Color color, int mAX)		//Generate a circle, color is given, size is randomly chosen,
	{										//but max diameter is given too.
		if (mAX == 0)
		{
			return;
		}
		MIN = CircleMin;
		this.color = color;
		size = generator.nextInt(mAX - MIN + 1) + MIN;
	}
}
