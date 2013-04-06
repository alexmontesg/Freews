package util.marshall;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Helper class to unmarshall parameters received by the web services
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 03/02/2013
 * @version 1.0
 */
public class Unmarshaller {

	/**
	 * Converts a String into array separating by semicolon
	 * 
	 * @param string
	 *            The String to transform
	 * @return The resulting array
	 */
	public static String[] stringToArray(String string) {
		return string.split(";");
	}

	/**
	 * Converts a String into a {@link Map} using the semicolon as separator of
	 * entries and the colon as the separator of pairs key, value
	 * 
	 * @param string
	 *            The String to be converted
	 * @return The resulting {@link Map}
	 */
	public static Map<String, String> stringToMap(String string) {
		Map<String, String> map = new HashMap<String, String>();
		String[] entries = string.split(";");
		for (String entry : entries) {
			String[] pair = entry.split(":", 2);
			map.put(pair[0], pair[1]);
		}
		return map;
	}

	/**
	 * Converts a String into a {@link Map} of {@link Vector}s using the
	 * semicolon as separator of entries, the colon as the separator of pairs
	 * key, value and the comma as separator of values in the same entry
	 * 
	 * @param string
	 *            The String to be converted
	 * @return The resulting {@link Map}
	 */
	public static Map<String, Vector<String>> stringToVectorMap(String string) {
		Map<String, Vector<String>> map = new HashMap<String, Vector<String>>();
		String[] entries = string.split(";");
		for (String entry : entries) {
			String[] pair = entry.split(":", 2);
			String[] tags = pair[1].split(",");
			Vector<String> vector = new Vector<String>();
			for (String tag : tags) {
				vector.add(tag);
			}
			map.put(pair[0], vector);
		}
		return map;
	}

	/**
	 * Converts a String into a {@link Vector} using the semicolon as separator
	 * 
	 * @param string
	 *            The String to be converted
	 * @return The resulting {@link Vector}
	 */
	public static Vector<String> stringToVector(String string) {
		String[] tags = stringToArray(string);
		Vector<String> vector = new Vector<String>(tags.length);
		for (String tag : tags) {
			vector.add(tag);
		}
		return vector;
	}

}
