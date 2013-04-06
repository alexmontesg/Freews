package com.persistence;

import com.model.User;

/**
 * Defines the operations that can be done with {@link User}s in the persistence
 * level
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 05/09/2012
 * @version 1.4
 */
public interface UserDataService {

	/**
	 * Retrieves a user with the given username and password
	 * 
	 * @param username
	 *            The username of the {@link User} to be retrieved
	 * @param password
	 *            The password of the {@link User} to be retrieved
	 * @return The matching {@link User}, <code>null</code> if the password does
	 *         not match the username
	 */
	public User getUser(String username, String password);

	/**
	 * Adds a {@link User} to the database
	 * 
	 * @param user
	 *            The {@link User} to be added
	 */
	public void addUser(User user);

	/**
	 * Retrives a {@link User} with the given Facebook's identifier
	 * 
	 * @param facebookId
	 *            The Facebook's identifier
	 * @return The matching {@link User}, <code>null</code> if it does not exist
	 */
	public User getUserByFacebookId(String facebookId);

	/**
	 * Checks if a mail is being used by any {@link User}
	 * 
	 * @param mail
	 *            The mail to be checked
	 * @return <code>true</code> if the mail is being used, <code>false</code>
	 *         otherwise
	 */
	public boolean existsMail(String mail);

	/**
	 * Checks if a username is being used by any {@link User}
	 * 
	 * @param username
	 *            The username to be checked
	 * @return <code>true</code> if the username is being used,
	 *         <code>false</code> otherwise
	 */
	public boolean existsUsername(String username);

	/**
	 * Deletes an {@link User} with the given username
	 * 
	 * @param username
	 *            The username of the {@link User} to be deleted
	 */
	public void deleteUser(String username);

	/**
	 * Gets the amount of registered {@link User}s
	 * 
	 * @return The amount of registered {@link User}s
	 */
	public int numberOfUsers();

	/**
	 * Deletes all the {@link User}s
	 */
	public void deleteAll();

}
