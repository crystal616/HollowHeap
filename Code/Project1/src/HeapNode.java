//**********************************
//* Defines the node used in hollow heap.
//* 
//**********************************

import java.util.LinkedList;

public class HeapNode implements Comparable<HeapNode>
{
	public int size;
	public int rank;
	public int arrayLocation;
	public LinkedList<HeapNode> child;
	public boolean hollow;

	public HeapNode(int size, int location)
	{
		this.size = size;	// The circle diameter referenced by this node.
		rank = 0;			// This node's rank
		child = new LinkedList<HeapNode>();		// A list of all its children
		hollow = false;
		arrayLocation = location;		// The record array index of the circle referenced by this node.
	}

	public void setHollow(boolean hollow)
	{
		this.hollow = hollow;
	}

	public int compareTo(HeapNode n)
	{
		return (this.size - n.size);
	}

}
