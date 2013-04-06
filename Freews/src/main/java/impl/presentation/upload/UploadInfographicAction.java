package impl.presentation.upload;

import impl.presentation.WebServiceAction;

import java.io.File;

import javax.ws.rs.core.MediaType;

import util.config.Config;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

/**
 * Uploads an {@link com.model.Infographic Infographic}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 10/08/2012
 * @version 1.3
 */
public class UploadInfographicAction extends WebServiceAction {

	private static final long serialVersionUID = 4379422465704378676L;
	private File infographic;
	private String contentType, filename, type, category;

	@Override
	public String execute() {
		log.debug("Ejecutando UploadInfographicAction para el archivo "
				+ filename + " del tipo " + contentType);
		boolean result = false;
		if (type.equalsIgnoreCase(getText("blast"))) {
			result = callWebService("blast");
		} else if (type.equalsIgnoreCase(getText("header"))) {
			result = callWebService("header");
		} else if (type.equalsIgnoreCase(getText("ending"))) {
			result = callWebService("ending");
		}
		return result ? SUCCESS : ERROR;
	}

	private boolean callWebService(String type) {
		WebResource webResource = getWebResource("infographic/upload");
		FormDataMultiPart fdmp = new FormDataMultiPart();
		fdmp.bodyPart((new FileDataBodyPart("file", infographic,
				MediaType.APPLICATION_OCTET_STREAM_TYPE)));
		fdmp.bodyPart((new FormDataBodyPart("category", category)));
		fdmp.bodyPart((new FormDataBodyPart("type", type)));
		return webResource.type(MediaType.MULTIPART_FORM_DATA).post(
				new GenericType<Boolean>() {
				}, fdmp);
	}

	@Override
	public void validate() {
		if (infographic == null
				|| infographic.length() > Integer.parseInt(Config
						.getProperty("max.file.size"))
				|| !contentType.contains(Config
						.getProperty("media.file.extension"))) {
			addFieldError("infographic", getText("invalid.file"));
		}
		if (type == null
				|| !(type.equalsIgnoreCase(getText("blast"))
						|| type.equalsIgnoreCase(getText("header")) || type
							.equalsIgnoreCase(getText("ending")))) {
			addFieldError("type", getText("invalid.type"));
		}
		if (!areFilledFields(category)) {
			addFieldError("category", getText("invalid.category"));
		}
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setInfographic(File infographic) {
		this.infographic = infographic;
	}

	public void setInfographicContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setInfographicFileName(String filename) {
		this.filename = filename;
	}
}