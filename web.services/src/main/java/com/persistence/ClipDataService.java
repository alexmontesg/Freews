package com.persistence;

import java.util.Collection;

import com.model.Rating;
import com.model.Video;

/**
 * Defines the operations that can be done with {@link Video clips} in the
 * persistence level
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 05/09/2012
 * @version 1.3
 */
public interface ClipDataService {

	/**
	 * Retrieves all the {@link Video clips} from the database
	 * 
	 * @return A {@link Collection} containing all the {@link Video clips}
	 */
	public Collection<Video> getClips();

	/**
	 * Retrieves all the {@link Video clips} uploaded by a
	 * {@link com.model.User User}
	 * 
	 * @param username
	 *            The username of the desired {@link com.model.User User}
	 * @return A {@link Collection} containing all the {@link Video clips}
	 *         uploaded by the {@link com.model.User User}
	 */
	public Collection<Video> getClipsByUser(String username);

	/**
	 * Retrieves a {@link Video clip} with a speficid identifier
	 * 
	 * @param id
	 *            The identifier of the desired {@link Video clip}
	 * @return The {@link Video clip} matching the id, <code>null</code> if it
	 *         does not exist
	 */
	public Video getClipByID(String id);

	/**
	 * Performs a query in all the indexed fields
	 * 
	 * @param query
	 *            The string to search
	 * @param lang
	 *            The language of the query
	 * @return A {@link Collection} containing the {@link Video clips} matching
	 *         the query
	 */
	public Collection<Video> freeTextSearch(String query, String lang);

	/**
	 * Adds a {@link Video clip} to the database
	 * 
	 * @param video
	 *            The {@link Video clip} to be added
	 * @return The identifier of the added {@link Video clip}
	 */
	public String insertClip(Video video);

	/**
	 * Deletes a {@link Video clip} from the database
	 * 
	 * @param id
	 *            The identifier of the {@link Video clip} to be deleted
	 */
	public void deleteClip(String id);

	/**
	 * Deletes all the {@link Video clips} from the database
	 */
	public void deleteAll();

	/**
	 * Adds a {@link Rating}
	 * 
	 * @param rating
	 *            The {@link Rating} to be added
	 */
	public void rate(Rating rating);

}
