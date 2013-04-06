package impl.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.business.UserManagerService;
import com.model.User;

/**
 * Class implementing web services related to {@link User} management
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 03/02/2013
 * @version 1.0
 */
@Path("ws/user")
public class UserServices extends FreewsServices {

	private static UserManagerService userManagerService;

	public void setUserManagerService(UserManagerService userManagerService) {
		UserServices.userManagerService = userManagerService;
	}

	/**
	 * Gets a {@link User} by its Facebook identifier
	 * 
	 * @param id
	 *            The Facebook identifier of the user
	 * @return The matching {@link User}
	 */
	@POST
	@Path("getFacebookUser")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public User getFacebookUser(@FormParam("id") String id) {
		log.debug("Calling getFacebookUser service");
		return userManagerService.getFacebookUser(id);
	}

	/**
	 * Logs in a {@link User}
	 * 
	 * @param username
	 *            The username of the {@link User}
	 * @param password
	 *            The password of the {@link User}
	 * @return The matching {@link User}
	 */
	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public User login(@FormParam("username") String username,
			@FormParam("password") String password) {
		log.debug("Calling login service");
		return userManagerService.getUser(username, password);
	}

	/**
	 * Registers a normal {@link User}
	 * 
	 * @param username
	 *            The desired username
	 * @param password
	 *            The password the {@link User} will user to log in
	 * @param firstName
	 *            The first name of the {@link User}
	 * @param lastName
	 *            The last name of the {@link User}
	 * @param mail
	 *            The mail of the {@link User}
	 * @param country
	 *            The country of the {@link User}
	 * @param facebookId
	 *            The Facebook identifier of the {@link User}
	 * @return <code>true</code if the {@link User} was sucessfully registered,
	 *         <code>false</code> otherwise
	 */
	@POST
	@Path("register")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean register(@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName,
			@FormParam("mail") String mail,
			@FormParam("country") String country,
			@FormParam("facebookId") String facebookId) {
		log.debug("Calling register service");
		return userManagerService.register(username, password, firstName,
				lastName, mail, country, facebookId, false);
	}

	/**
	 * Registers an admin
	 * 
	 * @param username
	 *            The desired username
	 * @param password
	 *            The password the {@link User} will user to log in
	 * @param firstName
	 *            The first name of the {@link User}
	 * @param lastName
	 *            The last name of the {@link User}
	 * @param mail
	 *            The mail of the {@link User}
	 * @param country
	 *            The country of the {@link User}
	 * @param facebookId
	 *            The Facebook identifier of the {@link User}
	 * @return <code>true</code if the {@link User} was sucessfully registered,
	 *         <code>false</code> otherwise
	 */
	@POST
	@Path("registerAdmin")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean registerAdmin(@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName,
			@FormParam("mail") String mail,
			@FormParam("country") String country,
			@FormParam("facebookId") String facebookId) {
		log.debug("Calling registerAdmin service");
		return userManagerService.register(username, password, firstName,
				lastName, mail, country, facebookId, true);
	}
}
