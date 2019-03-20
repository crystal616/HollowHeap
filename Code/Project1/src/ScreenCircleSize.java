//************************************
//* This interface defines the size of screen (painting) and circle.
//* All classes of this program, if need those size information,
//* will implement this interface.
//************************************

public interface ScreenCircleSize
{
	public final int ScreenHeight = 980;
	public final int ScreenWidth = 1550;
	public final int CircleMax = 500;	// Maximum diameter
	public final int CircleMin = 10;	// Minimum diameter
}
