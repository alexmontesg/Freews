package impl.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

/**
 * Class with common methods for other web services
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 03/02/2013
 * @version 1.0
 */
public abstract class FreewsServices {

	protected Logger log = Logger.getLogger(this.getClass());

	/**
	 * Converts an {@link InputStream} to a {@link File}, writing it
	 * 
	 * @param fileName
	 *            The name the {@link File} is taking
	 * @param inputStream
	 *            The {@link InputStream} to be converted
	 * @return The resulting {@link File}
	 * @throws IOException
	 *             If there is an error writing the {@link File} or reading the
	 *             {@link InputStream}
	 */
	protected File inputStreamToFile(String fileName, InputStream inputStream)
			throws IOException {
		File clip = new File(fileName);
		OutputStream out = new FileOutputStream(clip);
		int read = 0;
		byte[] bytes = new byte[1024];
		while ((read = inputStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		inputStream.close();
		out.flush();
		out.close();
		return clip;
	}

	/**
	 * Converts two Strings to an array of doubles containing a location
	 * 
	 * @param lat
	 *            The String representing the latitude
	 * @param lon
	 *            Th String representing the longitude
	 * @return An array with a size of two, having the latitude in the first
	 *         position and the longitude in the second one
	 */
	protected double[] parseLocation(String lat, String lon) {
		double[] location = new double[2];
		try {
			location[0] = Double.valueOf(lat);
			location[1] = Double.valueOf(lon);
			if (Math.abs(location[0]) > 90 || Math.abs(location[1]) > 180) {
				IllegalArgumentException e = new IllegalArgumentException(lat
						+ ", " + lon + " is an invalid location");
				log.error("Error parsing location, taking the old one", e);
				throw e;
			}
		} catch (NullPointerException e) {
			throw new IllegalArgumentException(lat + ", " + lon
					+ " is an invalid location", e);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(lat + ", " + lon
					+ " is an invalid location", e);
		}
		return location;
	}

}
