package br.com.softctrl.utils.java;

import java.util.Date;

import com.google.gson.annotations.Expose;

import br.com.softctrl.utils.json.GsonUtils;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class GSonUtilsTest extends TestCase {

	public static class Dummy {
		@Expose
		public byte vlr0;
		@Expose
		public boolean vlr1;
		@Expose
		public char vlr2;
		@Expose
		public double vlr3;
		@Expose
		public float vlr4;
		@Expose
		public int vlr5;
		@Expose
		public short vlr6;
		@Expose
		public Boolean vlr7;
		@Expose
		public Double vlr8;
		@Expose
		public Float vlr9;
		@Expose
		public Integer vlr10;
		@Expose
		public Short vlr11;
		@Expose
		public Date vlr12;
	}

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
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

	/**
	 * Rigourous Test :-)
	 */
	public void testGsonUtilsToJson() {
		final String espected = "{\"vlr0\":0,\"vlr1\":false,\"vlr2\":\"\u0000\",\"vlr3\":0.0,\"vlr4\":0.0,\"vlr5\":0,\"vlr6\":0,\"vlr7\":null,\"vlr8\":null,\"vlr9\":null,\"vlr10\":null,\"vlr11\":null,\"vlr12\":null}";
		final String received = GsonUtils.toJson(new Dummy());
		assertTrue(espected.equals(received));
	}

}
