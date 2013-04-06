package impl.persistence.video;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import com.model.Bulletin;
import com.persistence.BulletinDataService;

/**
 * Abstract implementation of {@link BulletinDataService} that helps with the
 * management of <a href="http://www.springsource.org/spring-data">Spring
 * Data</a> and MongoDB
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 02/03/2013
 * @version 1.0
 */
public abstract class AbstractBulletinDAO implements BulletinDataService {

	private ConfigurableApplicationContext ctx;

	protected final String BULLETINS = "bulletins";

	public final String insertBulletin(Bulletin bulletin) {
		String id = insertBulletinImpl(bulletin, getOperationHandler());
		ctx.close();
		return id;
	}

	/**
	 * Implementation of the {@link #insertBulletin(Bulletin)} method to work
	 * with Spring Data and MongoDB
	 * 
	 * @param bulletin
	 *            The {@link Bulletin} to be inserted
	 * @param op
	 *            The {@link MongoOperations} that performs the operation
	 * @return The identifier of the {@link Bulletin}
	 */
	protected abstract String insertBulletinImpl(Bulletin bulletin,
			MongoOperations op);

	public final Bulletin getBulletinByID(String id) {
		Bulletin bulletin = getBulletinByIDImpl(id, getOperationHandler());
		ctx.close();
		return bulletin;
	}

	/**
	 * Implementation of the {@link #getBulletinByID(String)} method to work
	 * with Spring Data and MongoDB
	 * 
	 * @param id
	 *            The identifier of the desired {@link Bulletin}
	 * @param op
	 *            The {@link MongoOperations} that performs the operation
	 * @return The {@link Bulletin} with the given identifier
	 */
	protected abstract Bulletin getBulletinByIDImpl(String id,
			MongoOperations op);

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
