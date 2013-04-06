package impl.presentation.seekvideos;

import impl.presentation.WebServiceAction;

import java.util.Collection;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.struts2.interceptor.RequestAware;

import com.model.Video;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

/**
 * Seeks {@link Video raw videos} to show them in the admin panel
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 24/08/2012
 * @version 1.2
 */
public class LoadRawVideosAction extends WebServiceAction implements
		RequestAware {

	private static final long serialVersionUID = -1803433662717155841L;
	private Map<String, Object> request;

	@Override
	public String execute() {
		log.debug("Ejecutando LoadRawVideosAction");
		request.put("rawVideos", callWebService());
		return SUCCESS;
	}

	private Collection<Video> callWebService() {
		WebResource wr = getWebResource("raw/getAll");
		return wr.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).get(
				new GenericType<Collection<Video>>() {
				});
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

}