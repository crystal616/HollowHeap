//**************************************
//* An abstract class defines single root hollow heap and relative methods
//**************************************

import java.util.LinkedList;

public abstract class SingleRoot extends HollowHeap
{
	public HeapNode deleteMin()
	{
		HeapNode result = min;
		root = result.child;	//Move all children of min node to list root.
		result.child = new LinkedList<HeapNode>();
		
		removeHollowRoot();
		linkMin();		//Rank and link all root nodes
		findMin();		//Find new min node
		return result;
	}

	public HeapNode deleteMax()		//Same steps as deleteMin to delete max
	{
		HeapNode result = max;
		root = result.child;
		result.child = new LinkedList<HeapNode>();

		removeHollowRoot();
		linkMax();
		findMax();
		return result;
	}

	public void findMin()
	{
		super.findMin();	//Use super call to find min from list root
		root.remove(min);	//Remove min from list root.
		min.child.addAll(root);		//Add all left subtrees as min's children. This is unranked-link, or a meld.
		root = new LinkedList<HeapNode>();
	}

	public void findMax()	//Same steps as findMin() to find max and link/meld
	{
		super.findMax();
		root.remove(max);
		max.child.addAll(root);
		root = new LinkedList<HeapNode>();
	}

}
