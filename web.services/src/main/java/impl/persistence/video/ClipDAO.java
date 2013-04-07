package impl.persistence.video;

import java.util.Collection;

import org.springframework.data.mongodb.core.MongoOperations;

import com.model.Rating;
import com.model.Video;

/**
 * Implementation of {@link AbstractClipDAO}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 23/07/2012
 * @version 1.5
 */
public class ClipDAO extends AbstractClipDAO {
	
	@Override
	protected Collection<Video> getClipsImpl(MongoOperations op) {
		return op.findAll(Video.class, CLIPS);
	}

	@Override
	protected Video getClipByIdImpl(String id, MongoOperations op) {
		return op.findById(id, Video.class, CLIPS);
	}

	@Override
	protected String insertClipImpl(Video video, MongoOperations op) {
		op.save(video, CLIPS);
		return video.getId();
	}

	@Override
	protected void deleteClipImpl(String id, MongoOperations op) {
		op.remove(op.findById(id, Video.class, CLIPS), CLIPS);
	}
	
	@Override
	protected void deleteAllImpl(MongoOperations op) {
		op.dropCollection(CLIPS);
	}
	
	@Override
	protected void rateImpl(Rating rating, MongoOperations op) {
		op.save(rating);
	}

}
