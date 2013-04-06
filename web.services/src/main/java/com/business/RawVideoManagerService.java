package com.business;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import com.model.Video;

/**
 * Defines the operations that can be done with {@link Video raw videos}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 05/09/2012
 * @version 1.1
 */
public interface RawVideoManagerService {

	/**
	 * Gets a {@link Video raw video} by its identifier
	 * 
	 * @param id
	 *            The identifier of the {@link Video raw video}
	 * @return <code>null</code> if the {@link Video raw video} does not exist,
	 *         the {@link Video raw video} otherwise
	 */
	public Video getRawVideoByID(String id);

	/**
	 * Gets all the {@link Video raw videos} available
	 * 
	 * @return A {@link Collection} containing all the {@link Video raw videos}
	 */
	public Collection<Video> getRawVideos();

	/**
	 * Uploads a {@link Video raw video}
	 * 
	 * @param description
	 *            The description of the {@link Video raw video}
	 * @param headline
	 *            The headline of the {@link Video raw video}
	 * @param tags
	 *            A {@link Vector} containing the tags describing the
	 *            {@link Video raw video}
	 * @param user
	 *            The username of the {@link Video}'s owner
	 * @param clip
	 *            The file to be uploaded
	 * @param date
	 *            The date when the {@link Video raw video} was taken
	 * @param lat
	 *            The latitude where the {@link Video raw video} was taken
	 * @param lon
	 *            The longitude where the {@link Video raw video} was taken
	 * @param lang
	 *            The locale in which the request has been made
	 */
	public void uploadRaw(String description, String headline,
			Vector<String> tagsVector, String username, File clip, Date date,
			Double lat, Double lon, String lang);

	/**
	 * Deletes a {@link Video raw video}
	 * 
	 * @param id
	 *            The identifier of the {@link Video raw video} to be deleted
	 */
	public void deleteRawVideo(String id);

}
