package impl.presentation.appendvideos;

import impl.presentation.WebServiceAction;

import java.util.Collection;
import java.util.Map;
import java.util.Vector;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.struts2.interceptor.RequestAware;

import com.model.Video;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

/**
 * Passes the {@link Video}s to append to the next jsp
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 23/07/2012
 * @version 1.4
 */
public class ShowTimelineAction extends WebServiceAction implements
		RequestAware {

	private static final long serialVersionUID = 1077064137115471203L;
	private Map<String, Object> request;
	private String[] selectedVideos;
	private Vector<String> langs;

	@Override
	public String execute() {
		log.debug("Ejecutando ShowTimelineAction");
		langs = new Vector<String>();
		langs.add("es");
		langs.add("en");
		langs.add("pt");
		Collection<Video> clips = callWebService();
		Vector<String> ids = new Vector<String>();
		for (Video video : clips) {
			ids.add(video.getId());
		}
		request.put("videostoappend", ids);
		return SUCCESS;
	}

	private Collection<Video> callWebService() {
		WebResource webResource = getWebResource("clip/getById");
		MultivaluedMap<String, String> params = populate("ids",
				arrayToCSV(selectedVideos));
		Collection<Video> clips = webResource.type(
				MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(
				new GenericType<Collection<Video>>() {
				}, params);
		return clips;
	}

	@Override
	public void validate() {
		if (selectedVideos == null || selectedVideos.length == 0) {
			addFieldError("selectedVideos", getText("invalid.selectedVideos"));
		}
	}

	public void setSelectedVideos(String[] selectedVideos) {
		this.selectedVideos = selectedVideos;
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}
	
	public Vector<String> getLangs() {
		return langs;
	}

}
