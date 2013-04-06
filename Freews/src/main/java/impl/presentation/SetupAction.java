package impl.presentation;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

/**
 * Adds data to the database for testing purposes
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 23/07/2012
 * @version 1.3
 */
public class SetupAction extends WebServiceAction {

	private static final long serialVersionUID = 4014337487690524923L;

	@Override
	public String execute() {
		addCategories();
		addUsers();
		return SUCCESS;
	}

	private void addUsers() {
		registerAdmin("admin", "admin", "Administrador", "Del Sistema",
				"admin@freews.com", null, null);
		register("freews", "freews", "Usuario", "Del Sistema",
				"freews@freews.com", null, null);
	}

	private boolean register(String username, String password,
			String firstName, String lastName, String mail, String country,
			String facebookId) {
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

	private boolean registerAdmin(String username, String password,
			String firstName, String lastName, String mail, String country,
			String facebookId) {
		WebResource webResource = getWebResource("user/registerAdmin");
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

	private void addCategories() {
		addCategory("Social");
		addCategory("Política");
	}

	private void addCategory(String category) {
		WebResource wrAdd = getWebResource("category/add");
		MultivaluedMap<String, String> params = populate("category", category);
		wrAdd.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(
				ClientResponse.class, params);
	}

}
