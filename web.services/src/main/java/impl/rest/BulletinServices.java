package impl.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.business.BulletinManagerService;
import com.model.Bulletin;

/**
 * Class implementing web services related to {@link Bulletin} management
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 03/02/2013
 * @version 1.0
 */
@Path("ws/bulletin")
public class BulletinServices extends FreewsServices {

	private static BulletinManagerService bulletinManagerService;

	public void setBulletinManagerService(
			BulletinManagerService bulletinManagerService) {
		BulletinServices.bulletinManagerService = bulletinManagerService;
	}

	/**
	 * Appends videos in order to create a {@link Bulletin}, those videos can be
	 * both {@link com.model.Video clips} and {@link com.model.Infographic
	 * Infographics}
	 * 
	 * @param selectedVideos
	 *            The urls of the serlected videos, separated by semicolons
	 * @param category
	 *            The name of the {@link com.model.Category Category} the
	 *            {@link Bulletin} is taking
	 * @param username
	 *            The username of the {@link com.model.User} creating the
	 *            {@link Bulletin}
	 * @param title
	 *            The title the {@link Bulletin} is taking
	 * @param lang
	 *            The language in which the subtitles of the {@link Bulletin}
	 *            are
	 * @return The identifier of the resulting {@link Bulletin}
	 */
	@POST
	@Path("append")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String appendVideos(
			@FormParam("selectedVideos") String selectedVideos,
			@FormParam("category") String category,
			@FormParam("username") String username,
			@FormParam("title") String title, @FormParam("lang") String lang) {
		log.debug("Calling appendVideos service");
		String id = bulletinManagerService.createBulletin(
				selectedVideos.split(";"), category, username, title, lang);
		return id;
	}

	/**
	 * Gets a {@link Bulletin} with a specific identifier
	 * 
	 * @param id
	 *            The identifier of the desired ´{@link Bulletin}
	 * @return The {@link Bulletin} with the given identifier
	 */
	@POST
	@Path("getById")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Bulletin getById(@FormParam("id") String id) {
		log.debug("Calling getBulletin service");
		return bulletinManagerService.getBulletinByID(id);
	}

}
