package impl.presentation.login;

import impl.presentation.WebServiceAction;

import java.util.Collection;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.struts2.interceptor.SessionAware;

import com.model.Category;
import com.model.User;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

/**
 * Logs in a {@link User}
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 23/07/2012
 * @version 1.4
 */
public class LoginAction extends WebServiceAction implements SessionAware {

	private static final long serialVersionUID = -6759882623134364308L;
	private Map<String, Object> session;
	private String username, password;

	@Override
	public String execute() {
		log.debug("Ejecutando LoginAction para el usuario " + username);
		User user = callWebService();
		if (user != null) {
			session.put("user", user);
			session.put("categories", callWebServiceCategories());
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	private Collection<Category> callWebServiceCategories() {
		WebResource wrGet = getWebResource("category/getAll");
		return wrGet.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).get(
				new GenericType<Collection<Category>>() {
				});
	}

	private User callWebService() {
		WebResource webResource = getWebResource("user/login");
		MultivaluedMap<String, String> params = populate("username", username,
				"password", password);
		User user = null;
		try {
			user = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
					.post(new GenericType<User>() {
					}, params);
		} catch (UniformInterfaceException e) {
			log.error("Wrong login for user " + username, e);
		}
		return user;
	}

	@Override
	public void validate() {
		if (username == null || username.trim().isEmpty()) {
			addFieldError("username", getText("invalid.username"));
		}
		if (password == null || password.trim().isEmpty()) {
			addFieldError("password", getText("invalid.password"));
		}
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
