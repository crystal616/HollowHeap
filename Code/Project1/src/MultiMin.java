//************************************************
//* A multi-roots min heap. All methods are defined, or inherited from super classes and modified.
//************************************************

import java.util.LinkedList;

public class MultiMin extends MultiRoots
{
	public void add(HeapNode n)		//Add node to root, find which is the min node
	{
		super.add(n);
		findMin(n);
	}

	private void findMin(HeapNode n)
	{
		if (min == null)
			min = n;
		else
		{
			if (min.size > n.size)
				min = n;
		}
	}

	public HeapNode deleteNext()	//Delete the highest priority node of this heap.
	{
		return deleteMin();
	}

	public void delete(HeapNode n)		//Delete a given node
	{
		if (min == n)		//If this node is the min, deleteMin is called.
		{
			deleteNext();
		} else if (root.contains(n))	//If it is one root node, make it hollow and remove hollow root nodes.
		{
			n.hollow = true;
			removeHollowRoot();
		} else
		{
			n.hollow = true;	//If it is not a root node, make it hollow.
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
			removeHollowRoot();
		}
	}
}
