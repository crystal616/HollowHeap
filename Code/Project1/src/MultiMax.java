//************************************************
//* A multi-roots max heap. All methods are defined or inherited from super classes
//************************************************

import java.util.LinkedList;

public class MultiMax extends MultiRoots
{
	public void add(HeapNode n)		//Add node to root, and find out if this node is the max.
	{
		super.add(n);
		findMax(n);
	}

	private void findMax(HeapNode n)
	{
		if (max == null)
			max = n;
		else
		{
			if (max.size < n.size)		//Compare input node with current max, find out which is bigger.
				max = n;
		}
	}

	public HeapNode deleteNext()	//Delete highest priority node.
	{
		return deleteMax();		//deleteMax is inherited from class MultiRoots
	}

	public void delete(HeapNode n)	//Given a node, delete it from this heap.
	{
		if (max == n)		//If this node is the max node, deleteNext().
		{
			deleteNext();
		} else if (root.contains(n))	//If it is not the max but is one root node.
		{
			n.hollow = true;
			removeHollowRoot();
		} else		//If it is not a root node
		{
			n.hollow = true;	//Make it hollow
			if (n.rank > 2)		//If its rank is greater than 2, move some children to root.
			{
				LinkedList<HeapNode> childNumber = n.child;
				n.child = new LinkedList<HeapNode>();
				while (!childNumber.isEmpty())
				{
					HeapNode t = childNumber.removeFirst();
					if (t.rank != n.rank - 1 && t.rank != n.rank - 2)
					{
						root.add(t);
					} else
					{
						n.child.add(t);
					}
				}
			}
			removeHollowRoot();		//Check if there are hollow root nodes.
		}
	}

}
