package impl.presentation.upload;

import java.util.Vector;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Preloads the data for the upload of an {@link com.model.Infographic
 * Infographic}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @version 1.0
 * @since 12/09/2012
 */
public class UploadInfographicFormAction extends ActionSupport {

	private static final long serialVersionUID = 8788580543617589673L;
	private Vector<String> types;

	@Override
	public String execute() {
		types = new Vector<String>();
		types.add(getText("blast"));
		types.add(getText("header"));
		types.add(getText("ending"));
		return SUCCESS;
	}

	public Vector<String> getTypes() {
		return types;
	}

}
