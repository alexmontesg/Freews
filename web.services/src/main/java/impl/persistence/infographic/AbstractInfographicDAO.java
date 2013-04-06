package impl.persistence.infographic;

import java.util.Collection;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import com.model.Infographic;
import com.persistence.InfographicDataService;

/**
 * Abstract implementation of {@link InfographicDataService} that helps with the
 * management of <a href="http://www.springsource.org/spring-data">Spring
 * Data</a> and MongoDB
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 02/03/2013
 * @version 1.0
 */
public abstract class AbstractInfographicDAO implements InfographicDataService {

	protected final String ENDINGS = "endings";
	protected final String HEADERS = "headers";
	protected final String BLASTS = "blasts";

	private ConfigurableApplicationContext ctx;

	public final Infographic getRandomHeader(String category) {
		Infographic infographic = getRandomInfographic(category, HEADERS,
				getOperationHandler());
		ctx.close();
		return infographic;
	}

	public final Infographic getRandomBlast(String category) {
		Infographic infographic = getRandomInfographic(category, BLASTS,
				getOperationHandler());
		ctx.close();
		return infographic;
	}

	public final Infographic getRandomEnding(String category) {
		Infographic infographic = getRandomInfographic(category, ENDINGS,
				getOperationHandler());
		ctx.close();
		return infographic;
	}

	/**
	 * Gets a random {@link Infographic} of a specific category from a specific
	 * collection
	 * 
	 * @param category
	 *            The value of the desired {@link com.model.Category category}
	 * @param collection
	 *            The collection where the {@link Infographic} is stored
	 * @param op
	 *            The {@link MongoOperations} that performs the operation
	 * @return A random {@link Infographic} of the given category and collection
	 */
	protected abstract Infographic getRandomInfographic(String category,
			String collection, MongoOperations op);

	public final String addHeader(Infographic infographic) {
		String str = addInfographic(infographic, HEADERS, getOperationHandler());
		ctx.close();
		return str;
	}

	public final String addBlast(Infographic infographic) {
		String str = addInfographic(infographic, BLASTS, getOperationHandler());
		ctx.close();
		return str;
	}

	public final String addEnding(Infographic infographic) {
		String str = addInfographic(infographic, ENDINGS, getOperationHandler());
		ctx.close();
		return str;
	}

	/**
	 * Adds an {@link Infographic} to a specific collection
	 * 
	 * @param infographic
	 *            The {@link Infographic} to be stored
	 * @param collection
	 *            The collection where the {@link Infographic} is going to be
	 *            stored
	 * @param op
	 *            The {@link MongoOperations} that performs the operation
	 * @return The identifier of the {@link Infographic}
	 */
	protected abstract String addInfographic(Infographic infographic,
			String collection, MongoOperations op);

	public final Collection<Infographic> getHeaders() {
		Collection<Infographic> infographics = getAllImpl(HEADERS,
				getOperationHandler());
		ctx.close();
		return infographics;
	}

	public final Collection<Infographic> getBlasts() {
		Collection<Infographic> infographics = getAllImpl(BLASTS,
				getOperationHandler());
		ctx.close();
		return infographics;
	}

	public final Collection<Infographic> getEndings() {
		Collection<Infographic> infographics = getAllImpl(ENDINGS,
				getOperationHandler());
		ctx.close();
		return infographics;
	}

	/**
	 * Gets all the {@link Infographic}s from a collection
	 * 
	 * @param collection
	 *            The collection to get the {@link Infographic}s from
	 * @param op
	 *            The {@link MongoOperations} that performs the operation
	 * @return A {@link Collection} with all the infographics of a database
	 *         collection
	 */
	public abstract Collection<Infographic> getAllImpl(String collection,
			MongoOperations op);

	public final void deleteHeader(String id) {
		deleteInfographicImpl(id, HEADERS, getOperationHandler());
		ctx.close();
	}

	public final void deleteBlast(String id) {
		deleteInfographicImpl(id, BLASTS, getOperationHandler());
		ctx.close();
	}

	public final void deleteEnding(String id) {
		deleteInfographicImpl(id, ENDINGS, getOperationHandler());
		ctx.close();
	}

	public abstract void deleteInfographicImpl(String id, String collection,
			MongoOperations op);

	public final void deleteAll() {
		deleteAllImpl(getOperationHandler(), HEADERS);
		deleteAllImpl(getOperationHandler(), BLASTS);
		deleteAllImpl(getOperationHandler(), ENDINGS);
		ctx.close();
	}

	/**
	 * Deletes all the {@link Infographic}s of a collection
	 * 
	 * @param op
	 *            The {@link MongoOperations} that performs the operation
	 * @param collection
	 *            The collection to delete
	 */
	public abstract void deleteAllImpl(MongoOperations op, String collection);

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
