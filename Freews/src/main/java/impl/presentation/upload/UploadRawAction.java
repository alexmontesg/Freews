package impl.presentation.upload;

import impl.presentation.WebServiceAction;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.struts2.interceptor.SessionAware;

import util.config.Config;
import util.lang.LangConverter;

import com.model.User;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

/**
 * Uploads a {@link com.model.Video raw video}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 24/08/2012
 * @version 1.6
 */
public class UploadRawAction extends WebServiceAction implements SessionAware {

	private static final long serialVersionUID = 1010452892564216223L;
	private Map<String, Object> session;
	private File clip;
	private String contentType, filename, headline, description, date, lat,
			lon;
	private Double dlon, dlat;
	private Date ddate;

	@Override
	public String execute() {
		log.debug("Ejecutando UploadRawAction para el archivo " + filename
				+ " del tipo " + contentType);
		return callWebService() ? SUCCESS : ERROR;
	}

	@Override
	public void validate() {
		if (clip == null
				|| clip.length() > Integer.parseInt(Config
						.getProperty("max.file.size"))
				|| !contentType.contains(Config
						.getProperty("media.file.extension"))) {
			addFieldError("clip", getText("invalid.file"));
		}
		if (headline == null || headline.trim().isEmpty()) {
			addFieldError("headline", getText("invalid.headline"));
		}
		if (description == null || description.trim().isEmpty()) {
			addFieldError("description", getText("invalid.description"));
		}
		try {
			dlon = Double.valueOf(this.lon);
			dlat = Double.valueOf(this.lat);
			if (dlon > 180 || dlon < -180 || dlat > 180 || dlat < -180) {
				addFieldError("lon", getText("invalid.position"));
			}
		} catch (NullPointerException e) {
			addFieldError("lon", getText("invalid.position"));
		} catch (NumberFormatException e) {
			addFieldError("lon", getText("invalid.position"));
		}
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
				getLocale());
		try {
			ddate = df.parse(this.date);
			if (ddate.after(new Date())) {
				addFieldError("takenDate", getText("invalid.date"));
			}
		} catch (ParseException e) {
			addFieldError("takenDate", getText("invalid.date"));
		}
	}

	private boolean callWebService() {
		WebResource webResource = getWebResource("raw/upload");
		FormDataMultiPart fdmp = new FormDataMultiPart();
		fdmp.bodyPart((new FileDataBodyPart("file", clip,
				MediaType.APPLICATION_OCTET_STREAM_TYPE)));
		fdmp.bodyPart((new FormDataBodyPart("username", ((User) (session
				.get("user"))).getUsername())));
		fdmp.bodyPart((new FormDataBodyPart("headline", headline)));
		fdmp.bodyPart((new FormDataBodyPart("description", description)));
		fdmp.bodyPart((new FormDataBodyPart("date", date)));
		fdmp.bodyPart((new FormDataBodyPart("lat", lat)));
		fdmp.bodyPart((new FormDataBodyPart("lon", lon)));
		fdmp.bodyPart((new FormDataBodyPart("lang", LangConverter
				.convert(getLocale().getLanguage()))));
		return webResource.type(MediaType.MULTIPART_FORM_DATA).post(
				new GenericType<Boolean>() {
				}, fdmp);
	}
	
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setClip(File clip) {
		this.clip = clip;
	}

	public void setClipContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setClipFileName(String filename) {
		this.filename = filename;
	}

	public void setTakenDate(String date) {
		this.date = date.replace('-', '/');
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}
}
