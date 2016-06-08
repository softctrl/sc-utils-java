package br.com.softctrl.utils.java;

import java.util.HashMap;
import java.util.Map.Entry;

import br.com.softctrl.utils.json.GsonUtils;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class GSonUtilsTest extends TestCase {

	/**
	 * Create the test case
	 * @param testName
	 * name of the test case
	 */
	public GSonUtilsTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(GSonUtilsTest.class);
	}

	private static final String VALID_JSON = "{\"vlr0\":0,\"vlr1\":false,\"vlr2\":\"\u0000\",\"vlr3\":0.0,\"vlr4\":0.0,\"vlr5\":0,\"vlr6\":0,\"vlr7\":null,\"vlr8\":null,\"vlr9\":null,\"vlr10\":null,\"vlr11\":null,\"vlr12\":null}";

	private static final HashMap<String, Boolean> JSONS = new HashMap<String, Boolean>();

	static {
		JSONS.put(VALID_JSON, true);
		JSONS.put("[]", true);
		JSONS.put("{}", true);
		JSONS.put("[{},{}]", true);
		JSONS.put("[}", false);
		JSONS.put("{]", false);
		JSONS.put("[{},{            ]", false);
		JSONS.put("[          {},{}.]", false);
		JSONS.put("[},{}]", false);
		JSONS.put("[.{},{}]", false);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testGsonUtilsToJson() {
		final String espected = VALID_JSON;
		final String received = GsonUtils.toJson(new Dummy());
		Dummy dummy1 = GsonUtils.fromJson(espected, Dummy.class);
		Dummy dummy2 = GsonUtils.fromJson(received, Dummy.class);
		System.out.println("espected:[" + espected + "]");
		System.out.println("received" + received + "]");
		assertTrue(dummy1.equals(dummy2));
	}

	/**
	 * 
	 */
	public void testValidateJson() {
		for (Entry<String, Boolean> item : JSONS.entrySet()) {
			System.out.println(String.format("%s -> %s", item.getValue(), item.getKey()));
			assertTrue(item.getValue().equals(GsonUtils.mayBeValidJson(item.getKey())));
		}
	}

}
