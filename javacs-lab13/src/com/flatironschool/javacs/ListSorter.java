/**
 *
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 *
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {

		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 *
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 *
	 * Returns a list that might be new.
	 *
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator)
	{
		//if list size is one return list- base case
		if (list.size() <= 1)
		{
			return list;
		}

		//split the list
    List<T> list1 = mergeSort(new LinkedList<T>(list.subList(0, list.size()/2)), comparator);
		List<T> list2 = mergeSort(new LinkedList<T>(list.subList(list.size()/2, list.size())),comparator);

		//sort the halfs
		insertionSort(list1, comparator);
		insertionSort(list2, comparator);

		//merge Lists
		List<T> result = new LinkedList();
		int count = 0;
		while(count < list1.size() + list2.size())
		{
			while(!list1.isEmpty())
			{
				if(list2.isEmpty())
				{
					count += list1.size();
					result.addAll(list1);
					break;
				}
				if(comparator.compare(list1.get(0),list2.get(0)) <= 0)
				{
					result.add(list1.get(0));
					list1.remove(0);
					count++;
				}
				else
				{
					result.add(list2.get(0));
					list2.remove(0);
					count++;
				}
			}
			result.addAll(list2);
			count += list2.size();
		}
		return result;
	}

	/**
	 * Sorts a list using a Comparator object.
	 *
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator)
	{
		PriorityQueue<T> queue = new PriorityQueue<T> (list.size(), comparator);
		for(T el : list)
		{
			queue.offer(el);
		}
		list.clear();
		while(queue.peek() != null)
		{
			list.add(queue.poll());
		}
	}


	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 *
	 * @param k
	 * @param list
	 * @param comparator
	 * @return
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator)
	{
		PriorityQueue<T> queue = new PriorityQueue<T> (k, comparator);
		List<T> lst = new LinkedList<T>(list);

		for(int i = 0; i<k; i++)
		{
			queue.offer(lst.get(0));
			lst.remove(0);
		}
		while(!lst.isEmpty())
		{
			if(comparator.compare(lst.get(0),queue.peek()) > 0)
			{
				queue.poll();
				queue.offer(lst.get(0));
				lst.remove(0);
			}
			else
			{
				lst.remove(0);
			}
		}
		while(queue.peek() != null)
		{
			lst.add(queue.poll());
		}
		return lst;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));

		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};

		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
