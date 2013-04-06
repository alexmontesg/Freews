package util.mail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Checks if a String is a valid mail
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 02/08/2012
 * @version 1.0
 */
public class MailValidator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*"
			+ "@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/**
	 * Checks if a String is a valid mail
	 * 
	 * @param mail
	 *            String to be checked
	 * @return <code>true</code> if the String is a valid mail,
	 *         <code>false</code> otherwise
	 */
	public static boolean isValidMail(final String mail) {
		Matcher matcher = Pattern.compile(EMAIL_PATTERN).matcher(mail);
		return matcher.matches();
	}

}
