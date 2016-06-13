/**
 * 
 */
package br.com.softctrl.utils.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.softctrl.utils.Objects;
import br.com.softctrl.utils.json.adapter.StringAdapterFactory;

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
public final class GsonUtils {

	static final class Constants {

		public static final String JSON_PATTERN = "(^\\[ *\\{.*} *]+$|^\\{.*}+$|^\\[ *]+$)";

		public static final String EMPTY_JSON_OBJECT = "{}";

		public static final String EMPTY_JSON_ARRAY = "[]";

		public static final String EMPTY_STRING = "";
	}

	/**
	 * @author carlostimoshenkorodrigueslopes@gmail.com
	 * @param <E>
	 */
	public static interface JsonSerializer<E> extends com.google.gson.JsonSerializer<E> {

		/**
		 * Get the object class to be serialized.
		 * @return the class.
		 */
		Class<E> getClazz();
	}

	private static GsonUtils $THIS = new GsonUtils(false, false);

	private Gson gson;
	
	private JsonParser parser;

	/**
	 * @param pretty
	 * @param serializeNulls
	 * @param serializers
	 */
	private GsonUtils(boolean pretty, boolean serializeNulls, JsonSerializer<?>... serializers) {
		GsonBuilder builder = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation();
		builder.registerTypeAdapterFactory(new StringAdapterFactory());
		if (pretty)
			builder.setPrettyPrinting();
		if (serializeNulls)
			builder.serializeNulls();
		if (serializers != null) {
			for (JsonSerializer<?> serializer : serializers) {
				builder.registerTypeAdapter(serializer.getClazz(), serializer);

			}
		}
		this.gson = builder.create();
	}

	/**
	 * @param pretty
	 * @param serializers
	 * @return
	 */
	public static final GsonUtils setup(boolean pretty, JsonSerializer<?>... serializers) {
		return ($THIS = new GsonUtils(pretty, true, serializers));
	}

	/**
	 * @param pretty
	 * @param serializeNulls
	 * @param serializers
	 * @return
	 */
	public static final GsonUtils setup(boolean pretty, boolean serializeNulls, JsonSerializer<?>... serializers) {
		return ($THIS = new GsonUtils(pretty, serializeNulls, serializers));
	}

	/**
	 * @return
	 */
	public static final GsonUtils getInstance() {
		return $THIS;
	}

	/**
	 * @return
	 */
	Gson getGson() {
		return this.gson;
	}
	
	/**
	 * @return parser.
	 */
	public JsonParser getParser() {
		return this.parser;
	}

	/**
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static final <T> T fromJson(final String json, Class<T> clazz) {
		return getInstance().getGson().fromJson(json, clazz);
	}

	/**
	 * @param object
	 * @return
	 */
	public static final <T> String toJson(final T object) {
		return getInstance().getGson().toJson(object);
	}

	/**
	 * 
	 * @param object
	 * @return
	 */
	public static final <T> JsonElement toJsonElement(final T object) {
		return getInstance().getGson().toJsonTree(object);
	}
	
	/**
	 * @param object
	 * @return T
	 */
	public static final <T> JsonArray toJsonArray(final T object) {
		return toJsonElement(object).getAsJsonArray();
	}

	/**
	 * @param file
	 * @param clazz
	 * @return
	 */
	public static final <T> T fromJsonFile(final String file, Class<T> clazz) {
		return fromJson(new File(file), clazz);
	}

	/**
	 * @param file
	 * @param clazz
	 * @return
	 */
	public static final <T> T fromJson(final File file, Class<T> clazz) {
		T result = null;
		try {
			result = fromJson(new FileReader(file), clazz);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * @param reader
	 * @param clazz
	 * @return
	 */
	public static final <T> T fromJson(final Reader reader, Class<T> clazz) {
		T result = null;
		try {
			result = getInstance().getGson().fromJson(reader, clazz);
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static final <T> T fromJson(final JsonElement json, final Class<T> clazz) {
		T result = null;
		result = getInstance().getGson().fromJson(json, clazz);
		return result;
	}

	/**
	 * @param json
	 * @return
	 */
	public static boolean mayBeValidJson(final String json) {
		return Objects.thisOrDefault(json, Constants.EMPTY_STRING).matches(Constants.JSON_PATTERN);
	}

	/**
	 * @param json
	 * @return
	 */
	public static String validJson(final String json) {
		if (mayBeValidJson(json)) {
			return json;
		} else {
			return Constants.EMPTY_JSON_OBJECT;
		}

	}

	/**
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

	/**
	 * 
	 * @param json
	 * @return
	 */
	public static boolean isEmptyJson(final String json) {
		return Objects.isNullOrEmpty(json)              ||
			   Constants.EMPTY_JSON_OBJECT.equals(json) ||
			   Constants.EMPTY_JSON_ARRAY.equals(json);
	}

	/**
	 * @param json
	 * @return
	 */
	public static JsonObject getAsJsonObject(String json) {
		return getInstance().getParser().parse(json).getAsJsonObject();
	}
	
 	/**
	 * @param json
	 * @return
	 */	
	public static JsonElement getAsJsonElement(String json) {
        	return fromJson(json, JsonElement.class);
	}
	
	/**
	 * @param jsonObject
	 * @param propertyName
	 * @return
	 */
	public static boolean isValidProperty(JsonObject jsonObject, String propertyName) {
		Objects.requireNonNull(jsonObject, "you need inform a vlaid JsonObject.");
		JsonElement property = jsonObject.get(propertyName);
		return (Objects.nonNull(property) && ! property.isJsonNull());
	}

}
