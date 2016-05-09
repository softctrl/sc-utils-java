package br.com.softctrl.utils.io.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.com.softctrl.utils.Objects;

/*
 * The MIT License (MIT)
 * Copyright (c) 2016 Carlos Timoshenko Rodrigues Lopes
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
public final class MD5 {

	private static final String MD_5 = "MD5";

	private static final String _02X = "%02x";

	/**
	 * @param file
	 * @return
	 */
	public static String getMD5CheckSum(final File file) {
		StringBuilder hashMD5 = new StringBuilder();
		try {
			hashMD5.append(getMD5CheckSum(new FileInputStream(file)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return hashMD5.toString();
	}

	/**
	 * @param inputStream
	 * @return
	 */
	public static String getMD5CheckSum(final InputStream inputStream) {
		StringBuilder hashMD5 = new StringBuilder();
		try {
			MessageDigest md5 = MessageDigest.getInstance(MD_5);

			byte[] buffer = new byte[1024];
			int count;
			while ((count = inputStream.read(buffer)) > 0) {
				md5.update(buffer, 0, count);
			}

			byte[] digest = md5.digest();
			for (byte b : digest) {
				hashMD5.append(String.format(_02X, b));
			}
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hashMD5.toString();
	}

	/**
	 * @param value
	 * @return
	 */
	public static String getMD5CheckSum(final String value) {
		StringBuilder hashMD5 = new StringBuilder();
		try {
			MessageDigest md5 = MessageDigest.getInstance(MD_5);
			byte[] digest = md5.digest(Objects.requireNonNull(value).getBytes(StandardCharsets.UTF_8));
			for (byte b : digest) {
				hashMD5.append(String.format(_02X, b));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hashMD5.toString();
	}

}
