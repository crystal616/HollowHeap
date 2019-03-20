//************************************************
//* A single root max heap. All needed methods are defined, or inherited from super classes or/and modified.
//************************************************

import java.util.LinkedList;

public class SingleMax extends SingleRoot
{
	public void add(HeapNode n)		//Add a node to heap.
	{
		if (max == null)
		{
			max = n;
		} else		//Compare this node with current max, find out which is bigger.
		{			//Make bigger one the max, add another as its child.
			if (max.size < n.size)
			{
				n.child.addAll(max.child);
				max.child = new LinkedList<HeapNode>();
				n.child.addFirst(max);
				max = n;
			} else
			{
				max.child.addFirst(n);
			}
		}
	}

	public HeapNode deleteNext()	//Delete the highest priority in this heap.
	{
		return deleteMax();
	}

	public void delete(HeapNode n)	//Delete a given node
	{
		if (max == n)	//If it is the max, call deleteNext()
		{
			deleteNext();
		} else
		{
			n.hollow = true;
			if (n.rank > 2)	//If its rank is greater than 2, move some children, meld with max.
			{
				LinkedList<HeapNode> childNew = n.child;
				n.child = new LinkedList<HeapNode>();
				while (!childNew.isEmpty())
				{
					HeapNode t = childNew.removeFirst();
					if (t.rank != n.rank - 1 && t.rank != n.rank - 2)
					{
						max.child.add(t);
					} else
					{
						n.child.add(t);
					}
				}
			}
		}
	}

}
