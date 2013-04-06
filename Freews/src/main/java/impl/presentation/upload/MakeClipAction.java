package impl.presentation.upload;

import impl.presentation.WebServiceAction;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.struts2.interceptor.RequestAware;

import util.lang.LangConverter;

import com.model.Video;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * Preloads the value of the metadata of a {@link Video clip}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 24/08/2012
 * @version 1.2
 */
public class MakeClipAction extends WebServiceAction implements RequestAware {

	private static final long serialVersionUID = -4841851595163536519L;
	private Map<String, Object> request;
	private String id;

	@Override
	public String execute() {
		log.debug("Ejecutando MakeClipAction para el id " + id);
		Video video = callWebService();
		request.put("headline", video.getHeadline());
		request.put("id", id);
		request.put("description", video.getDescription());
		saveTags(video);
		request.put("lat", video.getLat());
		request.put("lon", video.getLon());
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
				getLocale());
		request.put("date", df.format(video.getDate()));
		request.put("lang", LangConverter.convert(getLocale().getLanguage()));
		return SUCCESS;
	}

	private Video callWebService() {
		WebResource webResource = getWebResource("raw/getById");
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("id", id);
		Video video = webResource.type(
				MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(
				new GenericType<Video>() {
				}, params);
		return video;
	}

	/**
	 * Saves the tags into the request
	 * 
	 * @param video
	 *            The {@link Video} containing the tags to be saved
	 */
	private void saveTags(Video video) {
		StringBuilder tags = new StringBuilder();
		Iterator<Entry<String, Vector<String>>> it = video.getTags().entrySet()
				.iterator();
		Map.Entry<String, Vector<String>> entry = (Map.Entry<String, Vector<String>>) it
				.next();
		Vector<String> vectorTags = entry.getValue();
		for (String tag : vectorTags) {
			tags = tags.append(tag);
			tags = tags.append(", ");
		}
		tags = tags.deleteCharAt(tags.length() - 1);
		tags = tags.deleteCharAt(tags.length() - 1);
		it.remove();
		Map<String, String> tagsMap = new HashMap<String, String>();
		tagsMap.put(entry.getKey(), tags.toString());
		request.put("tags", tagsMap);
	}

	@Override
	public void validate() {
		if (!areFilledFields(id)) {
			addActionError(getText("invalid.id"));
		}
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	public void setId(String id) {
		this.id = id;
	}

}
