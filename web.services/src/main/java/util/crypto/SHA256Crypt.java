package util.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Encrypts a word using SHA-256 algorithm
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 23/07/2012
 * @version 1.0
 */
public class SHA256Crypt {

	/**
	 * Encrypts a word using SHA-256 algorithm
	 * 
	 * @param word
	 *            The word to be encrypted
	 * @return The word encrypted using SHA-256
	 * @throws NoSuchAlgorithmException
	 *             If the SHA-256 algorithm cannot be found
	 */
	public static String crypt(String word) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		return toHexString(md.digest(word.getBytes()));
	}

	/**
	 * Converts a byte array into a hexadecimal string
	 * 
	 * @param byteData
	 *            The array to be converted
	 * @return The hexadecimal String
	 */
	private static String toHexString(byte[] byteData) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			buffer.append(Integer.toHexString((byteData[i] & 0xff)));
		}
		return buffer.toString();
	}
}
