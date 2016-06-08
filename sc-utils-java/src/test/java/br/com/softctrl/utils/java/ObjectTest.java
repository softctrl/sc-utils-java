package br.com.softctrl.utils.java;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ObjectTest extends TestCase {

	/**
	 * Create the test case
	 * @param testName
	 * name of the test case
	 */
	public ObjectTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(ObjectTest.class);
	}
	private static final String VALID_JSON_ARRAY = "[{\"vlr0\":0,\"vlr1\":false,\"vlr2\":\"\u0000\",\"vlr3\":0.0,\"vlr4\":0.0,\"vlr5\":0,\"vlr6\":0,\"vlr7\":null,\"vlr8\":null,\"vlr9\":null,\"vlr10\":null,\"vlr11\":null,\"vlr12\":null}, {\"vlr0\":0,\"vlr1\":false,\"vlr2\":\"\u0000\",\"vlr3\":0.0,\"vlr4\":0.0,\"vlr5\":0,\"vlr6\":0,\"vlr7\":null,\"vlr8\":null,\"vlr9\":null,\"vlr10\":null,\"vlr11\":null,\"vlr12\":null}]";

	public void testValidateJsonArray() {

		Dummy[] dummies = br.com.softctrl.utils.Object.fromJsonArray(VALID_JSON_ARRAY, Dummy[].class);
		assertTrue(dummies != null);
		assertTrue(dummies.length  == 2);
		assertTrue(dummies[0].equals(dummies[1]));

	}

}
