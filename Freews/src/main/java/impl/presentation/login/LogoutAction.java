package impl.presentation.login;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.model.User;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Logs out a {@link User}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 29/08/2012
 * @version 1.0
 */
public class LogoutAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = -6759882623134364308L;
	private Map<String, Object> session;
	private Logger log = Logger.getLogger(this.getClass());

	@Override
	public String execute() {
		log.debug("Ejecutando LogoutAction");
		session.clear();
		return SUCCESS;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
