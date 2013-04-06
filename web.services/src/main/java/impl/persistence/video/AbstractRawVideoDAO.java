package impl.persistence.video;

import java.util.Collection;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import com.model.Video;
import com.persistence.RawVideoDataService;

/**
 * Abstract implementation of {@link RawVideoDataService} that helps with the
 * management of <a href="http://www.springsource.org/spring-data">Spring
 * Data</a> and MongoDB
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 02/03/2013
 * @version 1.0
 */
public abstract class AbstractRawVideoDAO implements RawVideoDataService {

	private ConfigurableApplicationContext ctx;

	protected final String RAW = "raw";

	public final String insertRawVideo(Video video) {
		String id = insertRawVideoImpl(video, getOperationHandler());
		ctx.close();
		return id;
	}

	/**
	 * Inserts a {@link Video} in the database
	 * 
	 * @param video
	 *            The {@link Video} to be inserted
	 * @param op
	 *            The {@link MongoOperations} that performs the operation
	 * @return The identifier of the {@link Video}
	 */
	protected abstract String insertRawVideoImpl(Video video, MongoOperations op);

	public final Collection<Video> getRawVideos() {
		Collection<Video> videos = getRawVideosImpl(getOperationHandler());
		ctx.close();
		return videos;
	}

	/**
	 * Gets all the {@link Video raw videos} stored in the database
	 * 
	 * @param op
	 *            The {@link MongoOperations} that performs the operation
	 * @return A {@link Collection} containing all the {@link Video raw videos}
	 */
	protected abstract Collection<Video> getRawVideosImpl(MongoOperations op);

	public final Video getRawVideoByID(String id) {
		Video video = getRawVideoByIdImpl(id, getOperationHandler());
		ctx.close();
		return video;
	}

	/**
	 * Implementation of the {@link #getRawVideoByID(String)} method to work
	 * with Spring Data and MongoDB
	 * 
	 * @param id
	 *            The identifier of the desired {@link Video}
	 * @param op
	 *            The {@link MongoOperations} that performs the operation
	 * @return The {@link Video} with the matching id
	 */
	protected abstract Video getRawVideoByIdImpl(String id, MongoOperations op);

	public final void deleteRawVideo(String id) {
		deleteRawVideoImpl(id, getOperationHandler());
		ctx.close();
	}

	/**
	 * Implementation of the {@link #deleteRawVideo(String)} method to work with
	 * Spring Data and MongoDB
	 * 
	 * @param id
	 *            The identifier of the {@link Video} to be deleted
	 * @param op
	 *            The {@link MongoOperations} that performs the operation
	 */
	protected abstract void deleteRawVideoImpl(String id, MongoOperations op);

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
