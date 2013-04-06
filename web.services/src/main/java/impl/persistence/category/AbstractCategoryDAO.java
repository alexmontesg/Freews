package impl.persistence.category;

import java.util.Collection;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import com.model.Category;
import com.persistence.CategoryDataService;

/**
 * Abstract implementation of {@link CategoryDataService} that helps with the
 * management of <a href="http://www.springsource.org/spring-data">Spring
 * Data</a> and MongoDB
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 02/03/2013
 * @version 1.0
 */
public abstract class AbstractCategoryDAO implements CategoryDataService {

	private ConfigurableApplicationContext ctx;
	protected final String CATEGORIES = "categories";

	public final Collection<Category> getCategories() {
		Collection<Category> categories = getCategoriesImpl(getOperationHandler());
		ctx.close();
		return categories;
	}

	protected abstract Collection<Category> getCategoriesImpl(MongoOperations op);

	public final void addCategory(Category category) {
		addCategoryImpl(category, getOperationHandler());
		ctx.close();
	}

	/**
	 * Implementation of the {@link #addCategory(Category)} method to use Spring
	 * Data with MongoDB
	 * 
	 * @param category
	 *            The {@link Category} to be added
	 * @param op
	 *            The {@link MongoOperations} that performs the operation
	 */
	protected abstract void addCategoryImpl(Category category,
			MongoOperations op);

	public final void deleteCategory(String category) {
		deleteCategoryImpl(category, getOperationHandler());
		ctx.close();
	}

	/**
	 * Implementation of the {@link #deleteCategory(String)} method to use
	 * Spring Data with MongoDB
	 * 
	 * @param category
	 *            The value of the {@link Category} to be deleted
	 * @param op
	 *            The {@link MongoOperations} that performs the operation
	 */
	protected abstract void deleteCategoryImpl(String category,
			MongoOperations op);

	public final void deleteAll() {
		deleteAllImpl(getOperationHandler());
		ctx.close();
	}

	/**
	 * Implementation of the {@link #deleteAll()} method to use Spring Data with
	 * MongoDB
	 * 
	 * @param op
	 *            The {@link MongoOperations} that performs the operation
	 */
	protected abstract void deleteAllImpl(MongoOperations op);

	/**
	 * Gets the {@link MongoOperations} item for this class
	 * 
	 * @return The {@link MongoOperations} item for this class
	 */
	private MongoOperations getOperationHandler() {
		ctx = new GenericXmlApplicationContext("mongo-config.xml");
		return (MongoOperations) ctx.getBean("mongoTemplate");
	}

}
