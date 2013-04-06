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

import com.business.RawVideoManagerService;
import com.model.Video;
import com.sun.jersey.multipart.FormDataParam;

/**
 * Class implementing web services related to {@link Video raw video} management
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 03/02/2013
 * @version 1.0
 */
@Path("ws/raw")
public class RawVideoServices extends FreewsServices {

	private static RawVideoManagerService rawVideoManagerService;

	public void setRawVideoManagerService(
			RawVideoManagerService rawVideoManagerService) {
		RawVideoServices.rawVideoManagerService = rawVideoManagerService;
	}

	/**
	 * Gets a {@link Video} by its identifier
	 * 
	 * @param id
	 *            The identifier of the {@link Video}
	 * @return The {@link Video} with the given identifier
	 */
	@POST
	@Path("getById")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Video getById(@FormParam("id") String id) {
		log.debug("Calling getRaw service");
		return rawVideoManagerService.getRawVideoByID(id);
	}

/**
	 * Deletes a {@link Video]
	 * @param id The identifier of the {@link Video} to be deleted
	 */
	@POST
	@Path("delete")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void delete(@FormParam("id") String id) {
		log.debug("Calling deleteRaw service");
		rawVideoManagerService.deleteRawVideo(id);
	}

	/**
	 * Gets all the {@link Video raw videos}
	 * 
	 * @return A {@link Collection} containing all the {@link Video raw videos}
	 */
	@GET
	@Path("getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Video> getAll() {
		log.debug("Calling getAllRaw service");
		return rawVideoManagerService.getRawVideos();
	}

	/**
	 * Uploads a {@link Video}
	 * 
	 * @param description
	 *            The description of the {@link Video}
	 * @param headline
	 *            The headline of the {@link Video}
	 * @param tags
	 *            The tags of the {@link Video}
	 * @param username
	 *            The username of the {@link com.model.User User} who uploads
	 *            the {@link Video}
	 * @param date
	 *            The date when the {@link Video} was taken
	 * @param lat
	 *            The latitude where the {@link Video} was taken
	 * @param lon
	 *            The longitude where the {@link Video} was taken
	 * @param inputStream
	 *            The {@link InputStream} to get the data of the {@link Video}
	 * @param lang
	 *            The language in which the textual datum are
	 * @return <code>true</code> if the upload was sucessful, <code>false</code>
	 *         otherwise
	 */
	@POST
	@Path("upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean upload(@FormDataParam("description") String description,
			@FormDataParam("headline") String headline,
			@FormDataParam("tags") String tags,
			@FormDataParam("username") String username,
			@FormDataParam("date") String date,
			@FormDataParam("lat") String lat, @FormDataParam("lon") String lon,
			@FormDataParam("file") InputStream inputStream,
			@FormDataParam("lang") String lang) {
		log.debug("Calling uploadRaw service");
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		Date takenDate;
		Double dlat, dlon;
		try {
			takenDate = df.parse(date);
		} catch (ParseException e) {
			return false;
		}
		try {
			dlon = Double.valueOf(lon);
			dlat = Double.valueOf(lat);
			if (dlon > 180 || dlon < -180 || dlat > 180 || dlat < -180) {
				return false;
			}
		} catch (NullPointerException e) {
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
		File clip;
		try {
			clip = inputStreamToFile("uploaded" + inputStream.toString(),
					inputStream);
		} catch (IOException e) {
			log.error("Error uploading raw video", e);
			return false;
		}
		rawVideoManagerService.uploadRaw(description, headline,
				Unmarshaller.stringToVector(tags), username, clip, takenDate,
				dlat, dlon, lang);
		return true;
	}

}
