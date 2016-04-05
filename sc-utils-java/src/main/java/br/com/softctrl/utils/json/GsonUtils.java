/**
 * 
 */
package br.com.softctrl.utils.json;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.softctrl.utils.Objects;

/*
The MIT License (MIT)

Copyright (c) 2016 Carlos Timoshenko Rodrigues Lopes
http://www.0x09.com.br

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

/**
 * 
 * @author carlostimoshenkorodrigueslopes@gmail.com
 */
public final class GsonUtils {

	/**
	 * 
	 * @author carlostimoshenkorodrigueslopes@gmail.com
	 *
	 * @param <E>
	 */
	public static interface JsonSerializer<E> extends com.google.gson.JsonSerializer<E> {
		/**
		 * Get the object class to be serialized.
		 * 
		 * @return the class.
		 */
		Class<E> getClazz();
	}

	private static GsonUtils $THIS = new GsonUtils(false);

	private Gson gson;

	private GsonUtils(boolean pretty, JsonSerializer<?>... serializers) {
		GsonBuilder builder = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().serializeNulls();
		if (pretty)
			builder.setPrettyPrinting();
		if (serializers != null) {
			for (JsonSerializer<?> serializer : serializers) {
				builder.registerTypeAdapter(serializer.getClazz(), serializer);

			}
		}
		this.gson = builder.create();
	}

	/**
	 * 
	 * @param pretty
	 * @param serializers
	 * @return
	 */
	public GsonUtils setup(boolean pretty, JsonSerializer<?>... serializers) {
		return ($THIS = new GsonUtils(pretty, serializers));
	}

	/**
	 * 
	 * @return
	 */
	public static final GsonUtils getInstance() {
		return $THIS;
	}

	/**
	 * 
	 * @return
	 */
	Gson getGson() {
		return this.gson;
	}

	/**
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static final <T> T fromJson(final String json, Class<T> clazz) {
		return getInstance().getGson().fromJson(json, clazz);
	}

	/**
	 * 
	 * @param object
	 * @return
	 */
	public static final <T> String toJson(final Object object) {
		return getInstance().getGson().toJson(object);
	}

	/**
	 * 
	 * @param file
	 * @param clazz
	 * @return
	 */
	public static final <T> T fromJsonFile(final String file, Class<T> clazz) {
		T result = null;
		FileReader json;
		try {
			json = new FileReader(new File(file));
			result = getInstance().getGson().fromJson(json, clazz);
			json.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return result;

	}

	/**
	 * 
	 * @param file
	 * @param clazz
	 * @return
	 */
	public static final <T> T fromJsonFile(final File file, Class<T> clazz) {
		T result = null;
		FileReader json;
		try {
			json = new FileReader(file);
			result = getInstance().getGson().fromJson(json, clazz);
			json.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return result;

	}

	/**
	 * 
	 * @param json
	 * @return
	 */
	public static boolean mayBeValidJson(final String json) {
		return Objects.nonNull(json)
				&& ((json.startsWith("{") && json.endsWith("}")) || (json.startsWith("[") && json.endsWith("]")));
	}

	/**
	 * 
	 * @param json
	 * @return
	 */
	public static String validJson(final String json) {
		if (mayBeValidJson(json)) {
			return json;
		} else {
			return "{}";
		}

	}

	/**
	 *
	 * @param json
	 * @return
	 */
	public static boolean isValidJson(final String json) {
		boolean result = false;
		try {
			fromJson(json, Object.class);
			result = true;
		} catch (Exception ex) {
		}
		return result;
	}

}
