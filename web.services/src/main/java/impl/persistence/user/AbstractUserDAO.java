package impl.persistence.user;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import com.model.User;
import com.persistence.UserDataService;

/**
 * Abstract implementation of {@link UserDataService} that helps with the
 * management of <a href="http://www.springsource.org/spring-data">Spring
 * Data</a> and MongoDB
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 02/03/2013
 * @version 1.0
 */
public abstract class AbstractUserDAO implements UserDataService {

	protected final String USERS = "users";
	private ConfigurableApplicationContext ctx;

	public final User getUser(String username, String password) {
		User user = getUserImpl(username, password, getOperationHandler());
		ctx.close();
		return user;
	}

	/**
	 * Implementation of the {@link #getUser(String, String)} method to use
	 * Spring Data with MongoDB
	 * 
	 * @param username
	 *            The username of the {@link User} to be retrieved
	 * @param password
	 *            The password of the {@link User} to be retrieved
	 * @param op
	 *            The {@link MongoOperations} that performs the operation
	 * @return The matching {@link User}, <code>null</code> if the password does
	 *         not match the username
	 */
	protected abstract User getUserImpl(String username, String password,
			MongoOperations op);

	public final void addUser(User user) {
		addUserImpl(user, getOperationHandler());
		ctx.close();
	}

	/**
	 * Implementation of the {@link #addUser(User)} method to use Spring Data
	 * with MongoDB
	 * 
	 * @param user
	 *            The {@link User} to be added
	 * @param op
	 *            The {@link MongoOperations} that performs the operation
	 */
	protected abstract void addUserImpl(User user, MongoOperations op);

	public final User getUserByFacebookId(String facebookId) {
		User user = getUserByFacebookIdImpl(facebookId, getOperationHandler());
		ctx.close();
		return user;
	}

	/**
	 * Implementation of the {@link #getUserByFacebookId(String)} method to use
	 * Spring Data with MongoDB
	 * 
	 * @param facebookId
	 *            The {@link User}'s Facebook identifier
	 * @param op
	 *            The {@link MongoOperations} that performs the operation
	 * @return The matching {@link User} if it exists, <code>null</code>
	 *         otherwise
	 */
	protected abstract User getUserByFacebookIdImpl(String facebookId,
			MongoOperations op);

	public final boolean existsMail(String mail) {
		boolean result = existsMailImpl(mail, getOperationHandler());
		ctx.close();
		return result;
	}

	/**
	 * Implementation of the {@link #existsMail(String)} method to use Spring
	 * Data with MongoDB
	 * 
	 * @param mail
	 *            The mail to be checked
	 * @param op
	 *            The {@link MongoOperations} that performs the operation
	 * @return <code>true</code> if the mail exists, <code>false</code>
	 *         otherwise
	 */
	protected abstract boolean existsMailImpl(String mail, MongoOperations op);

	public boolean existsUsername(String username) {
		boolean result = existsUsernameImpl(username, getOperationHandler());
		ctx.close();
		return result;
	}

	/**
	 * Implementation of the {@link #existsUsername(String)} method to use
	 * Spring Data with MongoDB
	 * 
	 * @param username
	 *            The username to be checked
	 * @param op
	 *            The {@link MongoOperations} that performs the operation
	 * @return <code>true</code> if the username exists, <code>false</code>
	 *         otherwise
	 */
	protected abstract boolean existsUsernameImpl(String username,
			MongoOperations op);

	public final void deleteUser(String username) {
		deleteUserImpl(username, getOperationHandler());
		ctx.close();
	}

	/**
	 * Implementation of the {@link #deleteUser(String)} method to use Spring
	 * Data with MongoDB
	 * 
	 * @param username
	 *            The username of the {@link User} to be deleted
	 * @param op
	 *            The {@link MongoOperations} that performs the operation
	 */
	protected abstract void deleteUserImpl(String username, MongoOperations op);

	public final int numberOfUsers() {
		int result = numberOfUsersImpl(getOperationHandler());
		ctx.close();
		return result;
	}

	/**
	 * Implementation of the {@link #numberOfUsers()} method to use Spring Data
	 * with MongoDB
	 * 
	 * @param op
	 *            The {@link MongoOperations} that performs the operation
	 * @return The number of users in the database
	 */
	protected abstract int numberOfUsersImpl(MongoOperations op);

	public final void deleteAll() {
		deleteAllImpl(getOperationHandler());
		ctx.close();
	}

	/**
	 * Implementation of the {@link #deleteAll()} method to use Spring Data with
	 * MongoDB
	 * 
	 * @param op
	 *            The {@link MongoOperations} that performs the operation
	 */
	protected abstract void deleteAllImpl(MongoOperations op);

	/**
	 * Gets the {@link MongoOperations} item for this class
	 * 
	 * @return The {@link MongoOperations} item for this class
	 */
	private MongoOperations getOperationHandler() {
		ctx = new GenericXmlApplicationContext("mongo-config.xml");
		return (MongoOperations) ctx.getBean("mongoTemplate");
	}

}
