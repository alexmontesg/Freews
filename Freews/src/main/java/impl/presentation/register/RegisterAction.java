package impl.presentation.register;

import impl.presentation.WebServiceAction;

import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.struts2.interceptor.SessionAware;

import util.mail.MailValidator;

import com.model.User;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;



/**
 * Registers a {@link User}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 23/07/2012
 * @version 1.5
 */
public class RegisterAction extends WebServiceAction implements SessionAware {

	private static final long serialVersionUID = 2991462032889596410L;
	private String username, password, firstName, lastName, mail, country;
	private Map<String, Object> session;

	@Override
	public String execute() {
		log.debug("Ejecutando RegisterAction para el usuario " + username);
		String facebookId = null;
		if (session.containsKey("preUser")) {
			facebookId = ((User) session.get("preUser")).getFacebookId();
			session.remove("preUser");
		}
		if (callWebService(facebookId)) {
			session.put("user", callWebServiceLogin());
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	private User callWebServiceLogin() {
		WebResource webResource = getWebResource("user/login");
		MultivaluedMap<String, String> params = populate("username", username,
				"password", password);
		User user = webResource
				.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(
						new GenericType<User>() {
						}, params);
		return user;
	}

	private boolean callWebService(String facebookId) {
		WebResource webResource = getWebResource("user/register");
		MultivaluedMap<String, String> params = populate("username", username,
				"password", password, "firstName", firstName, "lastName",
				lastName, "mail", mail, "country", country, "facebookId",
				facebookId);
		Boolean result = webResource.type(
				MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(
				new GenericType<Boolean>() {
				}, params);
		return result;
	}

	@Override
	public void validate() {
		if (!areFilledFields(username)) {
			addFieldError("username", getText("invalid.username"));
		}
		if (!areFilledFields(password)) {
			addFieldError("password", getText("invalid.password"));
		}
		if (!areFilledFields(firstName)) {
			addFieldError("firstName", getText("invalid.firstName"));
		}
		if (!areFilledFields(lastName)) {
			addFieldError("lastName", getText("invalid.lastName"));
		}
		if (!areFilledFields(mail) || !MailValidator.isValidMail(mail)) {
			addFieldError("mail", getText("invalid.mail"));
		}
		if (!areFilledFields(country)) {
			addFieldError("country", getText("invalid.country"));
		}
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setCountry(String country) {
		this.country = country;
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