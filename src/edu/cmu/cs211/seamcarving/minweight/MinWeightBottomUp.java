package edu.cmu.cs211.seamcarving.minweight;

import java.util.LinkedList;
import java.util.List;

/**
 * The bottom-up implementation of MinWeight. To receive credit for this portion
 * of the lab, your implementation must be a valid, bottom-up dynamic
 * programming solution.
 * 
 * @author Kyle Verrier
 * 
 */
public class MinWeightBottomUp extends MinWeight {

	@Override
	public int minWeight(int[] arr) {
		if (arr.length == 0)
			return 0;

		int[] minWeights = generateMinWeightArray(arr);

		return minWeights[minWeights.length - 1];
	}

	@Override
	public List<Integer> minWeightList(int[] arr) {
		int[] minWeights = generateMinWeightArray(arr);

		LinkedList<Integer> minWeightList = new LinkedList<Integer>();

		// Works backwards from the last mw to find last added element
		for (int i = minWeights.length - 1; i >= 0; i--) {
			// if the element before was added, arr[0] is only added if neg or 0
			if (i == 0) {
				if (arr[0] <= 0)
					minWeightList.addFirst(arr[0]);
			} else {
				if (minWeights[i] - arr[i] == minWeights[i - 1]) {
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
	 * Returns an array with each array slot holding the mw for the smaller
	 * subproblems
	 */
	private int[] generateMinWeightArray(int[] arr) {
		// int array used to store minimum weights for smaller subproblems which
		// will be fill out from a bottom up fashion
		int[] minWeights = new int[arr.length];

		/* BASE CASES */
		if (arr.length > 0)
			minWeights[0] = Math.min(arr[0], 0); // Checks for neg value
		if (arr.length > 1)
			minWeights[1] = Math.min(arr[0], minWeights[0] + arr[1]);

		/* MATHEMATICAL EQUATION */
		if (arr.length > 2)
			for (int i = 2; i < minWeights.length; i++) {
				minWeights[i] = Math.min(minWeights[i - 2] + arr[i - 1],
						minWeights[i - 1] + arr[i]);
			}

		return minWeights;
	}
}
