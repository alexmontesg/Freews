package com.business;

import com.model.User;

/**
 * Defines the operations that can be done with {@link User}s
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 23/07/2012
 * @version 1.3
 */
public interface UserManagerService {

	/**
	 * Checks the password of the given {@link User}
	 * 
	 * @param username
	 *            The username to be checked
	 * @param password
	 *            The password to be checked
	 * @return <code>null</code> if the username or password are incorrect, the
	 *         {@link User} otherwise
	 */
	public User getUser(String username, String password);

	/**
	 * Registers a {@link User}
	 * 
	 * @param username
	 *            The username that the {@link User} wants
	 * @param password
	 *            The password that the {@link User} wants
	 * @param firstName
	 *            The first name of the {@link User}
	 * @param lastName
	 *            The last name of the {@link User}
	 * @param mail
	 *            The mail of the {@link User}
	 * @param country
	 *            The country of the {@link User}
	 * @param facebookId
	 *            The Facebook's identifier of the {@link User}
	 * @param admin
	 *            The condition of administrator of the {@link User}
	 * @return <code>true</code> if the register was successfully completed,
	 *         <code>false</code> otherwise
	 */
	public boolean register(String username, String password, String firstName,
			String lastName, String mail, String country, String facebookId,
			Boolean admin);

	/**
	 * Checks if the Facebook's identifier exists on the database
	 * 
	 * @param facebookId
	 *            The Facebook's identifier to be checked
	 * @return <code>null</code> if the facebookId exists on the database, the
	 *         {@link User} otherwise
	 */
	public User getFacebookUser(String facebookId);

}
