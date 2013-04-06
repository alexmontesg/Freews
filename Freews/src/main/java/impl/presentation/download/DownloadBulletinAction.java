package impl.presentation.download;

import impl.presentation.WebServiceAction;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.struts2.interceptor.ServletResponseAware;

import util.config.Config;

import com.model.Bulletin;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

/**
 * Downloads a {@link Bulletin}
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @version 1.2
 * @since 07/09/2012
 */
public class DownloadBulletinAction extends WebServiceAction implements
		ServletResponseAware {

	private static final long serialVersionUID = -4310405487875904119L;
	private String id;
	private HttpServletResponse response;

	@Override
	public String execute() {
		log.debug("Ejecutando DownloadAction");
		Bulletin bulletin = callWebService();
		downloadFile(
				Config.getProperty("bulletins.folder") + id + "."
						+ Config.getProperty("media.file.extension"),
				bulletin.getTitle(), response);
		return SUCCESS;
	}

	private Bulletin callWebService() {
		WebResource webResource = getWebResource("bulletin/getById");
		MultivaluedMap<String, String> params = populate("id", id);
		Bulletin bulletin = webResource.type(
				MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(
				new GenericType<Bulletin>() {
				}, params);
		return bulletin;
	}

	@Override
	public void validate() {
		if (id == null || id.trim().isEmpty()) {
			addActionError(getText("invalid.id"));
		}
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
}
