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
 * Performs a free-text query
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 23/07/2012
 * @version 1.2
 */
public class SearchAction extends WebServiceAction implements RequestAware {

	private static final long serialVersionUID = -4975555868607444746L;

	private String query;
	private Map<String, Object> request;

	@Override
	public String execute() {
		log.debug("Ejecutando SearchAction");
		request.put("searchVideos", callWebService());
		request.put("query", query);
		request.put("lang", LangConverter.convert(getLocale().getLanguage()));
		return SUCCESS;
	}

	private Collection<Video> callWebService() {
		WebResource webResource = getWebResource("clip/search");
		MultivaluedMap<String, String> params = populate("query", query.trim(),
				"lang", LangConverter.convert(getLocale().getLanguage()));
		return webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
				.post(new GenericType<Collection<Video>>() {
				}, params);
	}

	@Override
	public void validate() {
		if (query == null || query.trim().length() < 3) {
			addFieldError("query", getText("invalid.query"));
		}
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	public void setQuery(String query) {
		this.query = query.trim();
	}

}
