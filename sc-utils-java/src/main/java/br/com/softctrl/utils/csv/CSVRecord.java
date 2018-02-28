/**
 * 
 */
package br.com.softctrl.utils.csv;

import java.io.Serializable;
import java.util.Arrays;

/*
 * The MIT License (MIT)
 * Copyright (c) 2018 Carlos Timoshenko Rodrigues Lopes
 * http://www.0x09.com.br
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

/**
 * @author carlostimoshenkorodrigueslopes@gmail.com
 */
public final class CSVRecord implements Serializable {

	private static final long serialVersionUID = -7758506152770487938L;
	private final String[] fields;
	private final String line;

	/**
	 * 
	 * @param fields
	 * @param line
	 */
	public CSVRecord(final String[] fields, final String line) {
		this.fields = fields;
		this.line = line;
	}

	/**
	 * 
	 * @return
	 */
	public String[] getFields() {
		return fields;
	}

	/*
	 * 
	 */
	public String getLine() {
		return line;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Arrays.toString(fields);
	}

}
