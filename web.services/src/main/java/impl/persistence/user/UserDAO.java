package impl.persistence.user;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.model.User;

/**
 * Implementation of {@link AbstractUserDAO}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 23/07/2012
 * @version 1.6
 */
public class UserDAO extends AbstractUserDAO {

	@Override
	protected User getUserImpl(String username, String password,
			MongoOperations op) {
		return op.findOne(new Query(Criteria.where("username").is(username)
				.and("password").is(password)), User.class, USERS);
	}

	@Override
	protected void addUserImpl(User user, MongoOperations op) {
		op.save(user, USERS);
	}

	@Override
	protected User getUserByFacebookIdImpl(String facebookId, MongoOperations op) {
		return op.findOne(
				new Query(Criteria.where("facebookId").is(facebookId)),
				User.class, USERS);
	}

	@Override
	protected boolean existsMailImpl(String mail, MongoOperations op) {
		return op.findOne(new Query(Criteria.where("mail").is(mail)),
				User.class, USERS) != null;
	}

	@Override
	protected boolean existsUsernameImpl(String username, MongoOperations op) {
		return op.findOne(new Query(Criteria.where("username").is(username)),
				User.class, USERS) != null;
	}

	@Override
	protected void deleteUserImpl(String username, MongoOperations op) {
		op.remove(new Query(Criteria.where("username").is(username)), USERS);
	}

	@Override
	protected int numberOfUsersImpl(MongoOperations op) {
		return op.findAll(User.class, USERS).size();
	}

	@Override
	protected void deleteAllImpl(MongoOperations op) {
		op.dropCollection(USERS);
	}

}
