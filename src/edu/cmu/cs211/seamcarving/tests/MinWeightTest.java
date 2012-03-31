package edu.cmu.cs211.seamcarving.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import edu.cmu.cs211.seamcarving.minweight.MinWeight;

/**
 * Abstract class for testing a generic MinWeight implementation. We have
 * provided source that extends this class and automatically inherits all of
 * these tests for each implementation. That way, any tests you add here will
 * automatically show up for both implementations.
 * 
 * @author Ankur Goyal
 * 
 */
public abstract class MinWeightTest {
	protected MinWeight mw;

	/* These could come in handy for a stress test... */
	int NELEMENTS = 100000;
	int RANGE = 10000;
	int SEED = 123456; /* your favorite number! */
	private Random r = new Random(SEED);

	/**
	 * This class makes it convenient to test specific sequences. You can track
	 * the original sequence (arr), the minimum weight (weight), and the minimum
	 * subsequence (subsequence).
	 * 
	 * @author Ankur Goyal
	 */
	private class TestTuple {
		public int[] arr;
		public int weight;
		public List<Integer> subsequence;

		/**
		 * Constructor for a TestTuple
		 * 
		 * @param arr
		 *            original sequence
		 * @param weight
		 *            minimum weight
		 * @param subsequence
		 *            minimum weight subsequence
		 */
		public TestTuple(int[] arr, int weight, List<Integer> subsequence) {
			this.arr = arr;
			this.weight = weight;
			this.subsequence = subsequence;
		}
	}

	/**
	 * Runs a list of TestTuples against your implementation.
	 * 
	 * @param tests
	 *            List of TestTuples to run
	 * @param testSubsequence
	 *            True if you want it to test the subsequence, false otherwise
	 */
	public void runTestTuples(List<TestTuple> tests, boolean testSubsequence) {
		for (int i = 0; i < tests.size(); i++) {
			TestTuple t = tests.get(i);
			assertEquals("testing weight of " + Arrays.toString(t.arr),
					t.weight, mw.minWeight(t.arr));
			if (testSubsequence)
				assertEquals(
						"testing subsequence of " + Arrays.toString(t.arr),
						t.subsequence, mw.minWeightList(t.arr));
		}
	}

	/**
	 * Basic sanity test. This is a good place to add some more positive cases.
	 * Note that this test makes use of the Arrays.asList() method. This method
	 * takes a comma separated list of values (effectively an "array") and
	 * converts them into a list. It's super convenient for creating lists by
	 * hand.
	 */
	@Test(timeout = 1000)
	public void sanityTest() {
		List<TestTuple> tests = Arrays.asList(new TestTuple(new int[] { 1, 2,
				3, 4 }, 4, Arrays.asList(1, 3)));
		runTestTuples(tests, true);
	}


	/* **************************** BASIC TESTS ************************** */

	/**
	 * Theory Homework Examples. Useful for debugging.
   *
   * @author Kyle Verrier
	 */
	@Test(timeout = 1000)
	public void theoryExampleTest() {
		TestTuple example1 = new TestTuple(new int[] { 2, 1, -2 }, -1,
				Arrays.asList(1, -2));
		TestTuple example2a = new TestTuple(new int[] { 3, 8, 2, 4, 6 }, 9,
				Arrays.asList(3, 2, 4));
		TestTuple example2b = new TestTuple(new int[] { -1, -2, -3, -4 }, -10,
				Arrays.asList(-1, -2, -3, -4));

		List<TestTuple> tests = Arrays.asList(example1, example2a, example2b);

		runTestTuples(tests, true);

	}

	/**
	 * A bunch of random small examples with messing around with negatives and
	 * ordering
   *
   * @author Kyle Verrier
	 */
	@Test
	public void basicExamples() {
		TestTuple test1 = new TestTuple(new int[] { 1, -2, 3, -4 }, -6,
				Arrays.asList(-2, -4));
		TestTuple test2 = new TestTuple(new int[] { -1, 2, 3, -4 }, -3,
				Arrays.asList(-1, 2, -4));
		TestTuple test3 = new TestTuple(new int[] { 1, 2, 3, -4 }, -2,
				Arrays.asList(2, -4));
		TestTuple test4 = new TestTuple(new int[] { -1, 2, 3, 4 }, 2,
				Arrays.asList(-1, 3));
		TestTuple test5 = new TestTuple(new int[] { 0, 1, -2, 3, -4 }, -6,
				Arrays.asList(0, -2, -4));
		TestTuple test6 = new TestTuple(new int[] { 0, -1, 2, 3, -4 }, -3,
				Arrays.asList(0, -1, 2, -4));
		TestTuple test7 = new TestTuple(new int[] { 2, -1, 4, 4, 0 }, 3,
				Arrays.asList(-1, 4, 0));
		TestTuple test8 = new TestTuple(new int[] { -2, -1, 4, 4, 0 }, 1,
				Arrays.asList(-2, -1, 4, 0));

		List<TestTuple> tests = Arrays.asList(test1, test2, test3, test4,
				test5, test6, test7, test8);

		runTestTuples(tests, true);
	}

