/**
 * 
 */
package br.com.softctrl.utils.csv;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.softctrl.utils.IAction;
import br.com.softctrl.utils.Objects;

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
public class CSVReader implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3065986146346734650L;

	private static final char NEWLINE = '\n';
	private static final int UNDEFINED = 0x00;
	private static final int OPENED_QUOTE = 0x11;
	private static final int CLOSED_QUOTE = 0x22;

	private final List<String> row;
	private final Reader reader;
	private StringBuilder field;
	private StringBuilder line;
	private int statusQuote = UNDEFINED;

	private final char delimiter;
	private final char quote;

	/**
	 * 
	 * @param reader
	 * @param delimiter
	 * @param quote
	 */
	public CSVReader(final Reader reader, final char delimiter, final char quote) {
		this.reader = Objects.requireNonNull(reader, "You need to provide a non null reader.");
		this.delimiter = delimiter;
		this.quote = quote;
		this.row = new ArrayList<>();
		this.field = new StringBuilder();
		this.line = new StringBuilder();		
	}
	
	/**
	 * 
	 * @param action
	 * @throws IOException
	 */
	public void process(final IAction<CSVRecord> action) throws IOException {

		if (this.reader.ready()) {
			int c = -1;
			while ((c = this.reader.read()) != -1) {
				if (NEWLINE == c) {

					action.perform(this.getRecord());
					this.clear();
				} else {
					this.read((char) c);
				}

			}

		}

	}

	/**
	 * 
	 * @param each
	 * @param action
	 * @throws IOException
	 */
	public void processEach(final int each, final IAction<CSVRecord[]> action) throws IOException {

		if (this.reader.ready()) {
			int c = -1;
			int count = 0;
			final List<CSVRecord> records = new ArrayList<>();
			while ((c = this.reader.read()) != -1) {

				if (NEWLINE == c) {

					if (count == each) {
						action.perform(records.toArray(new CSVRecord[] {}));
						records.clear();
						count = 0;
					}

					records.add(this.getRecord());
					this.clear();
					count++;

				} else {

					this.read((char) c);

				}

			}

			if (count > 0) {

				action.perform(records.toArray(new CSVRecord[] {}));
				records.clear();
				count = 0;

			}

		}

	}

	/**
	 * 
	 * @param b
	 */
	public void read(final char b) {

		this.line.append(b);
		if (b == this.delimiter && OPENED_QUOTE != this.statusQuote) {

			this.row.add(this.field.toString());
			this.field = new StringBuilder();
			if (this.statusQuote == CLOSED_QUOTE) {
				this.statusQuote = UNDEFINED;
			}

		} else if (b == this.quote) {

			switch (this.statusQuote) {
			case UNDEFINED:
				this.statusQuote = OPENED_QUOTE;
				break;
			case OPENED_QUOTE:
				this.statusQuote = CLOSED_QUOTE;
				break;
			case CLOSED_QUOTE:
				// TODO: ERROR!!!!
				// System.out.println("we got a quote with status CLOSED_QUOTE.");
				break;
			default:
				break;
			}

		} else {

			if (this.statusQuote == CLOSED_QUOTE) {
				this.statusQuote = OPENED_QUOTE; // TODO: Intern quote.
			}
			this.field.append(b);

		}

	}

	/**
	 * 
	 * @return
	 */
	protected final String[] getFields() {
		if (this.field.length() > 0) {
			this.row.add(this.field.toString());
			this.field = new StringBuilder();
		}
		return this.row.toArray(new String[] {});
	}
	
	/**
	 * 
	 * @return
	 */
	public final CSVRecord getRecord() {
		return new CSVRecord(this.getFields(), this.line.toString());
	}

	/**
	 * 
	 */
	public final void clear() {
		this.row.clear();
		this.field = new StringBuilder();
		this.line = new StringBuilder();	
		this.statusQuote = UNDEFINED;
	}
	
}
