package impl.presentation.login;

import org.apache.struts2.StrutsStatics;

import com.model.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * Intercepts requests and filters them if the user is not logged in or tries to
 * access to forbidden resources Logs in a {@link User}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 23/07/2012
 * @version 1.3
 */
public class LoginInterceptor extends AbstractInterceptor implements
		StrutsStatics {

	private static final long serialVersionUID = -4077390198609734426L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ctx = invocation.getInvocationContext();
		User user = (User) ctx.getSession().get("user");
		String namespace = invocation.getProxy().getNamespace().toLowerCase();
		if (!checkLogin(user, namespace)) {
			return "login";
		}
		if (!checkAdmin(user, namespace)) {
			return "error";
		}
		return invocation.invoke();
	}

	/**
	 * Checks if a {@link User} has to log in to access to a resource
	 * 
	 * @param user
	 *            The {@link User} to be checked
	 * @param uri
	 *            The resource he wants
	 * @return <tt>true</tt> if the {@link User} can access to the resource,
	 *         <tt>false</tt> if he needs to log in
	 */
	private boolean checkLogin(User user, String namespace) {
		return user != null || namespace.equalsIgnoreCase("/public");
	}

	/**
	 * Checks if a {@link User} has to be an admin to access to a resource
	 * 
	 * @param user
	 *            The {@link User} to be checked
	 * @param uri
	 *            The resource he wants
	 * @return <tt>true</tt> if the user can access to the resource,
	 *         <tt>false</tt> if he needs to be an admin
	 */
	private boolean checkAdmin(User user, String namespace) {
		return !namespace.equalsIgnoreCase("/admin")
				|| (user != null && user.getAdmin());
	}

}