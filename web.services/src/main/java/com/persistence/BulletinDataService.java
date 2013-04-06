package com.persistence;

import com.model.Bulletin;

/**
 * Defines the operations that can be done with {@link Bulletin}s in the
 * persistence level
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 05/09/2012
 * @version 1.3
 */
public interface BulletinDataService {

	/**
	 * Adds a {@link Bulletin} to the database
	 * 
	 * @param bulletin
	 *            The {@link Bulletin} to be inserted
	 * @return The id of the {@link Bulletin}
	 */
	public String insertBulletin(Bulletin bulletin);

	/**
	 * Retrieves a {@link Bulletin} with a specific identifier
	 * 
	 * @param id
	 *            The identifier of the desired {@link Bulletin}
	 * @return The {@link Bulletin} matching the id, <code>null</code> if it
	 *         does not exist
	 */
	public Bulletin getBulletinByID(String id);

}
