package impl.presentation.upload;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.RequestAware;

import util.lang.LangConverter;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Preloads the form for uploading a {@link com.model.Video raw video}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 24/08/2012
 * @version 1.1
 */
public class UploadRawFormAction extends ActionSupport implements RequestAware {

	private static final long serialVersionUID = 1010452892564216223L;
	private Map<String, Object> request;
	private Logger log = Logger.getLogger(this.getClass());

	@Override
	public String execute() {
		log.debug("Ejecutando UploadRawFormAction");
		request.put("lang", LangConverter.convert(getLocale().getLanguage()));
		return SUCCESS;
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}
}
