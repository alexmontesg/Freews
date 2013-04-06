package impl.business.categorymanager;

import java.util.Collection;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.business.CategoryManagerService;
import com.model.Category;
import com.persistence.CategoryDataService;

/**
 * Implementation of the {@link CategoryManagerService}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 23/07/2012
 * @version 1.1
 */
public class CategoryManager implements CategoryManagerService {

	private CategoryDataService categoryDataService;
	private static Logger log = Logger.getLogger(CategoryManagerService.class);

	public void setCategoryDataService(CategoryDataService categoryDataService) {
		this.categoryDataService = categoryDataService;
	}

	public Collection<Category> getCategories() {
		log.debug("Entrando en getCategories");
		Collection<Category> categories = new Vector<Category>();
		try {
			categories = categoryDataService.getCategories();
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			log.error(e.getCause() != null ? e.getCause().getMessage()
					: "Causa desconocida");
		}
		return categories;
	}

	public void addCategory(String category) {
		log.debug("Entrando en addCategory");
		try {
			Category cat = new Category();
			cat.setValue(category);
			categoryDataService.addCategory(cat);
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			log.error(e.getCause() != null ? e.getCause().getMessage()
					: "Causa desconocida");
		}
	}

}
