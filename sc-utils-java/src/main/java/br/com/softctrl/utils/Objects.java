/*
 * Copyright (c) 2009, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package br.com.softctrl.utils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import br.com.softctrl.utils.json.GsonUtils;

/**
 * This class consists of {@code static} utility methods for operating
 * on objects.  These utilities include {@code null}-safe or {@code
 * null}-tolerant methods for computing the hash code of an object,
 * returning a string for an object, and comparing two objects.
 *
 * @since 1.7
 */
public final class Objects {
    private Objects() {
        throw new AssertionError("No br.com.softctrl.utils.Objects instances for you!");
    }
    
    /**
     * 
     * @author carlostimoshenkorodrigueslopes@gmail.com
     *
     */
    public static final class StringHelper {

    	/**
    	 * 
    	 * @author carlostimoshenkorodrigueslopes@gmail.com
    	 *
    	 */
    	private static final class Constants {
    		private static final String CLASS_FORMAT = "Class<%s>: ";
    	}

        private StringBuilder mDescription = new StringBuilder();
        private java.util.HashMap<String, Object> mFields = new java.util.HashMap<String, Object>();

        /**
         *
         * @param clazz
         */
        private StringHelper(final Class<?> clazz) {
            this.mDescription.append(String.format(Constants.CLASS_FORMAT,
            		                               Objects.requireNonNull(clazz).getSimpleName()));
        }

        /**
         * 
         * @param name
         * @param value
         * @return
         */
        public StringHelper add(String name, Object value) {
            this.mFields.put(name, value);
            return this;
        }

		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
        @Override
        public String toString() {
			StringBuilder result = new StringBuilder('{');
			for (Map.Entry<String, Object> field : this.mFields.entrySet()) {
				result.append(field.getKey()).append(':').append(' ').append(field.getValue()).append(' ');
			}
			return this.mDescription.append(result).append('}').toString();
        }
        
        public <T> String toString(T object) {
        	if (nonNull(object)) {
        		JsonObject json = GsonUtils.toJsonElement(object).getAsJsonObject();
        		//json.
        		// TODO em desenvolvimento
        	}
        	return "";
        }
        
    }
    
    /**
     *
     * @param clazz
     * @return
     */
    public static StringHelper toStringHelper(final Class<?> clazz) {
        return new StringHelper(clazz);
    }
    
    /**
     * Returns {@code true} if the arguments are equal to each other
     * and {@code false} otherwise.
     * Consequently, if both arguments are {@code null}, {@code true}
     * is returned and if exactly one argument is {@code null}, {@code
     * false} is returned.  Otherwise, equality is determined by using
     * the {@link Object#equals equals} method of the first
     * argument.
     *
     * @param a an object
     * @param b an object to be compared with {@code a} for equality
     * @return {@code true} if the arguments are equal to each other
     * and {@code false} otherwise
     * @see Object#equals(Object)
     */
    public static <T> boolean equals(T a, T b) {
        return (a == b) || (a != null && a.equals(b));
    }

   /**
    * Returns {@code true} if the arguments are deeply equal to each other
    * and {@code false} otherwise.
    *
    * Two {@code null} values are deeply equal.  If both arguments are
    * arrays, the algorithm in {@link Arrays#deepEquals(Object[],
    * Object[]) Arrays.deepEquals} is used to determine equality.
    * Otherwise, equality is determined by using the {@link
    * Object#equals equals} method of the first argument.
    *
    * @param a an object
    * @param b an object to be compared with {@code a} for deep equality
    * @return {@code true} if the arguments are deeply equal to each other
    * and {@code false} otherwise
    * @see Arrays#deepEquals(Object[], Object[])
    * @see Objects#equals(Object, Object)
    */
    public static boolean deepEquals(Object a, Object b) {
        if (a == b)
            return true;
        else if (a == null || b == null)
            return false;
        else
            return Arrays.deepEquals0(a, b);
    }

    /**
     * Returns the hash code of a non-{@code null} argument and 0 for
     * a {@code null} argument.
     *
     * @param o an object
     * @return the hash code of a non-{@code null} argument and 0 for
     * a {@code null} argument
     * @see Object#hashCode
     */
    public static int hashCode(Object o) {
        return o != null ? o.hashCode() : 0;
    }

    /**
     * Returns the hash code of a non-{@code null} argument and 0 for
     * a {@code null} argument.
     *
     * @param o an generic Object
     * @return the hash code of a non-{@code null} argument and 0 for
     * a {@code null} argument
     * @see Object#hashCode
     */
    public static <T> int hashCode(T o) {
        return o != null ? o.hashCode() : 0;
    }

