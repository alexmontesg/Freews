package impl.presentation.download;

import impl.presentation.WebServiceAction;

import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.struts2.interceptor.ServletResponseAware;

import util.config.Config;

import com.model.Video;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

/**
 * Downloads a {@link Video raw video}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @version 1.3
 * @since 07/09/2012
 */
public class DownloadRawAction extends WebServiceAction implements
		ServletResponseAware {

	private static final long serialVersionUID = -4310405487875904119L;
	private String id;
	private HttpServletResponse response;

	public void setId(String id) {
		this.id = id;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public String execute() {
		log.debug("Ejecutando DownloadAction");
		Video video = callWebService();
		String title;
		try {
			title = video.getHeadline().values().iterator().next();
		} catch (NoSuchElementException e) {
			title = "RawVideo" + video.getId();
		}
		downloadFile(
				Config.getProperty("media.folder") + id + "."
						+ Config.getProperty("media.file.extension"), title,
				response);
		return SUCCESS;
	}

	private Video callWebService() {
		WebResource webResource = getWebResource("raw/getById");
		MultivaluedMap<String, String> params = populate("id", id);
		Video video = webResource.type(
				MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(
				new GenericType<Video>() {
				}, params);
		return video;
	}

	@Override
	public void validate() {
		if (id == null || id.trim().isEmpty()) {
			addActionError(getText("invalid.id"));
		}
	}
}
