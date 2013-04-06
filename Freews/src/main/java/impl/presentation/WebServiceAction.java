package impl.presentation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;

import util.config.Config;

import com.opensymphony.xwork2.ActionSupport;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * Helper action that calls a Web Service and performs other common actions
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 03/02/2013
 * @version 1.0
 */
public abstract class WebServiceAction extends ActionSupport {

	private static final long serialVersionUID = -7868228519735263077L;
	protected Logger log = Logger.getLogger(this.getClass());

	/**
	 * Gets the {@link WebResource} that is going to be called
	 * 
	 * @param path
	 *            The address of the {@link WebResource}
	 * @return The {@link WebResource} that is going to be called
	 */
	protected WebResource getWebResource(String path) {
		return getClient().resource(getBackend() + path);
	}

	/**
	 * Populates a map of parameters to be passed to the web service
	 * 
	 * @param args
	 *            Pairs of key, value to be passed to the web service
	 * @return The {@link MultivaluedMap} of parameters
	 */
	protected MultivaluedMap<String, String> populate(String... args) {
		if (args.length % 2 != 0) {
			throw new IllegalArgumentException(
					"One argument name without value");
		}
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		for (int i = 0; i < args.length; i += 2) {
			params.add(args[i], args[i + 1]);
		}
		return params;
	}

	/**
	 * Converts an array of strings to a CSV String using the semicolon as
	 * separator
	 * 
	 * @param array
	 *            The array of strings to be converted
	 * @return The CSV string
	 */
	protected String arrayToCSV(String[] array) {
		StringBuilder csv = new StringBuilder(array[0]);
		for (int i = 1; i < array.length; i++) {
			csv.append(";").append(array[i]);
		}
		return csv.toString();
	}

	/**
	 * Converts a {@link Map} to a CSV string using the semicolon as separator
	 * of entries and the colon as the separator of pairs key, value
	 * 
	 * @param map
	 *            The {@link Map} to be converted
	 * @return The resulting string
	 */
	protected String mapToCSV(Map<String, String> map) {
		StringBuilder csv = new StringBuilder();
		boolean first = true;
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (!first) {
				csv.append(";");
			}
			first = false;
			csv.append(entry.getKey() + ":" + entry.getValue());
		}
		return csv.toString();
	}

	/**
	 * Converts a {@link Map} of {@link Vector}s to a CSV string using the
	 * semicolon as separator of entries the colon as the separator of pairs key
	 * and, for each key, the comma as separator of different values for that
	 * key
	 * 
	 * @param map
	 *            The {@link Map} to be converted
	 * @return The resulting string
	 */
	protected String vectorMapToCSV(Map<String, Vector<String>> map) {
		StringBuilder csv = new StringBuilder();
		boolean first = true;
		for (Map.Entry<String, Vector<String>> entry : map.entrySet()) {
			if (!first) {
				csv.append(";");
			}
			first = false;
			StringBuilder tags = new StringBuilder();
			boolean firstTag = true;
			for (String tag : entry.getValue()) {
				if (!firstTag) {
					tags.append(",");
				}
				firstTag = false;
				tags.append(tag);
			}
			csv.append(entry.getKey() + ":" + tags);
		}
		return csv.toString();
	}

	/**
	 * Downloads a file
	 * 
	 * @param path
	 *            The path where the file is in the server
	 * @param title
	 *            The title suggested for the downloaded file
	 * @param response
	 *            The response where the file will be sent
	 */
	protected void downloadFile(String path, String title,
			HttpServletResponse response) {
		File file = new File(path);
		try {
			FileInputStream in = new FileInputStream(file);
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ title + "." + Config.getProperty("media.file.extension"));
			response.setContentLength((int) file.length());
			ServletOutputStream out = response.getOutputStream();
			byte[] outputByte = new byte[4096];
			while (in.read(outputByte, 0, 4096) != -1) {
				out.write(outputByte, 0, 4096);
			}
			in.close();
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			log.error("Archivo no encontrado: " + e.getMessage());
		} catch (IOException e) {
			log.error("Error enviando archivo: " + e.getMessage());
		}
	}

	/**
	 * Checks if a collection of fields are filled
	 * 
	 * @param fields
	 *            The fields to be checked
	 * @return <tt>true</tt> if all the fields are filled, <tt>false</tt>
	 *         otherwise
	 */
	protected boolean areFilledFields(String... fields) {
		for (String field : fields) {
			if (field == null || field.trim().isEmpty()) {
				return false;
			}
		}
		return true;
	}

	private Client getClient() {
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,
				Boolean.TRUE);
		return Client.create(clientConfig);
	}

	private String getBackend() {
		return Config.getProperty("web.services.url");
	}

}
