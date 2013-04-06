package util.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Gets properties from the settings file
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 03/02/2013
 * @version 1.0
 */
public class Config {

	private static Properties properties;

	private static final String FILE_NAME = "settings.properties";

	/**
	 * Gets the value of a property
	 * 
	 * @param propertyName
	 *            The property to be retrieved
	 * @return The value of the property with the given name
	 */
	public static String getProperty(String propertyName) {
		if (properties == null) {
			properties = new Properties();
			try {
				properties.load(getLocalStream(FILE_NAME));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return properties.getProperty(propertyName);
	}

	private static InputStream getLocalStream(String resourceName) {
		return Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(resourceName);
	}

}
