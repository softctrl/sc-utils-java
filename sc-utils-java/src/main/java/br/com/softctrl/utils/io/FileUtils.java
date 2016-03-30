/**
 * 
 */
package br.com.softctrl.utils.io;

import java.io.File;
import java.net.URL;

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
public final class FileUtils {

    public static final String USER_DIR = System.getProperty("user.dir");

    private FileUtils() {
    }

    /**
     * 
     * @param filename
     * @return
     */
    public static File getResourceFile(final String filename) {
        return new File(getFilePathInClasspath(filename));
    }

    /**
     * 
     * @param filename
     * @return
     */
    public static final File getFileInClasspath(final String filename) {
        String filePath = getFilePathInClasspath(filename);
        if ((filePath + "").trim().length() > 0) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * 
     * @param filename
     * @return
     */
    public static final String getFilePathInClasspath(final String filename) {
        URL url = ClassLoader.getSystemResource(filename);
        return (url == null ? "" : url.getFile());
    }

}