//******************************************************
//* This class is used to store circle and its "location" in color heap and size heap.
//* Its node, trackNode, is defined as a private class within this class.
//******************************************************

public class TrackArray
{
	int VOLUME = 40;	//Initial array size
	int current;		//Next available array index
	trackNode[] track;
	HeapNode color, size;

	public TrackArray()
	{
		current = 0;
		track = new trackNode[VOLUME];
	}

	public void add(Circle n, HeapNode color, HeapNode size)	//Add a circle and its "locations"
	{
		if (current >= track.length)	//If the array is full, double its size.
			doubleSize();

		track[current] = new trackNode(n, color, size);
		current++;
	}

	public void delete(int location)	//Delete an item by moving the last item to this given index.
	{
		track[location] = track[current - 1];
		current--;
		track[location].color.arrayLocation = location;
		track[location].size.arrayLocation = location;
	}

	public HeapNode getColor(int location)	
	{
		return track[location].color;
	}

	public HeapNode getSize(int location)
	{
		return track[location].size;
	}

	public Circle get(int location)
	{
		return track[location].circle;
	}

	private void doubleSize()	//Expand the array
	{
		int s = track.length;
		trackNode[] temp = new trackNode[s * 2];
		for (int i = 0; i < s; i++)
		{
			temp[i] = track[i];
		}
		track = temp;
	}

	private class trackNode
	{
		HeapNode color, size;	//"locations" in 2 heaps
		Circle circle;

		public trackNode(Circle n, HeapNode color, HeapNode size)
		{
			this.color = color;
			this.size = size;
			circle = n;
		}

	}
}
