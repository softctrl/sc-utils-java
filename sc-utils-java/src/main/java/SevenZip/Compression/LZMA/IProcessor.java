/**
 * 
 */
package SevenZip.Compression.LZMA;

import java.io.IOException;

/**
 * @author timoshenko
 */
public interface IProcessor<P extends IParameter> {

	/**
	 * @param parameter
	 * @return
	 * @throws IOException
	 */
	public boolean Code(P parameter) throws IOException;

}
