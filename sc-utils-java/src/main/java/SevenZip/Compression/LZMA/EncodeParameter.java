/**
 * 
 */
package SevenZip.Compression.LZMA;

import java.io.InputStream;
import java.io.OutputStream;

import SevenZip.ICodeProgress;

/**
 * @author timoshenko
 */
public class EncodeParameter implements IParameter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4139647497413341573L;

	java.io.InputStream inStream;

	java.io.OutputStream outStream;

	long inSize;

	long outSize;

	ICodeProgress progress;

	/**
	 * @param inStream
	 * @param outStream
	 * @param inSize
	 * @param outSize
	 * @param progress
	 */
	public EncodeParameter(InputStream inStream, OutputStream outStream, long inSize, long outSize, ICodeProgress progress) {
		super();
		this.inStream = inStream;
		this.outStream = outStream;
		this.inSize = inSize;
		this.outSize = outSize;
		this.progress = progress;
	}

}
