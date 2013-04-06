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
 * Searches the {@link Video}s a {@link com.model.User User} uploaded
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 23/07/2012
 * @version 1.2
 */
public class MyVideosAction extends WebServiceAction implements RequestAware {

	private static final long serialVersionUID = 6877875110538529936L;
	private Map<String, Object> request;
	private String username;

	@Override
	public String execute() {
		log.debug("Ejecutando MyVideosAction");
		request.put("videolist", callWebService());
		request.put("lang", LangConverter.convert(getLocale().getLanguage()));
		return SUCCESS;
	}

	private Collection<Video> callWebService() {
		WebResource webResource = getWebResource("clip/getByUser");
		MultivaluedMap<String, String> params = populate("username", username);
		return webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.post(new GenericType<Collection<Video>>() {
				}, params);
	}

	@Override
	public void validate() {
		if (!areFilledFields(username)) {
			addActionError(getText("invalid.username"));
		}
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

}