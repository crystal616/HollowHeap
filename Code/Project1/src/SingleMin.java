//************************************************
//* A single root min heap. All needed methods are defined, or inherited from super classes or/and modified.
//************************************************

import java.util.LinkedList;

public class SingleMin extends SingleRoot
{

	public void add(HeapNode n)		//Add a node to heap, compare it with current min, 
	{								//and decide which is the new min.
		if (min == null)
		{
			min = n;
		} else
		{
			if (min.size > n.size)
			{
				n.child.addAll(min.child);
				min.child.clear();
				n.child.addFirst(min);
				min = n;
			} else
			{
				min.child.addFirst(n);
			}
		}
	}

	public HeapNode deleteNext()	//Delete the highest priority in this heap
	{
		return deleteMin();
	}

	public void delete(HeapNode n)	//Delete a given node
	{
		n.hollow = true;	//Make it hollow
		if (min == n)
		{
			deleteNext();
		} else
		{
			if (n.rank > 2)		//If its rank is greater than 2, move some children and meld with min.
			{
				for (int i = 0; i < n.child.size(); i++)
				{
					LinkedList<HeapNode> childNew = n.child;
					n.child = new LinkedList<HeapNode>();
					while (!childNew.isEmpty())
					{
						HeapNode t = childNew.removeFirst();
						if (t.rank != n.rank - 1 && t.rank != n.rank - 2)
						{
							min.child.add(t);
						} else
						{
							n.child.add(t);
						}
					}
				}
			}
		}
	}
}
