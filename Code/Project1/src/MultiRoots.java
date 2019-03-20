//*************************************************
//* An abstrac class defines multi roots hollow heap and relative methods
//*************************************************

public abstract class MultiRoots extends HollowHeap
{
	public void add(HeapNode n)		//Add node to the front of list root.
	{
		root.addFirst(n);
	}

	public HeapNode deleteMin()		
	{
		HeapNode result = min;
		root.remove(min);
		if (result.child != null)	//Add all children of min to list root, remove any hollow node which is 
		{							//in list root. Link nodes.
			root.addAll(result.child);
			removeHollowRoot();
			linkMin();
		}
		findMin();		//Find out which node is the min via comparing nodes which are roots.
		return result;
	}

	public HeapNode deleteMax()		//Use the same steps to delete max node and find new max from all left nodes.
	{
		HeapNode result = max;
		root.remove(max);
				
		if (result.child != null)
		{
			root.addAll(result.child);
			removeHollowRoot();
			linkMax();
		}
		findMax();
		return result;
	}

}
