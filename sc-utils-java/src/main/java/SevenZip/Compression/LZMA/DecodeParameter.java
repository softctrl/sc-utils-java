package SevenZip.Compression.LZMA;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DecodeParameter implements IParameter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5162504241090568230L;

	java.io.InputStream inStream;

	java.io.OutputStream outStream;

	long outSize;
	
	/**
	 * 
	 * @param inStream
	 * @param outStream
	 * @throws IOException
	 */
	public DecodeParameter(InputStream inStream, OutputStream outStream) throws IOException {
		this(inStream, outStream, -1);
	}

	/**
	 * 
	 * @param inStream
	 * @param outStream
	 * @param outSize
	 * @throws IOException
	 */
	public DecodeParameter(InputStream inStream, OutputStream outStream, long outSize) throws IOException {
		super();
		if (outSize == -1) {
			for (int i = 0 ; i < 8 ; i++) {
				int v = inStream.read();
				if (v < 0) throw new RuntimeException("Can't read stream size");
				outSize |= ((long) v) << (8 * i);
			}
		}
		this.inStream = inStream;
		this.outStream = outStream;
		this.outSize = outSize;
	}

}
