package edu.cmu.cs211.seamcarving.minweight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The top-down implementation of MinWeight. To receive credit for this portion
 * of the lab, your implementation must be a valid, top-down dynamic programming
 * solution. Note that this applies for both minWeight() and minWeightList().
 * 
 * @author kverrier
 * 
 */
public class MinWeightTopDown extends MinWeight {

	@Override
	public int minWeight(int[] arr) {
		if (arr.length == 0)
			return 0;

		return minWeight(arr, new HashMap<Integer, Integer>(), arr.length - 1);
	}

	/**
	 * Recursive helper method used to determine minWeight for smaller
	 * subproblems for DP that utilizes memo-ization. Changes HashTable.
	 */
	private int minWeight(int[] arr, Map<Integer, Integer> hashTable,
			int lastIndex) {

		// checks to see if the subproblem has been memo-ized
		if (hashTable.containsKey(lastIndex))
			return hashTable.get(lastIndex);

		/* BASE CASES */
		if (lastIndex == 0) {
			int mw0 = Math.min(0, arr[0]); // checks for possible negative val
			hashTable.put(0, mw0); // memo-izes
			return mw0;
		}

		if (lastIndex == 1) {
			int mw1 = Math.min(minWeight(arr, hashTable, 0) + arr[1], arr[0]);
			hashTable.put(1, mw1); // memo-izes
			return mw1;
		}

		/* RECURSIVE FUNCTION */
		int mw = Math.min(minWeight(arr, hashTable, lastIndex - 2)
				+ arr[lastIndex - 1], minWeight(arr, hashTable, lastIndex - 1)
				+ arr[lastIndex]);

		hashTable.put(lastIndex, mw); // memo-izes

		return mw;
	}

	@Override
	public List<Integer> minWeightList(int[] arr) {
		// for empty array returns an empty list
		if (arr.length == 0)
			return new ArrayList<Integer>();

		// creates hashtable
		Map<Integer, Integer> hashTable = generateHashTable(arr);

		LinkedList<Integer> minWeightList = new LinkedList<Integer>();

		// Works backwards from the last mw to find last added element
		for (int i = hashTable.size() - 1; i >= 0; i--) {
			// if the element before was added, arr[0] is only added if neg or 0
			if (i == 0) {
				if (arr[0] <= 0)
					minWeightList.addFirst(arr[0]);
			} else {
				if (hashTable.get(i) - arr[i] == hashTable.get(i - 1)) {
					minWeightList.addFirst(arr[i]);
				} else {
					minWeightList.addFirst(arr[i - 1]);
					i--;
				}
			}
		}

		return minWeightList;
	}

	/**
	 * Returns HashTable used for memo-ization for DP TopDown.
	 */
	private Map<Integer, Integer> generateHashTable(int[] arr) {

		Map<Integer, Integer> hashTable = new HashMap<Integer, Integer>();

		minWeight(arr, hashTable, arr.length - 1);

		return hashTable;
	}
}
