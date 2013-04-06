package util.facebook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import util.config.Config;

import com.model.User;
import com.visural.common.IOUtil;

/**
 * Interacts with Facebook
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @version 1.3
 * @since 26/07/2012
 */
public class Facebook {

	/**
	 * Logs in a {@link User} using Facebook
	 * 
	 * @param code
	 *            The token returned by Facebook after a login request
	 * @return A token to identify the logged {@link User}
	 * @throws IOException
	 *             If the Facebook response cannot be retrieved
	 * @throws RuntimeException
	 *             If the Facebook response is something unexpected
	 * @throws IllegalArgumentException
	 *             If the parameter is null or empty
	 */
	public static String connect(String code) throws IOException {
		String accessToken = null;
		if (code == null || code.isEmpty()) {
			throw new IllegalArgumentException("Código inválido");
		}
		URL url = new URL(getAuthURL(code));
		String result = readURL(url);
		String[] pairs = result.split("&");
		for (String pair : pairs) {
			String[] kv = pair.split("=");
			if (kv.length != 2) {
				throw new RuntimeException("Unexpected auth response");
			} else {
				if (kv[0].equalsIgnoreCase("access_token")) {
					accessToken = kv[1];
					break;
				}
			}
		}
		return accessToken;
	}

	/**
	 * Gets a {@link User}'s Facebook id
	 * 
	 * @param accessToken
	 *            The token that identifies a {@link User}
	 * @return The id of the logged {@link User}
	 * @throws MalformedURLException
	 *             If the Facebook response cannot be retrieved
	 * @throws JSONException
	 *             If the Facebook response is something unexpected
	 * @throws IOException
	 *             If the Facebook response cannot be retrieved
	 */
	public static String getId(String accessToken)
			throws MalformedURLException, JSONException, IOException {
		JSONObject resp = new JSONObject(IOUtil.urlToString(new URL(
				"https://graph.facebook.com/me?access_token=" + accessToken)));
		String id = resp.getString("id");
		return id;
	}

	/**
	 * Gets a {@link User} using Facebook
	 * 
	 * @param accessToken
	 *            The token that identifies a {@link User}
	 * @return The logged {@link User}
	 * @throws MalformedURLException
	 *             If the Facebook response cannot be retrieved
	 * @throws JSONException
	 *             If the Facebook response is something unexpected
	 * @throws IOException
	 *             If the Facebook response cannot be retrieved
	 */
	public static User getUser(String accessToken)
			throws MalformedURLException, JSONException, IOException {
		User user = new User();
		JSONObject resp = new JSONObject(IOUtil.urlToString(new URL(
				"https://graph.facebook.com/me?access_token=" + accessToken)));
		user.setFacebookId(resp.getString("id"));
		user.setFirstName(resp.getString("first_name"));
		user.setLastName(resp.getString("last_name"));
		user.setMail(resp.getString("email"));
		return user;
	}

	/**
	 * Gets the url to log in a {@link User} into Facebook
	 * 
	 * @param authCode
	 *            The token returned by Facebook after a login request
	 * @return The URL to log in a {@link User} into Facebook
	 */
	private static String getAuthURL(String authCode) {
		return "https://graph.facebook.com/oauth/access_token?client_id="
				+ Config.getProperty("client.id") + "&redirect_uri="
				+ Config.getProperty("redirect.uri") + "&client_secret="
				+ Config.getProperty("secret") + "&code=" + authCode;
	}

	/**
	 * Reads the data from an URL
	 * 
	 * @param url
	 *            The URL to be read
	 * @return The data from an URL converted into a String
	 * @throws IOException
	 *             If the URL cannot be retrieved
	 */
	private static String readURL(URL url) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = url.openStream();
		int r;
		while ((r = is.read()) != -1) {
			baos.write(r);
		}
		String result = new String(baos.toByteArray());
		baos.close();
		return result;
	}
}