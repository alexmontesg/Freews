package impl.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import util.marshall.Unmarshaller;

import com.business.ClipManagerService;
import com.model.Video;
import com.sun.jersey.multipart.FormDataParam;

/**
 * Class implementing web services related to {@link Video clips} management
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 03/02/2013
 * @version 1.0
 */
@Path("ws/clip")
public class ClipServices extends FreewsServices {

	private static ClipManagerService clipManagerService;

	public void setClipManagerService(ClipManagerService clipManagerService) {
		ClipServices.clipManagerService = clipManagerService;
	}

	/**
	 * Gets a {@link Collection} of {@link Video}s by their id
	 * 
	 * @param ids
	 *            The ids of the {@link Video}s to get, separated by semicolons
	 * @return The matching {@link Video}s
	 */
	@POST
	@Path("getById")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Video> getById(@FormParam("ids") String ids) {
		log.debug("Calling getClip service");
		return clipManagerService.getClipsByID(Unmarshaller.stringToArray(ids));
	}

	/**
	 * Gets all the {@link Video}s
	 * 
	 * @return A {@link Collection} containing all the {@link Video}s
	 */
	@GET
	@Path("getAll")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Video> getAll() {
		log.debug("Calling getAllClips service");
		// TODO Hacer recomendación
		return clipManagerService.getClips();
	}

	/**
	 * Gets all the {@link Video}s uploaded by a {@link com.model.User User}
	 * 
	 * @param username
	 *            The username of the desired {@link com.model.User User}
	 * @return All the {@link Video}s uploaded by a {@link com.model.User User}
	 */
	@POST
	@Path("getByUser")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Video> getByUser(@FormParam("username") String username) {
		log.debug("Calling getClipByUser service");
		return clipManagerService.getClipsByUser(username);
	}

	/**
	 * Searches {@link Video}s using a given text
	 * 
	 * @param query
	 *            The text to search for
	 * @param lang
	 *            The language in which the query is being performed
	 * @return The {@link Video}s matching the query
	 */
	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Video> search(@FormParam("query") String query,
			@FormParam("lang") String lang) {
		log.debug("Calling searchClip service");
		Collection<Video> videos = clipManagerService.freeTextSearch(query,
				lang);
		return videos;
	}

	/**
	 * Deletes a {@link Video}
	 * 
	 * @param id
	 *            The identifier of the {@link Video} to be deleted
	 */
	@POST
	@Path("delete")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void delete(@FormParam("id") String id) {
		log.debug("Calling deleteClip service");
		clipManagerService.deleteClip(id);
	}

	@POST
	@Path("rate")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void rate(@FormParam("video_id") String video_id,
			@FormParam("user_id") String user_id,
			@FormParam("score") String score) {
		log.debug("Calling rateClip service");
		try {
			clipManagerService.rateClip(video_id, user_id,
					Double.parseDouble(score));
		} catch (NullPointerException e) {
			log.error(score + " is an invalid score", e);
		} catch (NumberFormatException e) {
			log.error(score + " is an invalid score", e);
		}
	}

	/**
	 * Edits a {@link Video}
	 * 
	 * @param descriptions
	 *            The new descriptions for the {@link Video}
	 * @param headlines
	 *            The new headlines for the {@link Video}
	 * @param tags
	 *            The new tags for the {@link Video}
	 * @param id
	 *            The identifier of the {@link Video}
	 * @param date
	 *            The new date when the {@link Video} was filmed
	 * @param lat
	 *            The new latitude where the {@link Video} was filmed
	 * @param lon
	 *            The new longitude where the {@link Video} was filmed
	 */
	@POST
	@Path("edit")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void edit(@FormParam("descriptions") String descriptions,
			@FormParam("headlines") String headlines,
			@FormParam("tags") String tags, @FormParam("id") String id,
			@FormParam("date") String date, @FormParam("lat") String lat,
			@FormParam("lon") String lon) {
		log.debug("Calling editClip service");
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		Date ddate;
		try {
			ddate = df.parse(date);
		} catch (ParseException e) {
			log.error("Error parsing date, taking the old one", e);
			ddate = clipManagerService.getClipByID(id).getDate();
		}
		double[] location = new double[2];
		try {
			location = parseLocation(lat, lon);
		} catch (IllegalArgumentException e) {
			log.error("Error parsing location, taking the old one", e);
			Video oldClip = clipManagerService.getClipByID(id);
			location[0] = oldClip.getLat();
			location[1] = oldClip.getLon();
		}
		clipManagerService.editClip(Unmarshaller.stringToMap(descriptions),
				Unmarshaller.stringToMap(headlines),
				Unmarshaller.stringToVectorMap(tags), id, ddate, location[0],
				location[1]);
	}

	/**
	 * Uploads a {@link Video}
	 * 
	 * @param descriptions
	 *            The descriptions of the {@link Video}
	 * @param headlines
	 *            The headlines of the {@link Video}
	 * @param id
	 *            The identifier of the {@link Video raw video} where this
	 *            {@link Video clip} came from
	 * @param date
	 *            The date when the {@link Video} was filmed
	 * @param lat
	 *            The latitude where the {@link Video} was filmed
	 * @param lon
	 *            The longitude where the {@link Video} was filmed
	 * @param subtitles
	 *            The subtitles of the {@link Video}
	 * @param inputStrean
	 *            An {@link InputStream} to read the data of the {@link Video}
	 * @return <code>true</code> if the upload was sucessful, <code>false</code>
	 *         otherwise
	 */
	@POST
	@Path("upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean upload(@FormDataParam("descriptions") String descriptions,
			@FormDataParam("headlines") String headlines,
			@FormDataParam("id") String id, @FormDataParam("date") String date,
			@FormDataParam("lat") String lat, @FormDataParam("lon") String lon,
			@FormDataParam("file") InputStream inputStream,
			@FormDataParam("subtitles") String subtitles) {
		log.debug("Calling uploadClip service");
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		Date takenDate;
		try {
			takenDate = df.parse(date);
		} catch (ParseException e) {
			log.error("Error parsing date " + date, e);
			return false;
		}
		double[] location = new double[2];
		try {
			location = parseLocation(lat, lon);
		} catch (IllegalArgumentException e) {
			log.error("Error parsing location", e);
			return false;
		}
		File clip;
		try {
			clip = inputStreamToFile("uploaded" + id, inputStream);
		} catch (IOException e) {
			log.error("Error uploading clip", e);
			return false;
		}

		clipManagerService.uploadClip(Unmarshaller.stringToMap(descriptions),
				Unmarshaller.stringToMap(headlines), id, clip, takenDate,
				location[0], location[1], Unmarshaller.stringToMap(subtitles));
		return true;
	}
}