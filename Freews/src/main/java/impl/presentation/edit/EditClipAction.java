package impl.presentation.edit;

import impl.presentation.WebServiceAction;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Edits a {@link Video clip}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 21/09/2012
 * @version 1.3
 */
public class EditClipAction extends WebServiceAction {

	private static final long serialVersionUID = 4519854459888040680L;
	private String headline_en, description_en, tags_en;
	private String headline_es, description_es, tags_es;
	private String headline_pt, description_pt, tags_pt;
	private String date, lat, lon, id;
	private Double dlon, dlat;
	private Date ddate;

	@Override
	public String execute() {
		log.debug("Ejecutando EditClipAction con id=" + id);
		Map<String, Vector<String>> tags = splitTags();
		Map<String, String> descriptions = new HashMap<String, String>();
		Map<String, String> headlines = new HashMap<String, String>();
		descriptions.put("en", description_en);
		descriptions.put("es", description_es);
		descriptions.put("pt", description_pt);
		headlines.put("en", headline_en);
		headlines.put("es", headline_es);
		headlines.put("pt", headline_pt);
		callWebService(headlines, descriptions, tags);
		return SUCCESS;
	}

	private void callWebService(Map<String, String> headlines,
			Map<String, String> descriptions, Map<String, Vector<String>> tags) {
		WebResource wr = getWebResource("clip/edit");
		MultivaluedMap<String, String> params = populate("headlines",
				mapToCSV(headlines), "descriptions", mapToCSV(descriptions),
				"tags", vectorMapToCSV(tags), "id", id, "date", date, "lat",
				lat, "lon", lon);
		wr.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(
				ClientResponse.class, params);
	}

	@Override
	public void validate() {
		if (!areFilledFields(headline_en, headline_es, headline_pt)) {
			addFieldError("headline", getText("invalid.headline"));
		}
		if (!areFilledFields(description_en, description_es, description_pt)) {
			addFieldError("description", getText("invalid.description"));
		}
		if (!areFilledFields(tags_en, tags_es, tags_pt)) {
			addFieldError("tags", getText("invalid.tags"));
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

	/**
	 * Splits the tags and stores them into a {@link Map}
	 * 
	 * @return A {@link Map} containing the tags
	 */
	private Map<String, Vector<String>> splitTags() {
		String[] tagsArray_en = tags_en.split(",");
		Vector<String> tagsVector_en = new Vector<String>();
		for (int i = 0; i < tagsArray_en.length; i++) {
			tagsVector_en.add(tagsArray_en[i].trim());
		}

		String[] tagsArray_es = tags_es.split(",");
		Vector<String> tagsVector_es = new Vector<String>();
		for (int i = 0; i < tagsArray_es.length; i++) {
			tagsVector_es.add(tagsArray_es[i].trim());
		}

		String[] tagsArray_pt = tags_pt.split(",");
		Vector<String> tagsVector_pt = new Vector<String>();
		for (int i = 0; i < tagsArray_pt.length; i++) {
			tagsVector_pt.add(tagsArray_pt[i].trim());
		}

		Map<String, Vector<String>> tags = new HashMap<String, Vector<String>>();
		tags.put("en", tagsVector_en);
		tags.put("es", tagsVector_es);
		tags.put("pt", tagsVector_pt);
		return tags;
	}

	public void setHeadline_en(String headline_en) {
		this.headline_en = headline_en.replace(";", "");
	}

	public void setDescription_en(String description_en) {
		this.description_en = description_en.replace(";", "");
	}

	public void setTags_en(String tags_en) {
		this.tags_en = tags_en.replace(";", "");
	}

	public void setHeadline_es(String headline_es) {
		this.headline_es = headline_es.replace(";", "");
	}

	public void setDescription_es(String description_es) {
		this.description_es = description_es.replace(";", "");
	}

	public void setTags_es(String tags_es) {
		this.tags_es = tags_es.replace(";", "");
	}

	public void setHeadline_pt(String headline_pt) {
		this.headline_pt = headline_pt.replace(";", "");
	}

	public void setDescription_pt(String description_pt) {
		this.description_pt = description_pt.replace(";", "");
	}

	public void setTags_pt(String tags_pt) {
		this.tags_pt = tags_pt.replace(";", "");
	}

	public void setId(String id) {
		this.id = id;
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
