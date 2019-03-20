//************************************
//* An abstract class defines hollow heap and relative methods
//************************************

import java.util.LinkedList;

public abstract class HollowHeap
{
	HeapNode min, max;		// The minimum or maximum node of this heap.
	LinkedList<HeapNode> root;		// Root list

	public HollowHeap()
	{
		min = null;
		max = null;
		root = new LinkedList<HeapNode>();
	}

	public abstract void add(HeapNode n);		// Add node to heap

	public abstract HeapNode deleteMin();		

	public abstract HeapNode deleteMax();

	protected void removeHollowRoot()
	{
		LinkedList<HeapNode> temp = new LinkedList<HeapNode>();
		HeapNode current;
		while (!root.isEmpty())
		{
			current = root.removeFirst();	//Remove the first node of root.
			if (!current.hollow)
			{
				temp.add(current);		//If node is not hollow, move it to the list temp.
			} else
			{
				if (!current.child.isEmpty())
				{
					root.addAll(current.child);		//If node is hollow, add all its children to list root.
				}
			}
		}
		root = temp;		//When root is all checked, root is empty now, make root point to temp.
	}

	public boolean linkMin()	//Rank all nodes in root list of a min heap  
	{
		if (root.isEmpty())
			return false;

		int s = 10;		//Our program has no chance to have a node whose rank is greater than 10.

		HeapNode[] temp = new HeapNode[s];	//This array is used to temporally store nodes. 
											//The index equals to node rank.
		HeapNode current;
		
		while (!root.isEmpty())
		{
			current = root.removeFirst();
			while (temp[current.rank] != null)	//This means there is another node has the same rank as this one.
			{
				HeapNode heap2 = temp[current.rank];	//heap2 and current have same rank value.
				temp[current.rank] = null;				//Combine them together to form a new subtree, 
				if (current.compareTo(heap2) < 0)		//and the "root" rank is 1 greater than their rank.
				{
					current.child.addFirst(heap2);
				} else
				{
					heap2.child.addFirst(current);
					current = heap2;
				}
				current.rank++;
			}
			temp[current.rank] = current;
		}

		for (int i = 0; i < s; i++)		//Add all ranked subtrees to list root.
		{
			if (temp[i] != null)
				root.add(temp[i]);
		}
		return true;
	}

	public void findMin()		//Find min node of list root.
	{
		if (root.size() == 0)
		{
			return;
		}

		min = root.getFirst();

		for (int i = 1; i < root.size(); i++)
		{
			if (min.size > root.get(i).size)
			{
				min = root.get(i);
			}
		}
	}

	public boolean linkMax()		//Rank all nodes in root list of a max heap 
	{								//The method is same as linkMin().
		if (root.isEmpty())
			return false;

		int s = 10;
		HeapNode[] temp = new HeapNode[s];		
		HeapNode current;
		
		while (!root.isEmpty())
		{
			current = root.removeFirst();
			while (temp[current.rank] != null)
			{
				HeapNode heap2 = temp[current.rank];
				temp[current.rank] = null;
				if (current.compareTo(heap2) > 0)
				{
					current.child.addFirst(heap2);
				} else
				{
					heap2.child.addFirst(current);
					current = heap2;
				}
				current.rank++;
			}
			temp[current.rank] = current;
		}
		for (int i = 0; i < s; i++)
		{
			if (temp[i] != null)
				root.add(temp[i]);
		}
		return true;
	}

	public void findMax()		//Find min node of list root.
	{
		if (root.size() == 0)
		{
			return;
		}
		
		max = root.get(0);

		for (int i = 1; i < root.size(); i++)
		{
			if (max.size < root.get(i).size)
			{
				max = root.get(i);
			}
		}
	}

}
