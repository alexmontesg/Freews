package impl.rest;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.business.CategoryManagerService;
import com.model.Category;

/**
 * Class implementing web services related to {@link Category} management
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 03/02/2013
 * @version 1.0
 */
@Path("ws/category")
public class CategoryServices extends FreewsServices {

	private static CategoryManagerService categoryManagerService;

	public void setCategoryManagerService(
			CategoryManagerService categoryManagerService) {
		CategoryServices.categoryManagerService = categoryManagerService;
	}

	/**
	 * Adds a {@link Category}
	 * 
	 * @param category
	 *            The value of the {@link Category} to be added
	 */
	@POST
	@Path("add")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void add(@FormParam("category") String category) {
		log.debug("Calling addCategory service");
		categoryManagerService.addCategory(category);
	}

	/**
	 * Gets all the {@link Category categories} available
	 * 
	 * @return A {@link Collection} with all the {@link Category categories}
	 *         available
	 */
	@GET
	@Path("getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Category> getAll() {
		log.debug("Calling getAllCategories service");
		return categoryManagerService.getCategories();
	}

}
