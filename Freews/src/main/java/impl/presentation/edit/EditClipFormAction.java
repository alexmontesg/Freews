package impl.presentation.edit;

import impl.presentation.WebServiceAction;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.struts2.interceptor.RequestAware;

import com.model.Video;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

/**
 * Loads the data of a {@link Video clip} to be edited
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 21/09/2012
 * @version 1.2 
 */
public class EditClipFormAction extends WebServiceAction implements
		RequestAware {

	private static final long serialVersionUID = -6201989698902586617L;
	private Map<String, Object> request;
	private String video;

	@Override
	public String execute() {
		log.debug("Ejecutando EditClipFormAction");
		Video video = callWebService();
		request.put("video", video);
		saveTags(video);
		return SUCCESS;
	}

	private Video callWebService() {
		WebResource webResource = getWebResource("clip/getById");
		String[] selectedVideos = new String[1];
		selectedVideos[0] = video;
		MultivaluedMap<String, String> params = populate("ids",
				arrayToCSV(selectedVideos));
		Collection<Video> clips = webResource.type(
				MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(
				new GenericType<Collection<Video>>() {
				}, params);
		return clips.iterator().next();
	}

	/**
	 * Saves the tags into the request
	 * 
	 * @param video
	 */
	private void saveTags(Video video) {
		Map<String, String> tagsMap = new HashMap<String, String>();
		Iterator<Entry<String, Vector<String>>> it = video.getTags().entrySet()
				.iterator();
		while (it.hasNext()) {
			StringBuilder tags = new StringBuilder();
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
			tagsMap.put(entry.getKey(), tags.toString());
		}
		request.put("tags", tagsMap);
	}

	@Override
	public void validate() {
		if (!areFilledFields(video)) {
			addActionError(getText("invalid.video"));
		}
	}
	
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	public void setVideo(String video) {
		this.video = video;
	}

}
