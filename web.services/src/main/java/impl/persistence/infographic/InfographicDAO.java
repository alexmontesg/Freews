package impl.persistence.infographic;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.model.Infographic;

/**
 * Implementation of {@link AbstractInfographicDAO}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 23/07/2012
 * @version 1.4
 */
public class InfographicDAO extends AbstractInfographicDAO {

	@Override
	protected Infographic getRandomInfographic(String category,
			String collection, MongoOperations op) {
		List<Infographic> infographics = op.find(
				new Query(Criteria.where("category").is(category)),
				Infographic.class, collection);
		if (infographics.size() <= 0) {
			return null;
		}
		int index = new Random(System.currentTimeMillis()).nextInt(infographics
				.size());
		return infographics.get(index);
	}

	@Override
	protected String addInfographic(Infographic infographic, String collection,
			MongoOperations op) {
		op.save(infographic, collection);
		return infographic.getId();
	}

	@Override
	public Collection<Infographic> getAllImpl(String collection,
			MongoOperations op) {
		return op.findAll(Infographic.class, collection);
	}

	@Override
	public void deleteInfographicImpl(String id, String collection,
			MongoOperations op) {
		op.remove(op.findById(id, Infographic.class, collection), collection);
	}

	@Override
	public void deleteAllImpl(MongoOperations op, String collection) {
		op.dropCollection(collection);
	}
}
