package impl.presentation.delete;

import impl.presentation.WebServiceAction;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Deletes a {@link Video clip}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 21/09/2012
 * @version 1.1
 */
public class DeleteClipAction extends WebServiceAction {
	
	private static final long serialVersionUID = 3910520070722162026L;
	private String id;
	
	@Override
	public String execute() {
		log.debug("Ejecutando DeleteRawVideoAction");
		callWebService();
		return SUCCESS;
	}

	private void callWebService() {
		WebResource wr = getWebResource("clip/delete");
		MultivaluedMap<String, String> params = populate("id", id);
		wr.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(
				ClientResponse.class, params);
	}
	
	@Override
	public void validate() {
		if (id == null || id.trim().isEmpty()) {
			addActionError(getText("invalid.id"));
		}
	}
	
	public void setId(String id) {
		this.id = id.trim();
	}

}