/**
 * 
 */
package br.com.softctrl.utils.szip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import SevenZip.Compression.LZMA.DecodeParameter;
import SevenZip.Compression.LZMA.Decoder;
import SevenZip.Compression.LZMA.EncodeParameter;
import SevenZip.Compression.LZMA.Encoder;
import SevenZip.Compression.LZMA.IParameter;
import SevenZip.Compression.LZMA.IProcessor;
import br.com.softctrl.utils.Base64;
import br.com.softctrl.utils.Objects;

/**
 * @author timoshenko
 */
public class EncoderBuilder<T extends IProcessor<P>, P extends IParameter> {

	/**
	 * @author timoshenko
	 */
	public enum Action {
		ENCODE("e"), DECODE("d"), BENCHMARK("b");

		private final String param;

		private static final Action[] ACTIONS = Action.values();

		private Action(String param) {
			this.param = param;
		}

		public String getParam() {
			return param;
		}

		/**
		 * @param value
		 * @return
		 */
		public static Action valueOf(char c) {
			Action result = null;
			String value = "" + c;
			for (Action action : ACTIONS) {
				if (action.getParam() == value) {
					result = action;
					break;
				}
			}
			return result;
		}
	}

	public enum MatchFinder {
		BT2, BT4, BT4B;

		private static final MatchFinder[] MATCH_FINDERS = MatchFinder.values();

		public static final int maxIdx = MATCH_FINDERS.length;

		public int getCode() {
			return this.ordinal();
		}

		public static MatchFinder valueOf(int cod) {
			MatchFinder result = null;
			if (Objects.inRange(cod, 0, maxIdx)) {
				result = MATCH_FINDERS[cod];
			}
			return result;
		}
	}

	public enum Algorithm {
		FAST_MODE(0), MAX_MODE(2);

		private static final Algorithm[] ALGORITHMS = Algorithm.values();

		public static final int maxIdx = ALGORITHMS.length;

		private int mode;

		private Algorithm(int mode) {
			this.mode = mode;
		}

		public int getMode() {
			return mode;
		}

		public static Algorithm valueOf(int mode) {
			Algorithm result = null;
			if (Objects.inRange(mode, 0, maxIdx)) {
				result = ALGORITHMS[mode];
			}
			return result;
		}
	}

	public Action action = null;

	public int numBenchmarkPasses = 10;

	public int dictionarySize = 1 << 23;

	public boolean dictionarySizeIsDefined = false;

	public int numberOfLiteralContextBits = 3;

	public int numberOfLiteralPosBits = 0;

	public int numberOfPosBits = 2;

	public int numberOfFastBytes = 128;

	public boolean numberOfFastBytesIsDefined = false;

	public boolean writeEndOfStreamMarker = false;

	public Algorithm algorithm = Algorithm.MAX_MODE;

	public MatchFinder matchFinder = MatchFinder.BT4;

	public String inputFile;

	public String outputFile;

	/**
	 * @param s
	 * @return
	 */
	boolean ParseSwitch(String s) {

		if (s.startsWith("d")) {
			dictionarySize = 1 << Integer.parseInt(s.substring(1));
			dictionarySizeIsDefined = true;
		} else if (s.startsWith("fb")) {
			numberOfFastBytes = Integer.parseInt(s.substring(2));
			numberOfFastBytesIsDefined = true;
		} else if (s.startsWith("a"))
			algorithm = Algorithm.valueOf(Integer.parseInt(s.substring(1)));
		else if (s.startsWith("lc"))
			numberOfLiteralContextBits = Integer.parseInt(s.substring(2));
		else if (s.startsWith("lp"))
			numberOfLiteralPosBits = Integer.parseInt(s.substring(2));
		else if (s.startsWith("pb"))
			numberOfPosBits = Integer.parseInt(s.substring(2));
		else if (s.startsWith("eos"))
			writeEndOfStreamMarker = true;
		else if (s.startsWith("mf")) {

			matchFinder = Objects.thisOrDefault(MatchFinder.valueOf(s.substring(2).toUpperCase()), MatchFinder.BT4);

		} else {
			return false;
		}
		return true;

	}

