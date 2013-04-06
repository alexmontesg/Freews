package impl.presentation.seekvideos;

import impl.presentation.WebServiceAction;

import java.util.Collection;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.struts2.interceptor.RequestAware;

import util.lang.LangConverter;

import com.model.Video;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

/**
 * Seeks {@link Video clips} to show them in the web
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 23/07/2012
 * @version 1.3
 */
public class SeekVideosAction extends WebServiceAction implements RequestAware {

	private static final long serialVersionUID = -4566429255277196796L;
	private Map<String, Object> request;


	@Override
	public String execute() {
		log.debug("Ejecutando SeekVideosAction");
		// TODO Hacer recomendación
		request.put("videolist", callWebService());
		request.put("lang", LangConverter.convert(getLocale().getLanguage()));
		return SUCCESS;
	}

	private Collection<Video> callWebService() {
		WebResource wr = getWebResource("clip/getAll");
		return wr.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).get(
				new GenericType<Collection<Video>>() {
				});
	}
	
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

}