	/* **************************** EDGE CASES ************************** */

	/**
	 * Tests empty array
   *
   * @author Kyle Verrier
	 */
	@Test
	public void testEmptyArray() {
		List<TestTuple> tests = Arrays.asList(new TestTuple(new int[] {}, 0,
				new ArrayList<Integer>()));
		runTestTuples(tests, true);
	}

	/**
	 * Tests null array
   *
   * @author Kyle Verrier
	 */
	@Test(expected = NullPointerException.class)
	public void testNullArray() {
		int[] nullIntArray = null;
		mw.minWeight(nullIntArray);
		mw.minWeightList(nullIntArray);
	}

	/**
	 * Tests array with random null elements
   *
   * @author Kyle Verrier
	 */
	@Test(expected = NullPointerException.class)
	public void testArrayWithNullElements() {
		int[] testArrayWithNullElements = new int[10];

		for (int i = 0; i < testArrayWithNullElements.length; i++) {
			if (r.nextBoolean())
				testArrayWithNullElements[i] = r.nextInt(RANGE);
			else
				testArrayWithNullElements[i] = (Integer) null;

		}

		mw.minWeight(testArrayWithNullElements);
		mw.minWeightList(testArrayWithNullElements);
	}

	/**
	 * Test arrays with single elements
   *
   * @author Kyle Verrier
	 */
	@Test
	public void testSingleElements() {
		TestTuple singlePositiveTest = new TestTuple(new int[] { 1 }, 0,
				new ArrayList<Integer>());
		TestTuple singleNegativeTest = new TestTuple(new int[] { -1 }, -1,
				Arrays.asList(-1));
		TestTuple singleZeroTest = new TestTuple(new int[] { 0 }, 0,
				Arrays.asList(0));

		List<TestTuple> tests = Arrays.asList(singlePositiveTest,
				singleNegativeTest, singleZeroTest);

		runTestTuples(tests, true);
	}

	/* **************************** STRESS TESTS ************************** */

	/**
	 * Test a lot of negative numbers and zeros which will always be included in
	 * the minWeight and List
   *
   * @author Kyle Verrier
	 */
	@Test(timeout = 1000)
	public void testNegativesAndZeros() {
		List<Integer> expMinWeightList = new ArrayList<Integer>();
		int[] arr = new int[NELEMENTS];
		int mw = 0;

		// Construct list of 0's and negative numbers
		for (int i = 0; i < NELEMENTS; i++) {
			if (r.nextBoolean()) {
				expMinWeightList.add(0);
				arr[i] = 0;
			} else {
				int newNegativeNumber = -1 * r.nextInt(RANGE);
				arr[i] = newNegativeNumber;
				expMinWeightList.add(newNegativeNumber);
				mw += newNegativeNumber;
			}
		}

		List<TestTuple> tests = Arrays.asList(new TestTuple(arr, mw,
				expMinWeightList));
		runTestTuples(tests, true);

	}

	/**
	 * Stress Test. Calculates the minimum weight and the sequence of numbers
	 * and compares the sum of the list to the calculated weight. This does not
	 * really deterministically test that the string is right but the
	 * probability for a wrong result is low enough that it is still useful.
   *
   * @author Kyle Verrier
	 */
	@Test
	public void stressTest() {
		int[] arr = createRandomIntArray();

		int minWeight = mw.minWeight(arr);
		List<Integer> mwList = mw.minWeightList(arr);
		int sumOfMwList = sumUpSetOfIntegers(mwList);

		assertEquals(
				"Sum of minWeight list ints is not the same as the calculated minimum weight",
				minWeight, sumOfMwList);
	}

	// Generates an array of random ints
	private int[] createRandomIntArray() {
		int[] arr = new int[NELEMENTS];

		for (int i = 0; i < arr.length; i++) {
			arr[i] = r.nextInt(RANGE);
		}

		return arr;
	}

	// sums of a list of integers
	private int sumUpSetOfIntegers(List<Integer> intList) {
		int sum = 0;

		for (Integer i : intList) {
			sum += i;
		}

		return sum;

	}

}
