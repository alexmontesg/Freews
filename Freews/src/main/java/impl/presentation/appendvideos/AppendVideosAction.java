package impl.presentation.appendvideos;

import impl.presentation.WebServiceAction;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import util.config.Config;

import com.model.User;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Appends {@link Video}s in order to create a {@link Bulletin}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 23/07/2012
 * @version 1.6
 */
public class AppendVideosAction extends WebServiceAction implements
		RequestAware, SessionAware, ServletRequestAware {

	private static final long serialVersionUID = 5374698954795833411L;
	private Map<String, Object> request, session;
	private HttpServletRequest servletRequest;
	private String[] selectedVideos;
	private String category, title, lang;

	@Override
	public String execute() {
		log.debug("Ejecutando AppendVideosAction");
		String id = callWebService();
		String path = servletRequest.getServerName()
				+ Config.getProperty("relative.bulletins.folder");
		request.put("mediaurl", path + id);
		request.put("appendedvideo", id);
		return SUCCESS;
	}

	private String callWebService() {
		WebResource webResource = getWebResource("bulletin/append");
		MultivaluedMap<String, String> params = populate("selectedVideos",
				arrayToCSV(selectedVideos), "category", category, "username",
				((User) session.get("user")).getUsername(), "title", title,
				"lang", lang);
		ClientResponse response = webResource.type(
				MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(
				ClientResponse.class, params);
		String id = response.getEntity(String.class);
		return id;
	}

	@Override
	public void validate() {
		if (selectedVideos == null || selectedVideos.length == 0) {
			addFieldError("selectedVideos", getText("invalid.selectedVideos"));
		}
		if (category == null || category.trim().isEmpty()) {
			addFieldError("category", getText("invalid.category"));
		}
		if (title == null || title.trim().isEmpty()) {
			addFieldError("title", getText("invalid.title"));
		}
	}

	public void setSelectedVideos(String[] selectedVideos) {
		this.selectedVideos = selectedVideos;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

}
