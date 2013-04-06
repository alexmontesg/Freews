package util.lang;

/**
 * Converts a Locale String with country code into a language code only
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 17/09/2012
 * @version 1.0
 */
public class LangConverter {

	public final static String ES = "es";
	public final static String EN = "en";
	public final static String PT = "pt";

	/**
	 * Converts a Locale String with country code into a language code only
	 * 
	 * @param lang
	 *            Locale String with country code
	 * @return Language code
	 */
	public static String convert(String lang) {
		if (lang.toLowerCase().startsWith("es")) {
			return ES;
		}
		if (lang.startsWith("pt")) {
			return PT;
		}
		return EN;
	}
}
