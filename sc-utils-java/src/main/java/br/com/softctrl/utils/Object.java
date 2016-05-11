package br.com.softctrl.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import SevenZip.Compression.LZMA.Decoder;
import br.com.softctrl.utils.json.GsonUtils;
import br.com.softctrl.utils.szip.EncoderBuilder;

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
 * @author carlostimoshenkorodrigueslopes@gmail.com
 */
public class Object implements Serializable {
	
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
	 * @author carlostimoshenkorodrigueslopes@gmail.com
	 */
	private static class Compressed extends br.com.softctrl.utils.Object {
		private static final long serialVersionUID = 647086363729758142L;
		@Expose @SerializedName("js") private String value;
		private Compressed(final String value){ this.value = value; }
	    public static Compressed fromJson(String json){ return fromJson(json, Compressed.class); }
	}

    /**
     * 
     */
    private static final long serialVersionUID = 989851473361266980L;
    
    /**
     * 
     * @return
     */
    public final String toJson() {
        return GsonUtils.toJson(this);
    }
    
	/**
	 * Performs a compression on the Json representation of this instance object.
	 * @return A compressed string.
	 * @throws IOException .
	 * @throws Exception .
	 */
	public final String compress() throws IOException, Exception {
		
		final String json = this.toJson();
		ByteArrayInputStream inputStream = new ByteArrayInputStream(json.getBytes());
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		EncoderBuilder.newEncoder().create().prepareOutputStream(outputStream, json.length()).Code(inputStream, outputStream, -1, -1, null);
		return new Compressed(Base64.encode(outputStream.toByteArray()).replaceAll("\n", "")).toJson();
		
	}

	/**
	 * Performs a compression on the array of the objects informed.
	 * @return A compressed string.
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static final <T> String compress(T[] objects) throws IOException, Exception {

		final String json = GsonUtils.toJson(objects);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(json.getBytes());
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		EncoderBuilder.newEncoder().create().prepareOutputStream(outputStream, json.length()).Code(inputStream, outputStream, -1, -1, null);
		return new Compressed(Base64.encode(outputStream.toByteArray()).replaceAll("\n", "")).toJson();

	}

    /**
     * 
     * @param json
     * @param clazz
     * @return
     */
    public static <E> E fromJson(String json, Class<E> clazz){
        return GsonUtils.fromJson(json, clazz);
    }

    /**
     * 
     * @param json
     * @param clazz
     * @return
     */
    public static <E> E fromJson(File json, Class<E> clazz){
        return GsonUtils.fromJson(json, clazz);
    }
    
	/**
	 * Creates an object from an informed <codigo>json</codigo> ciphered string.
	 * @param json A <codigo>json</codigo> ciphered string.
	 * @return If informed a valid <codigo>json</codigo> ciphered string, will return a valid object.
	 * @throws IOException .
	 */
	public static final <T> T from7zJson(String json, Class<T> clazz) throws IOException {

		ByteArrayInputStream input = new ByteArrayInputStream(Base64.decode(Objects.requireNonNull(Compressed.fromJson(json)).value, Base64.DEFAULT));
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		if (!(new Decoder()).Code(input, output)) throw new RuntimeException("Error in data stream");
		return GsonUtils.fromJson(new String(output.toByteArray()), clazz);

	}

}