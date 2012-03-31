package edu.cmu.cs211.seamcarving.tests;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs211.seamcarving.GradientFilter;
import edu.cmu.cs211.seamcarving.GrayImage;

/**
 * Testing Suite for the Gradient Filter.
 *
 * @author Kyle Verrier
 */
public class GradientFilterTest {
	protected GradientFilter filter_;
	int RANGE = 100; // scaled back for FD
	int SEED = 123456; /* your favorite number! */
	private Random r = new Random(SEED);

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		filter_ = new GradientFilter();
	}

	@Test
	public void squareImageTest() {
		// Use a 3x3 image to test the nominal case
		float[][] inputVals = { { 1.0f, 2.0f, 3.0f }, { 6.0f, 4.0f, 2.0f },
				{ 6.0f, 8.0f, 0.0f } };
		float[][] expectedSqDiff = { { 26.0f, 5.0f, 10.0f },
				{ 4.0f, 20.0f, 8.0f }, { 40.0f, 128.0f, 0.0f } };

		GrayImage filtered = filter_.filter(new GrayImage(inputVals));
		assertEquals(filtered.getHeight(), 3);
		assertEquals(filtered.getWidth(), 3);

		for (int row = 0; row < 3; ++row) {
			for (int col = 0; col < 3; ++col) {
				assertEquals("Unexpected entry at (" + row + "," + col + "): ",
						filtered.get(row, col),
						Math.sqrt(expectedSqDiff[row][col]), 1e-6);
			}
		}
	}

  /* *****************************  TESTS ******************************** */

	/* UTILITY STUFF */

	/**
	 * Tests an input GrayImage table and calculated expected sum of squared
	 * difference based on the rules of the gradient
	 */
	private void compareTwoTable(float[][] inputVals, float[][] expectedSqDiff) {
		GrayImage filtered = filter_.filter(new GrayImage(inputVals));
		assertEquals(filtered.getHeight(), expectedSqDiff.length);
		assertEquals(filtered.getWidth(), expectedSqDiff[0].length);

		for (int row = 0; row < expectedSqDiff.length; ++row) {
			for (int col = 0; col < expectedSqDiff[0].length; ++col) {
				assertEquals("Unexpected entry at (" + row + "," + col + "): ",
						filtered.get(row, col),
						Math.sqrt(expectedSqDiff[row][col]), 1e-6);
			}
		}
	}

	/**
	 * Quick sanity test to make sure utility function is working for test
	 * example.
	 */
	@Test
	public void testCompareTwoTables() {
		float[][] inputVals = { { 1.0f, 2.0f, 3.0f }, { 6.0f, 4.0f, 2.0f },
				{ 6.0f, 8.0f, 0.0f } };
		float[][] expectedSqDiff = { { 26.0f, 5.0f, 10.0f },
				{ 4.0f, 20.0f, 8.0f }, { 40.0f, 128.0f, 0.0f } };

		compareTwoTable(inputVals, expectedSqDiff);
	}

	/* ************************ BASIC TESTS *********************************** */

	/*
	 * Here are some small examples to test basic functionality
	 */
	@Test
	public void testExample1() {
		float[][] inputVals = { { 1.0f, 0.0f }, { 0.0f, 1.0f } };
		float[][] expectedSqDiff = { { 2.0f, 1.0f }, { 1.0f, 2.0f } };

		compareTwoTable(inputVals, expectedSqDiff);
	}

	@Test
	public void testExample2() {
		float[][] inputVals = { { 1.0f, 0.0f } };
		float[][] expectedSqDiff = { { 2.0f, 0.0f } };

		compareTwoTable(inputVals, expectedSqDiff);
	}

	@Test
	public void testExample3() {
		float[][] inputVals = { { 1.0f }, { 0.0f } };
		float[][] expectedSqDiff = { { 2.0f }, { 0.0f } };

		compareTwoTable(inputVals, expectedSqDiff);
	}

	@Test
	public void testExample4() {
		float[][] inputVals = { { 1.0f, 5.0f, 0.0f }, { 4.0f, 7.0f, 8.0f } };
		float[][] expectedSqDiff = { { 25.0f, 29.0f, 64.0f },
				{ 25.0f, 50.0f, 128.0f } };

		compareTwoTable(inputVals, expectedSqDiff);
	}

	/* ************************ EDGE CASES *********************************** */

	/**
	 * Tests empty image.
	 */
	@Test
	public void emptyTableTest() {
		float[][] inputVals = { {} };
		float[][] expectedSqDiff = { {} };

		compareTwoTable(inputVals, expectedSqDiff);
	}

	/**
	 * Tests for single element
	 */
	@Test
	public void singleElementTest() {
		float[][] inputVals = { { 1.0f } };
		float[][] expectedSqDiff = { { 2.0f } };

		compareTwoTable(inputVals, expectedSqDiff);
	}

	/**
	 * Tests for an image that is a single column.
	 */
	@Test
	public void singleColTest() {
		float[][] inputVals = { { 1.0f }, { 2.0f }, { 1.0f } };
		float[][] expectedSqDiff = { { 2.0f }, { 5.0f }, { 2.0f } };

		compareTwoTable(inputVals, expectedSqDiff);
	}

	/**
	 * Tests for an image that is a single row.
	 */
	@Test
	public void singleRowTest() {
		float[][] inputVals = { { 1.0f, 2.0f, 1.0f } };
		float[][] expectedSqDiff = { { 2.0f, 5.0f, 2.0f } };

		compareTwoTable(inputVals, expectedSqDiff);
	}

	/* ************************ STRESS TESTs *********************************** */

	/**
	 * Stress test using a matrix full of all ones.
	 */
	@Test
	public void stressTestWithAllOnes() {
		float[][] onesMatrix = generateOnesMatrix();

		float[][] expectedSqDiff = new float[onesMatrix.length][onesMatrix[0].length];

		for (int i = 0; i < expectedSqDiff.length - 1; i++) {
			expectedSqDiff[i][expectedSqDiff[0].length - 1] = 1;
		}

		for (int j = 0; j < expectedSqDiff[0].length - 1; j++) {
			expectedSqDiff[expectedSqDiff.length - 1][j] = 1;
		}

		expectedSqDiff[expectedSqDiff.length - 1][expectedSqDiff[0].length - 1] = 2;

		compareTwoTable(onesMatrix, expectedSqDiff);
	}

	/**
	 * Stress test using a matrix of alternating 1's and 0's. Also assumes the
	 * dimensions are odd for convenience of computed solution.
	 */
	@Test
	public void stressTestWithChecker() {
		float[][] checkerMatrix = generateBinaryCheckerBoardMatrix();
		float[][] expectedSqDiff = new float[checkerMatrix.length][checkerMatrix[0].length];

		// Solution is all even rows are full of 2's
		// Odd Rows full of 2s except last which is 1
		// And last row alternates between 2 and 1
		float[] evenRow = new float[expectedSqDiff[0].length];

		for (int j = 0; j < evenRow.length; j++) {
			evenRow[j] = 2;
		}

		// Sets up odd rows which is 2s and last is 1
		float[] oddRow = evenRow.clone();

		oddRow[oddRow.length - 1] = 1;

		// Sets of last row which alternates between 2 and 1
		float[] lastRow = new float[expectedSqDiff[0].length];

		for (int j = 0; j < lastRow.length; j++) {
			if (j % 2 == 0)
				lastRow[j] = 2;
			else
				lastRow[j] = 1;
		}

		// Creates expectedSqDiff Matrix
		for (int i = 0; i < expectedSqDiff.length - 1; i++) {
			if (i % 2 == 0)
				expectedSqDiff[i] = evenRow;
			else
				expectedSqDiff[i] = oddRow;
		}

		expectedSqDiff[expectedSqDiff.length - 1] = lastRow;

		compareTwoTable(checkerMatrix, expectedSqDiff);

	}

	// Creates an alternating checkerboard pattern of 0s and 1s
	private float[][] generateBinaryCheckerBoardMatrix() {
		int height = (r.nextInt(RANGE / 2) * 2) + 1; // always odd
		int width = (r.nextInt(RANGE / 2) * 2) + 1; // always odd

		float[][] matrix = new float[height][width];

		float[] evenRow = new float[width];
		float[] oddRow = new float[width];

		for (int j = 0; j < width; j++) {
			if (j % 2 == 0) {
				evenRow[j] = 1.0f;
				oddRow[j] = 0.0f;
			} else {
				evenRow[j] = 0.0f;
				oddRow[j] = 1.0f;
			}
		}

		for (int i = 0; i < height; i++) {
			if (i % 2 == 0)
				matrix[i] = evenRow;
			else
				matrix[i] = oddRow;
		}

		return matrix;
	}

	// Creates a matrix full of all ones
	private float[][] generateOnesMatrix() {
		int height = r.nextInt(RANGE);
		int width = r.nextInt(RANGE);

		float[][] onesMatrix = new float[height][width];

		for (int i = 0; i < onesMatrix.length; i++) {
			for (int j = 0; j < onesMatrix[0].length; j++) {
				onesMatrix[i][j] = 1;
			}
		}

		return onesMatrix;
	}

}
