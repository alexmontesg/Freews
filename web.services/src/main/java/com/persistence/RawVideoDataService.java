package com.persistence;

import java.util.Collection;

import com.model.Video;

/**
 * Defines the operations that can be done with {@link Video raw videos} in the
 * persistence level
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 05/09/2012
 * @version 1.3
 */
public interface RawVideoDataService {

	/**
	 * Adds a {@link Video raw video} to the database
	 * 
	 * @param video
	 *            The {@link Video raw video} to be added
	 * @return The identifier of the added {@link Video raw video}
	 */
	public String insertRawVideo(Video video);

	/**
	 * Retrieves all the {@link Video raw videos} from the database
	 * 
	 * @return A {@link Collection} containing all the {@link Video raw videos}
	 */
	public Collection<Video> getRawVideos();

	/**
	 * Retrieves a {@link Video raw video} with a speficid identifier
	 * 
	 * @param id
	 *            The identifier of the desired {@link Video raw video}
	 * @return The {@link Video raw video} matching the id, <code>null</code> if
	 *         it does not exist
	 */
	public Video getRawVideoByID(String id);

	/**
	 * Deletes a {@link Video raw video} from the database
	 * 
	 * @param id
	 *            The identifier of the {@link Video raw video} to be deleted
	 */
	public void deleteRawVideo(String id);

}
