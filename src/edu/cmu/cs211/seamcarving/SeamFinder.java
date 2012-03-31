package edu.cmu.cs211.seamcarving;

import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

/**
 * Class that is used to find the minimum energy vertical seam in an image
 *
 * @author Kyle Verrier
 */
public class SeamFinder {

	public SeamFinder() {

	}

	/**
	 * Finds the minimum vertical seam in the cost image. A seam is a connected
	 * line through the image.
	 * 
	 * @param costs
	 *            The cost of removing each pixel in the image
	 * @return A vector the same height as the image where each entry specifies,
	 *         for a single row, which column to remove. So, if vector[5] = 3,
	 *         then the minimum seam goes through the pixel at row 5 and column
	 *         3.
	 */
	public Vector<Integer> findMinSeam(GrayImage costs) {
		if (costs.getWidth() == 0)
			return new Vector<Integer>();

		// creates dynamic programming table
		float[][] dpTable = createDpTable(costs);

		int[] minSeamArray = new int[costs.getHeight()];

		// finds the column of the last row of the minimum seam
		int minSeamEnd = findMinSeamIndex(dpTable);
		// sets the last element of the minSeam array
		minSeamArray[costs.getHeight() - 1] = minSeamEnd;

		// Starting from the last row of the DP table, the minSeam is found
		int nextJ = minSeamEnd;

		for (int i = dpTable.length - 1; i > 0; i--) {
			minSeamArray[i] = nextJ;

			nextJ = findNextJ(dpTable, i, nextJ);
		}

		minSeamArray[0] = nextJ; // sets starting column

		Vector<Integer> minSeam = new Vector<Integer>();

		// creates minSeam vector seam by going through minSeamArray
		for (int j : minSeamArray)
			minSeam.add(j);

		return minSeam;

	}

	/**
	 * Calculates the values of the DP table.
	 */
	private float[][] createDpTable(GrayImage costs) {

		float[][] dpTable = new float[costs.getHeight()][costs.getWidth()];

		for (int i = 0; i < dpTable.length; i++) {
			for (int j = 0; j < dpTable[0].length; j++) {
				dpTable[i][j] = calculateCell(costs, dpTable, i, j);
			}
		}

		return dpTable;
	}

	/**
	 * Calculates the minimum of the possible previous seam to fill in a cell DP
	 * table. Takes previous seam weight and then adds the value at p(i,j).
	 */
	private float calculateCell(GrayImage image, float[][] table, int i, int j) {
		// initlizes first row
		if (i == 0) {
			return image.get(i, j);
		}

		// checks for single col image
		if (j == 0 && j == table[0].length - 1) {
			return image.get(i, j) + table[i - 1][j];
		}

		// if on the left edge does not consider going to the left
		if (j == 0) {
			return image.get(i, j)
					+ Math.min(table[i - 1][j], table[i - 1][j + 1]);
		}

		// if on the right edge does not consider going to the right
		if (j == table[0].length - 1) {
			return image.get(i, j)
					+ Math.min(table[i - 1][j], table[i - 1][j - 1]);
		}

		// otherwise looks at the left, center, and right possibilities as mins
		return image.get(i, j)
				+ Collections.min(Arrays.asList(table[i - 1][j - 1],
						table[i - 1][j], table[i - 1][j + 1]));

	}

	/**
	 * Performs a linear search on the bottom row of DP Table to determine
	 * smallest seam.
	 */
	private int findMinSeamIndex(float[][] table) {
		int lastRow = table.length - 1;

		int minIndex = 0;
		float minValue = table[lastRow][0];

		for (int j = 1; j < table[0].length; j++) {
			if (table[lastRow][j] < minValue) {
				minIndex = j;
				minValue = table[lastRow][j];
			}
		}

		return minIndex;
	}

	/**
	 * From a certain (i,j) in the DP table, determines the previous smallest
	 * path and returns which column it is in.
	 */
	private int findNextJ(float[][] table, int i, int j) {
		// Checks for DP table of width 1
		if (table[0].length == 1)
			return 0;

		// if at the left border only considers above and above to the right
		if (j == 0) {
			if (table[i - 1][0] < table[i - 1][1])
				return 0;
			else
				return 1;
		}

		// if at the right border only considers above and above to the left
		if (j == table[0].length - 1) {
			if (table[i - 1][table[0].length - 2] < table[i - 1][table[0].length - 1])
				return table[0].length - 2;
			else
				return table[0].length - 1;
		}

		// otherwise looks at above left, center, and right
		return scanThreeAbove(table, i, j);

	}

	/**
	 * Determines which column leads to smallest DP table cell for a certain
	 * (i,j) and returns the column
	 */
	private int scanThreeAbove(float[][] table, int i, int j) {
		int minIndex = j - 1;
		float minValue = table[i - 1][j - 1];

		for (int k = 0; k < 3; k++) {
			if (table[i - 1][j - 1 + k] < minValue) {
				minValue = table[i - 1][j - 1 + k];
				minIndex = j - 1 + k;
			}
		}

		return minIndex;
	}
}
