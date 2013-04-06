package impl.presentation.seekvideos;

import impl.presentation.WebServiceAction;

import java.util.Collection;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.struts2.interceptor.RequestAware;

import util.lang.LangConverter;

import com.model.Video;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

/**
 * Retrieves all the information of a {@link Video}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 23/07/2012
 * @version 1.3
 */
public class SeeMoreInfoAction extends WebServiceAction implements RequestAware {

	private static final long serialVersionUID = -4245651029428740499L;
	private Map<String, Object> request;
	private String id;

	@Override
	public String execute() {
		log.debug("Ejecutando SeeMoreInfoAction");
		request.put("video", callWebService());
		request.put("lang", LangConverter.convert(getLocale().getLanguage()));
		return SUCCESS;
	}

	private Video callWebService() {
		WebResource webResource = getWebResource("clip/getById");
		String[] selectedVideos = new String[1];
		selectedVideos[0] = id;
		MultivaluedMap<String, String> params = populate("ids",
				arrayToCSV(selectedVideos));
		Collection<Video> clips = webResource.type(
				MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(
				new GenericType<Collection<Video>>() {
				}, params);
		return clips.iterator().next();
	}

	@Override
	public void validate() {
		if (!areFilledFields(id)) {
			addActionError(getText("invalid.id"));
		}
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

}
