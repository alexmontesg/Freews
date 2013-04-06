package impl.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.business.InfographicManagerService;
import com.sun.jersey.multipart.FormDataParam;

/**
 * Class implementing web services related to {@link com.model.Infographic
 * Infographics} management
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 03/02/2013
 * @version 1.0
 */
@Path("ws/infographic")
public class InfographicServices extends FreewsServices {

	private static InfographicManagerService infographicManagerService;

	public void setInfographicManagerService(
			InfographicManagerService infographicManagerService) {
		InfographicServices.infographicManagerService = infographicManagerService;
	}

	/**
	 * Uploads an {@link com.model.Infographic Infographic}
	 * 
	 * @param type
	 *            The type of the {@link com.model.Infographic Infographic}, can
	 *            be either "blast", "header", or "ending"
	 * @param inputStream
	 *            The {@link InputStream} to read the
	 *            {@link com.model.Infographic Infographic} data
	 * @param category
	 *            The name of the {@link com.model.Category Category} of the
	 *            {@link com.model.Infographic Infographic}
	 * @return <code>true</code> if the upload was sucessful, <code>false</code>
	 *         otherwise
	 */
	@POST
	@Path("upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean upload(@FormDataParam("type") String type,
			@FormDataParam("file") InputStream inputStream,
			@FormDataParam("category") String category) {
		log.debug("Calling uploadInfographic service");
		File infographic;
		try {
			infographic = this.inputStreamToFile(
					"uploadedInf" + inputStream.toString(), inputStream);
		} catch (IOException e) {
			log.error("Error uploading infographic", e);
			return false;
		}
		if (type.equalsIgnoreCase("blast")) {
			infographicManagerService.uploadBlast(category, infographic);
			return true;
		} else if (type.equalsIgnoreCase("header")) {
			infographicManagerService.uploadHeader(category, infographic);
			return true;
		} else if (type.equalsIgnoreCase("ending")) {
			infographicManagerService.uploadEnding(category, infographic);
			return true;
		}
		return false;
	}

}