    /**
    * Generates a hash code for a sequence of input values. The hash
    * code is generated as if all the input values were placed into an
    * array, and that array were hashed by calling {@link
    * Arrays#hashCode(Object[])}.
    *
    * <p>This method is useful for implementing {@link
    * Object#hashCode()} on objects containing multiple fields. For
    * example, if an object that has three fields, {@code x}, {@code
    * y}, and {@code z}, one could write:
    *
    * <blockquote><pre>
    * &#064;Override public int hashCode() {
    *     return Objects.hash(x, y, z);
    * }
    * </pre></blockquote>
    *
    * <b>Warning: When a single object reference is supplied, the returned
    * value does not equal the hash code of that object reference.</b> This
    * value can be computed by calling {@link #hashCode(Object)}.
    *
    * @param values the values to be hashed
    * @return a hash value of the sequence of input values
    * @see Arrays#hashCode(Object[])
    * @see List#hashCode
    */
    public static int hash(Object... values) {
        return Arrays.hashCode(values);
    }

    /**
     * 
     * @param values
     * @return
     */
    public static <T> int hash(T... values) {
        return Arrays.hashCode(values);
    }
    /**
     * Returns the result of calling {@code toString} for a non-{@code
     * null} argument and {@code "null"} for a {@code null} argument.
     *
     * @param o an object
     * @return the result of calling {@code toString} for a non-{@code
     * null} argument and {@code "null"} for a {@code null} argument
     * @see Object#toString
     * @see String#valueOf(Object)
     */
    public static String toString(Object o) {
        return String.valueOf(o);
    }

    /**
     * 
     * @param object
     * @return
     */
    public static <T> String toString(T object) {
        return String.valueOf(object);
    }

    /**
     * Returns the result of calling {@code toString} on the first
     * argument if the first argument is not {@code null} and returns
     * the second argument otherwise.
     *
     * @param o an object
     * @param nullDefault string to return if the first argument is
     *        {@code null}
     * @return the result of calling {@code toString} on the first
     * argument if it is not {@code null} and the second argument
     * otherwise.
     * @see Objects#toString(Object)
     */
    public static String toString(Object o, String nullDefault) {
        return (o != null) ? o.toString() : nullDefault;
    }

    /**
     * Returns 0 if the arguments are identical and {@code
     * c.compare(a, b)} otherwise.
     * Consequently, if both arguments are {@code null} 0
     * is returned.
     *
     * <p>Note that if one of the arguments is {@code null}, a {@code
     * NullPointerException} may or may not be thrown depending on
     * what ordering policy, if any, the {@link Comparator Comparator}
     * chooses to have for {@code null} values.
     *
     * @param <T> the type of the objects being compared
     * @param a an object
     * @param b an object to be compared with {@code a}
     * @param c the {@code Comparator} to compare the first two arguments
     * @return 0 if the arguments are identical and {@code
     * c.compare(a, b)} otherwise.
     * @see Comparable
     * @see Comparator
     */
    public static <T> int compare(T a, T b, Comparator<? super T> c) {
        return (a == b) ? 0 :  c.compare(a, b);
    }

    /**
     * Checks that the specified object reference is not {@code null}. This
     * method is designed primarily for doing parameter validation in methods
     * and constructors, as demonstrated below:
     * <blockquote><pre>
     * public Foo(Bar bar) {
     *     this.bar = Objects.requireNonNull(bar);
     * }
     * </pre></blockquote>
     *
     * @param obj the object reference to check for nullity
     * @param <T> the type of the reference
     * @return {@code obj} if not {@code null}
     * @throws NullPointerException if {@code obj} is {@code null}
     */
    public static <T> T requireNonNull(T obj) {
        if (obj == null)
            throw new NullPointerException();
        return obj;
    }

    /**
     * 
     * @param objects
     * @return
     */
    public static <T> List<T> requireNonNull(List<T> objects) {
        if (isNullOrEmpty(objects)) throw new NullPointerException();
        return objects;
    }

    /**
     * 
     * @param objects
     * @return
     */
    public static <T> T[] requireNonNull(T[] objects) {
        if (isNullOrEmpty(objects)) throw new NullPointerException();
        return objects;
    }

    /**
     * Checks that the specified object reference is not {@code null} and
     * throws a customized {@link NullPointerException} if it is. This method
     * is designed primarily for doing parameter validation in methods and
     * constructors with multiple parameters, as demonstrated below:
     * <blockquote><pre>
     * public Foo(Bar bar, Baz baz) {
     *     this.bar = Objects.requireNonNull(bar, "bar must not be null");
     *     this.baz = Objects.requireNonNull(baz, "baz must not be null");
     * }
     * </pre></blockquote>
     *
     * @param obj     the object reference to check for nullity
     * @param message detail message to be used in the event that a {@code
     *                NullPointerException} is thrown
     * @param <T> the type of the reference
     * @return {@code obj} if not {@code null}
     * @throws NullPointerException if {@code obj} is {@code null}
     */
    public static <T> T requireNonNull(T obj, String message) {
        if (obj == null)
            throw new NullPointerException(message);
        return obj;
    }

