package impl.business.usermanager;

import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

import util.crypto.SHA256Crypt;
import util.mail.MailValidator;

import com.business.UserManagerService;
import com.model.User;
import com.persistence.UserDataService;

/**
 * Implementation of {@link UserManagerService}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 23/07/2012
 * @version 1.5
 */
public class UserManager implements UserManagerService {

	private static Logger log = Logger.getLogger(UserManagerService.class);
	private UserDataService userDataService;

	public void setUserDataService(UserDataService userDataService) {
		this.userDataService = userDataService;
	}

	public User getUser(String username, String password) {
		log.debug("Entrando en getUser");
		User user = null;
		try {
			user = userDataService.getUser(username,
					SHA256Crypt.crypt(password));
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			log.error(e.getCause() != null ? e.getCause().getMessage()
					: "Causa desconocida");
		} catch (NoSuchAlgorithmException e) {
			log.error("No se encontr� el algoritmo de encriptaci�n");
			log.error(e.getMessage());
		}
		return user;
	}

	public boolean register(String username, String password, String firstName,
			String lastName, String mail, String country, String facebookId,
			Boolean admin) {
		log.debug("Entrando en register");
		try {
			if (MailValidator.isValidMail(mail)
					&& !userDataService.existsMail(mail)
					&& !userDataService.existsUsername(username)) {
				User user = new User();
				user.setUsername(username);
				user.setPassword(SHA256Crypt.crypt(password));
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setMail(mail);
				user.setCountry(country);
				user.setFacebookId(facebookId);
				user.setAdmin(admin);
				userDataService.addUser(user);
				return true;
			}
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			log.error(e.getCause() != null ? e.getCause().getMessage()
					: "Causa desconocida");
		} catch (NoSuchAlgorithmException e) {
			log.error("No se encontró el algoritmo de encriptación");
			log.error(e.getMessage());
		}
		return false;
	}

	public User getFacebookUser(String facebookId) {
		log.debug("Entrando en getFacebookUser con el id " + facebookId);
		User user = null;
		try {
			user = userDataService.getUserByFacebookId(facebookId);
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			log.error(e.getCause() != null ? e.getCause().getMessage()
					: "Causa desconocida");
		}
		return user;
	}

}
