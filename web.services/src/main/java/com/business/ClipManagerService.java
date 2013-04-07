package com.business;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

import com.model.Video;

/**
 * Defines the operations that can be done with {@link Video clips}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 05/09/2012
 * @version 1.2
 */
public interface ClipManagerService {

	/**
	 * Gets all the {@link Video clips} available
	 * 
	 * @return A {@link Collection} containing all the {@link Video clips}
	 */
	public Collection<Video> getClips();

	/**
	 * Uploads a {@link Video clip}
	 * 
	 * @param description
	 *            The descriptions of the {@link Video clip}
	 * @param headline
	 *            The headlines of the {@link Video clip}
	 * @param id
	 *            The id of the {@link Video raw video} where the {@link Video
	 *            clip} came from
	 * @param clip
	 *            The file to be uploaded
	 * @param takenDate
	 *            The date when the {@link Video clip} was taken
	 * @param lat
	 *            The lattitude where the {@link Video clip} was taken
	 * @param lon
	 *            The longitude where the {@link Video clip} was taken
	 * @param subtitles
	 *            The subtitles of the {@link Video clip}
	 */
	public void uploadClip(Map<String, String> description,
			Map<String, String> headline, String id, File clip, Date takenDate,
			Double lat, Double lon, Map<String, String> subtitles);

	/**
	 * Gets all the {@link Video clips} a {@link com.model.User User} has
	 * uploaded
	 * 
	 * @param username
	 *            The name of the {@link com.model.User User} to query for
	 * 
	 * @return A {@link Collection} containing all the {@link Video clips} a
	 *         {@link com.model.User User} has uploaded
	 */
	public Collection<Video> getClipsByUser(String username);

	/**
	 * Gets a {@link Video clip} by its identifier
	 * 
	 * @param id
	 *            The identifier of the {@link Video clip}
	 * @return <code>null</code> if the {@link Video clip} does not exist, the
	 *         {@link Video clip} otherwise
	 */
	public Video getClipByID(String id);

	/**
	 * Deletes a {@link Video clip} by its identifier
	 * 
	 * @param id
	 *            The identifier of the {@link Video clip}
	 */
	public void deleteClip(String id);

	/**
	 * Gets {@link Video clips} by their identifier
	 * 
	 * @param ids
	 *            The identifiers of the {@link Video clips}
	 * @return A {@link Collection} containing the requested {@link Video clips}
	 */
	public Collection<Video> getClipsByID(String[] ids);

	/**
	 * Performs a free-text query
	 * 
	 * @param query
	 *            The string to be searched
	 * @param lang
	 *            The language of the query
	 * @return A {@link Collection} containing the matching {@link Video clips}
	 */
	public Collection<Video> freeTextSearch(String query, String lang);

	/**
	 * Edits a {@link Video clip}
	 * 
	 * @param description
	 *            The descriptions of the {@link Video clip}
	 * @param headline
	 *            The headlines of the {@link Video clip}
	 * @param tags
	 *            The tags describing the {@link Video clip}
	 * @param id
	 *            The id of the {@link Video raw video} where the {@link Video
	 *            clip} came from
	 * @param takenDate
	 *            The date when the {@link Video clip} was taken
	 * @param lat
	 *            The lattitude where the {@link Video clip} was taken
	 * @param lon
	 *            The longitude where the {@link Video clip} was taken
	 */
	public void editClip(Map<String, String> descriptions,
			Map<String, String> headlines, Map<String, Vector<String>> tags,
			String id, Date takenDate, Double lat, Double lon);

	/**
	 * Adds the rating of a {@link Video clip} by a specific {@link User}
	 * 
	 * @param video_id
	 *            The identifier of the {@link Video} to rate
	 * @param user_id
	 *            The identifier of the {@link User} that rated the
	 *            {@link Video}
	 * @param score
	 *            The score given by the {@link User}
	 */
	public void rateClip(String video_id, String user_id, double score);

}
