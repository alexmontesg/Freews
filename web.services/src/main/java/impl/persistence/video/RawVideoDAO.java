package impl.persistence.video;

import java.util.Collection;

import org.springframework.data.mongodb.core.MongoOperations;

import com.model.Video;

/**
 * Implementation of {@link AbstractRawVideoDAO}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 23/07/2012
 * @version 1.5
 */
public class RawVideoDAO extends AbstractRawVideoDAO {

	@Override
	protected String insertRawVideoImpl(Video video, MongoOperations op) {
		op.save(video, RAW);
		return video.getId();
	}

	@Override
	protected Collection<Video> getRawVideosImpl(MongoOperations op) {
		return op.findAll(Video.class, RAW);
	}

	@Override
	protected Video getRawVideoByIdImpl(String id, MongoOperations op) {
		return op.findById(id, Video.class, RAW);
	}

	@Override
	protected void deleteRawVideoImpl(String id, MongoOperations op) {
		op.remove(op.findById(id, Video.class, RAW), RAW);
	}
	
}
