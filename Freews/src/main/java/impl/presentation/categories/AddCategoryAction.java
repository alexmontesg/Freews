package impl.presentation.categories;

import impl.presentation.WebServiceAction;

import java.util.Collection;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.struts2.interceptor.SessionAware;

import com.model.Category;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

/**
 * Adds a {@link Category}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 13/09/2012
 * @version 1.1
 */
public class AddCategoryAction extends WebServiceAction implements SessionAware {

	private static final long serialVersionUID = -8650385123756971218L;
	private Map<String, Object> session;
	private String category;

	@Override
	public String execute() {
		session.put("categories", callWebService());
		return SUCCESS;
	}

	private Collection<Category> callWebService() {
		WebResource wrAdd = getWebResource("category/add");
		MultivaluedMap<String, String> params = populate("category", category);
		wrAdd.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(
				ClientResponse.class, params);
		WebResource wrGet = getWebResource("category/getAll");
		Collection<Category> categories = wrGet.type(
				MediaType.APPLICATION_FORM_URLENCODED_TYPE).get(
				new GenericType<Collection<Category>>() {
				});
		return categories;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	public void validate() {
		if (category == null || category.trim().isEmpty()) {
			addFieldError("category", getText("invalid.category"));
		}
	}

}
