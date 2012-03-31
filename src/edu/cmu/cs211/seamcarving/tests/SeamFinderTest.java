/**
 * 
 */
package edu.cmu.cs211.seamcarving.tests;

import static org.junit.Assert.assertEquals;

import java.util.Random;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs211.seamcarving.GrayImage;
import edu.cmu.cs211.seamcarving.SeamFinder;

public class SeamFinderTest {

	protected SeamFinder finder_;

	int RANGE = 100; // scaled back for FD
	int FLOAT_MULTIPLIER = 100; // Used for range of random float Matrix
	int SEED = 123456; /* your favorite number! */
	private Random r = new Random(SEED);

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		finder_ = new SeamFinder();
	}

	/**
	 * Utility to check that given an image of costs, we get the resulting seam
	 * solution
	 * 
	 * @param costs
	 *            Array of costs representing the image in [row][column] format
	 * @param expectedSeam
	 *            Array of columns that should be selected in the seam, one for
	 *            each row.
	 */
	protected void testSeam(float[][] costs, int[] expectedSeam) {
		Vector<Integer> seam = finder_.findMinSeam(new GrayImage(costs));
		assertSeamEquals(seam, expectedSeam);
	}

	protected void assertSeamEquals(Vector<Integer> seam, int[] expected) {
		assertEquals(
				"The seam is the wrong length. Shold be the same as the number of rows. ",
				expected.length, seam.size());

		for (int i = 0; i < expected.length; ++i) {
			assertEquals("Wrong pixel chosen in the seam for row " + i + ": ",
					expected[i], seam.get(i).intValue());
		}
	}

	@Test
	public void verticalSeamTest() {

		// Testing when the best seam is going to be completely vertical in the
		// center of the image
		float[][] array = { { 1, 0, 1 }, { 2, 0, 2 }, { 1, 0, 1 }, { 3, 0, 3 } };

		testSeam(array, new int[] { 1, 1, 1, 1 });
	}

	/* ************************** BASIC TESTS ******************************** */
	@Test
	public void theoryExampleTest() {
		float[][] arr = { { 1, 4, 6, 1 }, { 4, 3, 2, 5 }, { 1, 1, 8, 3 },
				{ 2, 6, 3, 9 } };

		testSeam(arr, new int[] { 3, 2, 1, 0 });
	}

	/* ************************** EDGE CASES ********************************** */
	/**
	 * Tests empty array.
	 */
	@Test
	public void emptyArrayTest() {
		float[][] arr = new float[][] { {} };

		testSeam(arr, new int[] {});
	}

	/**
	 * Tests variations of single seam images.
	 */
	@Test
	public void testSingleSeams() {
		float[][] singleElement = new float[][] { { 1 } };
		float[][] singleSeam = new float[][] { { 1 }, { 2 }, { 3 }, { 4 } };

		testSeam(singleElement, new int[] { 0 });
		testSeam(singleSeam, new int[] { 0, 0, 0, 0 });
	}

	/* ************************* STRESS TESTS ********************************* */

	/**
	 * Tests a random large matrix with deterministic minSeam. Creates a random
	 * table of float values greater than 1. Chooses a random column to put in
	 * all zeros. This is deterministically the minimum seam, so it checks
	 * against an array full of that column's number.
	 */
	@Test
	public void stressTest() {

		float[][] randomImage = createBigRandomImage();

		int specialCol = r.nextInt(randomImage[0].length);

		// create a column of all zero that will be the minSeam
		for (int i = 0; i < randomImage.length; i++) {
			randomImage[i][specialCol] = 0;
		}

		// creates min seam that is a specific column
		int[] minSeam = new int[randomImage.length];
		for (int i = 0; i < minSeam.length; i++) {
			minSeam[i] = specialCol;
		}

		testSeam(randomImage, minSeam);

	}

	/**
	 * Generates a large table of float values from 0 (exclusive) to
	 * FLOAT_MULTIPLIER + 1 (exclusive).
	 * 
	 * Note: no zeros in the matrix
	 */
	private float[][] createBigRandomImage() {
		int height = r.nextInt(RANGE);
		int width = r.nextInt(RANGE);
		float[][] randomMatrix = new float[height][width];

		for (int i = 0; i < randomMatrix.length; i++) {
			for (int j = 0; j < randomMatrix[0].length; j++) {
				randomMatrix[i][j] = FLOAT_MULTIPLIER * r.nextFloat() + 1;
			}
		}
		return randomMatrix;
	}

}