	/**
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public boolean Parse(String[] args) throws Exception {
		int pos = 0;
		boolean switchMode = true;
		for (int i = 0 ; i < args.length ; i++) {
			String s = args[i];
			if (s.length() == 0)
				return false;
			if (switchMode) {
				if (s.compareTo("--") == 0) {
					switchMode = false;
					continue;
				}
				if (s.charAt(0) == '-') {
					String sw = s.substring(1).toLowerCase();
					if (sw.length() == 0)
						return false;
					try {
						if (!ParseSwitch(sw))
							return false;
					} catch (NumberFormatException e) {
						return false;
					}
					continue;
				}
			}
			if (pos == 0 && Objects.length(s) == 1) {
				if ((action = Action.valueOf(s.charAt(0))) == null) {
					return false;
				}
			} else if (pos == 1) {
				if (Action.BENCHMARK.equals(action)) {
					try {
						numBenchmarkPasses = Integer.parseInt(s);
						if (numBenchmarkPasses < 1)
							return false;
					} catch (NumberFormatException e) {
						return false;
					}
				} else
					inputFile = s;
			} else if (pos == 2)
				outputFile = s;
			else
				return false;
			pos++;
			continue;
		}
		return true;
	}

	/**
	 * 
	 */
	private EncoderBuilder() {

	}

	/**
	 * @return
	 */
	public static final EncoderBuilder<Encoder, EncodeParameter> newEncoder() {
		return new EncoderBuilder<Encoder, EncodeParameter>().setAction(Action.ENCODE);
	}

	/**
	 * @return
	 */
	public static final EncoderBuilder<Decoder, DecodeParameter> newDecoder() {
		return new EncoderBuilder<Decoder, DecodeParameter>().setAction(Action.DECODE);
	}

	/**
	 * @return
	 */
	// TODO: implement needs
	boolean isValid() {
		return true;
	}

	/**
	 * @param action the action to set
	 * @return
	 */
	public final EncoderBuilder<T, P> setAction(Action action) {
		this.action = action;
		return this;
	}

	/**
	 * @param numBenchmarkPasses the numBenchmarkPasses to set
	 * @return
	 */
	public final EncoderBuilder<T, P> setNumBenchmarkPasses(int numBenchmarkPasses) {
		this.numBenchmarkPasses = numBenchmarkPasses;
		return this;
	}

	/**
	 * @param dictionarySize the dictionarySize to set
	 * @return
	 */
	public final EncoderBuilder<T, P> setDictionarySize(int dictionarySize) {
		this.dictionarySize = dictionarySize;
		return this;
	}

	/**
	 * @param dictionarySizeIsDefined the dictionarySizeIsDefined to set
	 * @return
	 */
	public final EncoderBuilder<T, P> setDictionarySizeIsDefined(boolean dictionarySizeIsDefined) {
		this.dictionarySizeIsDefined = dictionarySizeIsDefined;
		return this;
	}

	/**
	 * @param numberOfLiteralContextBits the numberOfLiteralContextBits to set
	 * @return
	 */
	public final EncoderBuilder<T, P> setNumberOfLiteralContextBits(int numberOfLiteralContextBits) {
		this.numberOfLiteralContextBits = numberOfLiteralContextBits;
		return this;
	}

	/**
	 * @param numberOfLiteralPosBits the numberOfLiteralPosBits to set
	 * @return
	 */
	public final EncoderBuilder<T, P> setNumberOfLiteralPosBits(int numberOfLiteralPosBits) {
		this.numberOfLiteralPosBits = numberOfLiteralPosBits;
		return this;
	}

	/**
	 * @param numberOfPosBits the numberOfPosBits to set
	 * @return
	 */
	public final EncoderBuilder<T, P> setNumberOfPosBits(int numberOfPosBits) {
		this.numberOfPosBits = numberOfPosBits;
		return this;
	}

	/**
	 * @param numberOfFastBytes the numberOfFastBytes to set
	 * @return
	 */
	public final EncoderBuilder<T, P> setNumberOfFastBytes(int numberOfFastBytes) {
		this.numberOfFastBytes = numberOfFastBytes;
		return this;
	}

	/**
	 * @param numberOfFastBytesIsDefined the numberOfFastBytesIsDefined to set
	 * @return
	 */
	public final EncoderBuilder<T, P> setNumberOfFastBytesIsDefined(boolean numberOfFastBytesIsDefined) {
		this.numberOfFastBytesIsDefined = numberOfFastBytesIsDefined;
		return this;
	}

