/**
 * 
 */
package br.com.softctrl.utils.json.adapter;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

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
public class StringAdapterFactory implements TypeAdapterFactory {

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
	 * 
	 * @author carlostimoshenkorodrigueslopes@gmail.com
	 *
	 * @param <T>
	 */
	private static class StringAdapter<T> extends TypeAdapter<T> {

		private final TypeAdapter<T> delegate;
		private final TypeAdapter<JsonElement> elementAdapter;

		/**
		 * 
		 * @param delegate
		 * @param elementAdapter
		 */
		public StringAdapter(final TypeAdapter<T> delegate, final TypeAdapter<JsonElement> elementAdapter) {
			this.delegate = delegate;
			this.elementAdapter = elementAdapter;
		}

		/*
		 * (non-Javadoc)
		 * @see com.google.gson.TypeAdapter#read(com.google.gson.stream.JsonReader)
		 */
		@Override
		public T read(JsonReader jsonReader) throws IOException {

			JsonElement element = elementAdapter.read(jsonReader);
			if (element.isJsonPrimitive()) {
				JsonPrimitive value = element.getAsJsonPrimitive();
				if (value.isString() && Objects.isNullOrEmpty(value.getAsString())) return null;
			}
			return this.delegate.fromJsonTree(element);

		}

		/*
		 * (non-Javadoc)
		 * @see com.google.gson.TypeAdapter#write(com.google.gson.stream.JsonWriter, java.lang.Object)
		 */
		@Override
		public void write(JsonWriter jsonWrite, T value) throws IOException {
			this.delegate.write(jsonWrite, value);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.google.gson.TypeAdapterFactory#create(com.google.gson.Gson, com.google.gson.reflect.TypeToken)
	 */
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> token) {
		
		TypeAdapter<T> result = null;
		if (String.class.equals(token.getRawType())) {
			final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, token);
			final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
			result = new StringAdapter<>(delegate, elementAdapter).nullSafe();
		}
		return result;
	}

}
