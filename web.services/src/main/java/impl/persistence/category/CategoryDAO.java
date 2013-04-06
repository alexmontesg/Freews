package impl.persistence.category;

import java.util.Collection;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.model.Category;

/**
 * Implementation of {@link AbstractCategoryDAO}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 23/07/2012
 * @version 1.4
 */
public class CategoryDAO extends AbstractCategoryDAO {

	@Override
	protected Collection<Category> getCategoriesImpl(MongoOperations op) {
		return op.findAll(Category.class, CATEGORIES);
	}

	@Override
	protected void addCategoryImpl(Category category, MongoOperations op) {
		op.save(category, CATEGORIES);
	}

	@Override
	protected void deleteCategoryImpl(String category, MongoOperations op) {
		op.remove(new Query(Criteria.where("value").is(category)), CATEGORIES);
	}

	@Override
	protected void deleteAllImpl(MongoOperations op) {
		op.dropCollection(CATEGORIES);
	}

}
