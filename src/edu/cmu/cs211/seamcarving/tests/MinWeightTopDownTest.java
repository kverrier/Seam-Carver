package edu.cmu.cs211.seamcarving.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs211.seamcarving.minweight.*;

public class MinWeightTopDownTest extends MinWeightTest {

	@Before
	public void setUp() {
		super.mw = new MinWeightTopDown();
		NELEMENTS = 1000;
	}

	@Test
	public void theoryExample() {
		int[] ex = { 3, 8, 2, 4, 6 };

		MinWeightTopDown mw = new MinWeightTopDown();

		int minWeight = mw.minWeight(ex);

		assertEquals(9, minWeight);
	}
}
