package com.business;

import com.model.Bulletin;

/**
 * Defines the operations that can be done with {@link Bulletin}s
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 05/09/2012
 * @version 1.3
 */
public interface BulletinManagerService {

	/**
	 * Appends {@link com.model.Video Video}s in order to create a
	 * {@link Bulletin}
	 * 
	 * @param ids
	 *            The identifiers of the {@link com.model.Video Video}s
	 * @param category
	 *            The category of the resulting {@link Bulletin}
	 * @param user
	 *            The username of the {@link com.model.User User} who makes the
	 *            request
	 * @param title
	 *            The {@link Bulletin}'s title
	 * @param lang
	 *            The lang of the subtitles
	 * @return The id of the resulting {@link Bulletin}
	 */
	public String createBulletin(String[] ids, String category, String user,
			String title, String lang);

	/**
	 * Gets a {@link Bulletin} by its identifier
	 * 
	 * @param id
	 *            The identifier of the {@link Bulletin}
	 * @return The required {@link Bulletin}
	 */
	public Bulletin getBulletinByID(String id);

}
