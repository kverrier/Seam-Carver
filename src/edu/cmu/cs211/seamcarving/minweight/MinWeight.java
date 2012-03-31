package edu.cmu.cs211.seamcarving.minweight;

import java.util.List;

/**
 * The abstract class representing a Minimum Weight Subsequence computer.
 * This class should be stateless, so a single MinWeight object should
 * work on multiple sequences without tracking state.
 *  
 * @author Ankur Goyal
 *
 */
public abstract class MinWeight {
	
	/**
	 * Computes the Minimum Weight of the sequence defined in the array arr.
	 * Refer to the theory for the precise definition of this value: W(S'_n).
	 * 
	 * @param arr subsequence whose minweight to compute
	 * @return computed minimum weight
	 * @throws NullPointerException if arr is null
	 */
	public abstract int minWeight(int[] arr);
	
	/**
	 * Computes the subsequence corresponding to the minimum sequence defined
	 * in arr. Refer to the theory for the precise definition of this value: S'_n.
	 * 
	 * The returned subsequence is a list for simplicity.
	 * 
	 * Note that although it doesn't affect the overall weight, you should keep 0's
	 * in the list for simplicity. This means that if the sequence is [0,-1, -1],
	 * both [-1, 1] and [0, -1, -1] are valid subsequences, but your algorithm
	 * should return [0, -1, -1]. As a hint, this should help simplify your 
	 * implementation.
	 * 
	 * @param arr subsequence whose minimum subsequence to compute
	 * @return list corresponding to the minimum subsequence
	 * @throws NullPointerException if arr is null
	 */
	public abstract List<Integer> minWeightList(int[] arr);
}
