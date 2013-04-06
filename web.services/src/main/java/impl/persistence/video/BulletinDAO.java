package impl.persistence.video;

import org.springframework.data.mongodb.core.MongoOperations;

import com.model.Bulletin;

/**
 * Implementation of {@link AbstractBulletinDAO}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 23/07/2012
 * @version 1.4
 */
public class BulletinDAO extends AbstractBulletinDAO {

	@Override
	protected String insertBulletinImpl(Bulletin bulletin, MongoOperations op) {
		op.save(bulletin, BULLETINS);
		return bulletin.getId();
	}

	@Override
	protected Bulletin getBulletinByIDImpl(String id, MongoOperations op) {
		return op.findById(id, Bulletin.class, BULLETINS);
	}

}
