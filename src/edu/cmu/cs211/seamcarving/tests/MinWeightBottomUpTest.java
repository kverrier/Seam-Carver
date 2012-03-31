package edu.cmu.cs211.seamcarving.tests;

import org.junit.Before;

import edu.cmu.cs211.seamcarving.minweight.MinWeightBottomUp;

/**
 * Runs the MinWeight JUnit test suite using the bottom up implementation.
 *
 * @author Kyle Verrier
 */
public class MinWeightBottomUpTest extends MinWeightTest {

	@Before
	public void setUp() {
		super.mw = new MinWeightBottomUp();
		NELEMENTS = 1000;
	}

}