    /**
     * Returns {@code true} if the provided reference is {@code null} otherwise
     * returns {@code false}.
     *
     * @apiNote This method exists to be used as a
     * {@link java.util.function.Predicate}, {@code filter(Objects::isNull)}
     *
     * @param obj a reference to be checked against {@code null}
     * @return {@code true} if the provided reference is {@code null} otherwise
     * {@code false}
     *
     * @see java.util.function.Predicate
     * @since 1.8
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * 
     * @param obj
     * @return
     */
    public static <T> boolean isNull(T obj) {
        return (obj == null);
    }
    
    
    /**
     * 
     * @param value
     * @return
     */
    public static boolean isNullOrEmpty(String value) {
        return ((value + "").trim().length() == 0);
    }

    /**
     * 
     * @param items
     * @return
     */
    public static boolean isNullOrEmpty(Object[] items) {
        return (isNull(items) ? true : (items.length == 0));
    }

    /**
     * 
     * @param items
     * @return
     */
    public static <T> boolean isNullOrEmpty(T[] items) {
        return (isNull(items) ? true : (items.length == 0));
    }

    /**
     * 
     * @param items
     * @return
     */
    public static <T> boolean isNullOrEmpty(List<T> items) {
        return (isNull(items) ? true : (items.size() == 0));
    }
    
    /**
     * 
     * @param items
     * @return
     */
    public static <T, S> boolean isNullOrEmpty(Map<T, S> items) {
        return (isNull(items) ? true : (items.size() == 0));
    }

    /**
     * Returns {@code true} if the provided reference is non-{@code null}
     * otherwise returns {@code false}.
     *
     * @apiNote This method exists to be used as a
     * {@link java.util.function.Predicate}, {@code filter(Objects::nonNull)}
     *
     * @param obj a reference to be checked against {@code null}
     * @return {@code true} if the provided reference is non-{@code null}
     * otherwise {@code false}
     *
     * @see java.util.function.Predicate
     * @since 1.8
     */
    public static <T> boolean nonNull(T obj) {
        return obj != null;
    }

    /**
     * 
     * @param value
     * @return
     */
    public static boolean nonNullOrEmpty(String value) {
        return (value != null && value.trim().length() > 0);
    }

    /**
     * 
     * @param tArray
     * @return
     */
    public static <T> boolean nonNullOrEmpty(T[] tArray) {
        return (tArray != null && tArray.length > 0);
    }

    /**
     * 
     * @param map
     * @return
     */
    public static <K, V> boolean nonNullOrEmpty(Map<K, V> map) {
        return (map != null && map.size() > 0);
    }

    /**
     * 
     * @param tArray
     * @return
     */
    public static <T> boolean nonNullOrEmpty(List<T> tList) {
        return (tList != null && tList.size() > 0);
    }

    /**
     * 
     * @param value
     * @param defaultValue
     * @return
     */
    public static <T>  T thisOrDefault(T value, T defaultValue) {
        return (T) (value == null ? requireNonNull(defaultValue, "Seriously!!"): value);
    }
    
    /**
     * 
     * @param value
     * @return
     */
    public static int length(String value){
    	return thisOrDefault(value, "").trim().length();
    }
    
    /**
     * 
     * @param values
     * @return
     */
    public static <T> int length(T[] values){
    	return  (isNull(values) ? -1 : values.length);
    }
    
    /**
     * 
     * @param values
     * @return
     */
    public static int length(byte[] values){
    	return  (isNull(values) ? -1 : values.length);
    }

    /**
     * 
     * @param values
     * @return
     */
    public static int length(char[] values){
    	return  (isNull(values) ? -1 : values.length);
    }

    /**
     * 
     * @param values
     * @return
     */
    public static int length(double[] values){
    	return  (isNull(values) ? -1 : values.length);
    }

    /**
     * 
     * @param values
     * @return
     */
    public static int length(float[] values){
    	return  (isNull(values) ? -1 : values.length);
    }

    /**
     * 
     * @param values
     * @return
     */
    public static int length(int[] values){
    	return  (isNull(values) ? -1 : values.length);
    }

    /**
     * 
     * @param values
     * @return
     */
    public static int length(long[] values){
    	return  (isNull(values) ? -1 : values.length);
    }

    /**
     * 
     * @param values
     * @return
     */
    public static <T> int length(List<T> values){
    	return  (isNull(values) ? -1 : values.size());
    }
    
    /**
     * 
     * @param value
     * @param start
     * @param end
     * @return
     */
    public static boolean inRange(int value, int start, int end){
    	return (start <= value && value <= end);
    }
    
    /**
     * 
     * @param value
     * @param start
     * @param end
     * @return
     */
    public static boolean inRange(String value, int start, int end){
    	int length = length(value);
    	return (start <= length && length <= end);
    }
    
    /**
     * 
     * @param value
     * @param start
     * @param end
     * @return
     */
    public static <T extends Number> boolean inRange(T value, int start, int end){
    	int vlr = requireNonNull(value).intValue();
    	return (start <= vlr && vlr <= end);
    }

}
