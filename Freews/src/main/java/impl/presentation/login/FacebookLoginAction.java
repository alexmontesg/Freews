package impl.presentation.login;

import impl.presentation.WebServiceAction;

import java.io.IOException;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.struts2.interceptor.SessionAware;
import org.json.JSONException;

import util.facebook.Facebook;

import com.model.User;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

/**
 * Logs in a {@link User} using Facebook
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 26/07/2012        
 * @version 1.4
 */
public class FacebookLoginAction extends WebServiceAction implements
		SessionAware {

	private static final long serialVersionUID = 1206121370358209624L;
	private String code;
	private Map<String, Object> session;

	@Override
	public String execute() {
		log.debug("Ejecutando FacebookLoginAction");
		String accessToken;
		try {
			accessToken = Facebook.connect(code);
			String id = Facebook.getId(accessToken);
			User user = callWebService(id);
			if (user != null) {
				session.put("user", user);
				return SUCCESS;
			} else {
				session.put("preUser", Facebook.getUser(accessToken));
				return "register";
			}
		} catch (IOException e) {
			return ERROR;
		} catch (JSONException e) {
			return ERROR;
		}
	}

	private User callWebService(String id) {
		WebResource webResource = getWebResource("user/getFacebookUser");
		MultivaluedMap<String, String> params = populate("id", id);
		User user = webResource
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(
						new GenericType<User>() {
						}, params);
		return user;
	}

	@Override
	public void validate() {
		if (code == null || code.trim().isEmpty()) {
			addActionError(getText("invalid.code"));
		}
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
