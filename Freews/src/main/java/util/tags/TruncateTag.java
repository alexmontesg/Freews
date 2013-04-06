package util.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.opensymphony.xwork2.ActionContext;

/**
 * JSP Tag that truncates text
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 02/03/2012
 * @version 1.0
 */
public class TruncateTag extends SimpleTagSupport {

	private String text;
	private int max;

	@Override
	public void doTag() throws JspException, IOException {
		PageContext pageContext = (PageContext) getJspContext();
		JspWriter out = pageContext.getOut();
		if (text.length() > max) {
			out.println("<abbr title=\"" + text + "\">"
					+ text.substring(0, max) + "...</abbr>");
		} else {
			out.println(text);
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = (String) ActionContext.getContext().getValueStack()
				.findValue(text);
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

}
