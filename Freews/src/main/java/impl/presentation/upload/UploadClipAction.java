package impl.presentation.upload;

import impl.presentation.WebServiceAction;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import util.config.Config;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

/**
 * Uploads a {@link com.model.Video clip}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 08/08/2012
 * @version 1.3
 */
public class UploadClipAction extends WebServiceAction {

	private static final long serialVersionUID = -8215748280112753933L;
	private File clip;
	private String contentType;
	private String filename;

	private String headline_en, description_en, subs_en;
	private String headline_es, description_es, subs_es;
	private String headline_pt, description_pt, subs_pt;

	private String date;
	private String lat;
	private String lon;
	private String id;

	@Override
	public String execute() {
		log.debug("Ejecutando UploadClipAction para el archivo " + filename
				+ " del tipo " + contentType);
		Map<String, String> descriptions = new HashMap<String, String>();
		Map<String, String> headlines = new HashMap<String, String>();
		Map<String, String> subtitles = new HashMap<String, String>();
		descriptions.put("en", description_en);
		descriptions.put("es", description_es);
		descriptions.put("pt", description_pt);

		headlines.put("en", headline_en);
		headlines.put("es", headline_es);
		headlines.put("pt", headline_pt);

		subtitles.put("en", subs_en);
		subtitles.put("es", subs_es);
		subtitles.put("pt", subs_pt);
		if (callWebService(descriptions, headlines, subtitles)) {
			return SUCCESS;
		}
		return ERROR;
	}

	private boolean callWebService(Map<String, String> descriptions,
			Map<String, String> headlines, Map<String, String> subtitles) {
		WebResource webResource = getWebResource("clip/upload");
		FormDataMultiPart fdmp = new FormDataMultiPart();
		fdmp.bodyPart((new FileDataBodyPart("file", clip,
				MediaType.APPLICATION_OCTET_STREAM_TYPE)));
		fdmp.bodyPart((new FormDataBodyPart("id", id)));
		fdmp.bodyPart((new FormDataBodyPart("headlines", mapToCSV(headlines))));
		fdmp.bodyPart((new FormDataBodyPart("descriptions",
				mapToCSV(descriptions))));
		fdmp.bodyPart((new FormDataBodyPart("subtitles", mapToCSV(subtitles))));
		fdmp.bodyPart((new FormDataBodyPart("date", date)));
		fdmp.bodyPart((new FormDataBodyPart("lat", lat)));
		fdmp.bodyPart((new FormDataBodyPart("lon", lon)));
		return webResource.type(MediaType.MULTIPART_FORM_DATA).post(
				new GenericType<Boolean>() {
				}, fdmp);
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
		if (!areFilledFields(headline_es, headline_en, headline_pt)) {
			addFieldError("headline", getText("invalid.headline"));
		}
		if (!areFilledFields(description_es, description_en, description_pt)) {
			addFieldError("description", getText("invalid.description"));
		}
		if (!areFilledFields(subs_es, subs_en, subs_pt)) {
			addFieldError("subtitles", getText("invalid.subtitles"));
		}
		try {
			double dlon = Double.valueOf(this.lon);
			double dlat = Double.valueOf(this.lat);
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
			Date ddate = df.parse(this.date);
			if (ddate.after(new Date())) {
				addFieldError("takenDate", getText("invalid.date"));
			}
		} catch (ParseException e) {
			addFieldError("takenDate", getText("invalid.date"));
		}
	}
	
	public void setHeadline_en(String headline_en) {
		this.headline_en = headline_en;
	}

	public void setDescription_en(String description_en) {
		this.description_en = description_en;
	}

	public void setHeadline_es(String headline_es) {
		this.headline_es = headline_es;
	}

	public void setDescription_es(String description_es) {
		this.description_es = description_es;
	}

	public void setHeadline_pt(String headline_pt) {
		this.headline_pt = headline_pt;
	}

	public void setDescription_pt(String description_pt) {
		this.description_pt = description_pt;
	}

	public void setSubs_pt(String subs_pt) {
		this.subs_pt = subs_pt;
	}

	public void setSubs_es(String subs_es) {
		this.subs_es = subs_es;
	}

	public void setSubs_en(String subs_en) {
		this.subs_en = subs_en;
	}

	public void setId(String id) {
		this.id = id;
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