	/**
	 * @param writeEndOfStreamMarker the writeEndOfStreamMarker to set
	 * @return
	 */
	public final EncoderBuilder<T, P> setWriteEndOfStreamMarker(boolean writeEndOfStreamMarker) {
		this.writeEndOfStreamMarker = writeEndOfStreamMarker;
		return this;
	}

	/**
	 * @param algorithm the algorithm to set
	 * @return
	 */
	public final EncoderBuilder<T, P> setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
		return this;
	}

	/**
	 * @param matchFinder the matchFinder to set
	 * @return
	 */
	public final EncoderBuilder<T, P> setMatchFinder(MatchFinder matchFinder) {
		this.matchFinder = matchFinder;
		return this;
	}

	/**
	 * @param inputFile the inputFile to set
	 * @return
	 */
	public final EncoderBuilder<T, P> setInputFile(String inputFile) {
		this.inputFile = inputFile;
		return this;
	}

	/**
	 * @param outputFile the outputFile to set
	 * @return
	 */
	public final EncoderBuilder<T, P> setOutputFile(String outputFile) {
		this.outputFile = outputFile;
		return this;
	}

	public T create() throws Exception {

		T result = null;
		if (Action.ENCODE.equals(this.action)) {
			if (!isValid())
				throw new RuntimeException("Incorrect parameters.");
			result = (T) new SevenZip.Compression.LZMA.Encoder();
			SevenZip.Compression.LZMA.Encoder encoder = new SevenZip.Compression.LZMA.Encoder();
			if (!encoder.SetAlgorithm(Objects.requireNonNull(this.algorithm).getMode()))
				throw new Exception("Incorrect compression mode");
			if (!encoder.SetDictionarySize(this.dictionarySize))
				throw new Exception("Incorrect dictionary size");
			if (!encoder.SetNumFastBytes(this.numberOfFastBytes))
				throw new Exception("Incorrect -fb value");
			if (!encoder.SetMatchFinder(Objects.requireNonNull(this.matchFinder).getCode()))
				throw new Exception("Incorrect -mf value");
			if (!encoder.SetLcLpPb(this.numberOfLiteralContextBits, this.numberOfLiteralPosBits, this.numberOfPosBits))
				throw new Exception("Incorrect -lc or -lp or -pb value");
			encoder.SetEndMarkerMode(this.writeEndOfStreamMarker);
		} else if (Action.DECODE.equals(this.action)) {

		}
		// if (!isValid())
		// throw new RuntimeException("Incorrect parameters.");
		// SevenZip.Compression.LZMA.Encoder encoder = new SevenZip.Compression.LZMA.Encoder();
		// if (!encoder.SetAlgorithm(Objects.requireNonNull(this.algorithm).getMode()))
		// throw new Exception("Incorrect compression mode");
		// if (!encoder.SetDictionarySize(this.dictionarySize))
		// throw new Exception("Incorrect dictionary size");
		// if (!encoder.SetNumFastBytes(this.numberOfFastBytes))
		// throw new Exception("Incorrect -fb value");
		// if (!encoder.SetMatchFinder(Objects.requireNonNull(this.matchFinder).getCode()))
		// throw new Exception("Incorrect -mf value");
		// if (!encoder.SetLcLpPb(this.numberOfLiteralContextBits, this.numberOfLiteralPosBits, this.numberOfPosBits))
		// throw new Exception("Incorrect -lc or -lp or -pb value");
		// encoder.SetEndMarkerMode(this.writeEndOfStreamMarker);
		// return encoder;
		return result;

	}

	public static void main(String[] args) throws IOException, Exception {

		final String message = "4KSBxIHlhLHihKHhpIHjhKHhhJHjhJXhjJLhjJnEgeGQkcSh4YSB44Sh44SV4YyZ4YiQ4YCRxYHEgeSEgeOEsemAkOGAkuGFkeOEoeOEseKEseOFoeOEoeKEk+GEgeOEgeiEseKGkOGYk+GkgeOEgeGAkeGUkemAk+GIl+GEgeOEseKEseaEseaEseaFoQ==";//"[{\"quantidadeEncontrado\":1,\"quantidadeRecuperado\":1,\"jsonRecuperado\":[{\"nome\":\"ANDERSON EDSON DE LIMA PEREIRA\",\"nomeMae\":\"SUELI DE FATIMA LIMA\",\"documento\":\"14134948\",\"listaPorNatureza\":[{\"descricao\":\"OUTRAS INFRACOES DEMAIS LEIS ESPECIAIS \",\"listaEnvolvimento\":[{\"data\":1393512180000,\"tipoEnvolvimento\":\"AUTOR\",\"cidadeBairro\":\"SANTA EFIGENIA / BELO HORIZONTE\"}]},{\"descricao\":\"DESACATO\",\"listaEnvolvimento\":[{\"data\":1392974580000,\"tipoEnvolvimento\":\"AUTOR\",\"cidadeBairro\":\"ESPLANADA / BELO HORIZONTE\"}]},{\"descricao\":\"FURTO\",\"listaEnvolvimento\":[{\"data\":1379946720000,\"tipoEnvolvimento\":\"AUTOR\",\"cidadeBairro\":\"SANTA EFIGENIA / BELO HORIZONTE\"},{\"data\":1386169140000,\"tipoEnvolvimento\":\"AUTOR\",\"cidadeBairro\":\"FLORESTA / BELO HORIZONTE\"},{\"data\":1391076840000,\"tipoEnvolvimento\":\"SOLICITANTE\",\"cidadeBairro\":\"GRANJA DE FREITAS / BELO HORIZONTE\"}]},{\"descricao\":\"TRAFICO ILICITO DE DROGAS\",\"listaEnvolvimento\":[{\"data\":1399034940000,\"tipoEnvolvimento\":\"AUTOR\",\"cidadeBairro\":\"GRANJA DE FREITAS / BELO HORIZONTE\"}]},{\"descricao\":\"AMEACA\",\"listaEnvolvimento\":[{\"data\":1392807480000,\"tipoEnvolvimento\":\"AUTOR\",\"cidadeBairro\":\"SANTA EFIGENIA / BELO HORIZONTE\"}]},{\"descricao\":\"OUTRAS ACOES DEFESA SOCIAL (DISCRIMINAR NO HISTORICO)\",\"listaEnvolvimento\":[{\"data\":1456851600000,\"tipoEnvolvimento\":\"AUTOR\",\"cidadeBairro\":\"praça da esplanada / RIBEIRAO DAS NEVES\"}]},{\"descricao\":\"VIAS DE FATO / AGRESSAO\",\"listaEnvolvimento\":[{\"data\":1386527040000,\"tipoEnvolvimento\":\"AUTOR\",\"cidadeBairro\":\"VILA ANTENA / BELO HORIZONTE\"},{\"data\":1392927360000,\"tipoEnvolvimento\":\"OUTROS (DISCRIMINAR HISTORICO)\",\"cidadeBairro\":\"SANTA EFIGENIA / BELO HORIZONTE\"}]},{\"descricao\":\"NEGAR SALDAR DESPESA\",\"listaEnvolvimento\":[{\"data\":1377026400000,\"tipoEnvolvimento\":\"AUTOR\",\"cidadeBairro\":\"BARRO PRETO / BELO HORIZONTE\"},{\"data\":1392147060000,\"tipoEnvolvimento\":\"AUTOR\",\"cidadeBairro\":\"BOA VIAGEM / BELO HORIZONTE\"},{\"data\":1394937720000,\"tipoEnvolvimento\":\"AUTOR\",\"cidadeBairro\":\"UNIAO / BELO HORIZONTE\"}]}]}]}]";
		ByteArrayInputStream input1 = new ByteArrayInputStream(message.getBytes());
		ByteArrayOutputStream output1 = new ByteArrayOutputStream();
		Encoder encoder = EncoderBuilder.newEncoder().create();// .prepareOutputStream(output1, message.length());
		encoder.prepareOutputStream(output1, message.length()).Code(input1, output1, -1, -1, null);
		System.out.println("[" + message.length() + "]" + message);
		final String compressed = Base64.encode(output1.toByteArray()).replaceAll("\n", "");
		System.out.println("[" + compressed.length() + "]" + compressed);

		ByteArrayInputStream input2 = new ByteArrayInputStream(Base64.decode(compressed, Base64.DEFAULT));
		ByteArrayOutputStream output2 = new ByteArrayOutputStream();

		SevenZip.Compression.LZMA.Decoder decoder = new SevenZip.Compression.LZMA.Decoder();

		if (!decoder.Code(input2, output2/* , outSize */))
			throw new Exception("Error in data stream");

		final String decompressed = new String(output2.toByteArray());
		System.out.println("[" + decompressed.length() + "]" + decompressed);
		System.out.println(decompressed.equals(message));

	}

}
