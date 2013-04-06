package impl.business.infographicmanager;

import java.io.File;

import org.apache.log4j.Logger;

import util.config.Config;
import util.videos.VideosUtil;

import com.business.InfographicManagerService;
import com.model.Infographic;
import com.persistence.InfographicDataService;

/**
 * Implementation of the {@link InfographicManagerService}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 10/08/2012
 * @version 1.4
 */
public class InfographicManager implements InfographicManagerService {

	/**
	 * Resizes an {@link Infographic} in a separate {@link Thread}
	 * 
	 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
	 *         García</a>
	 * @since 27/08/2012
	 * @version 1.1
	 */
	private static class Resizer extends Thread {
		private File infographic;
		private String tempFileName;
		private String newFilename;

		public Resizer(File infographic, String id, String newFilename) {
			this.infographic = infographic;
			tempFileName = Config.getProperty("media.folder") + "tempinf" + id
					+ Config.getProperty("media.file.extension");
			this.newFilename = newFilename;
		}

		@Override
		public void run() {
			File tempFile = new File(tempFileName);
			infographic.renameTo(tempFile);
			VideosUtil.resize(tempFileName, newFilename);
			infographic.delete();
			tempFile.delete();
		}
	}

	private InfographicDataService infographicDataService;
	private static Logger log = Logger
			.getLogger(InfographicManagerService.class);

	public void setInfographicDataService(
			InfographicDataService infographicDataService) {
		this.infographicDataService = infographicDataService;
	}

	public void uploadBlast(String category, File infographic) {
		log.debug("Entrando en uploadBlast");
		Infographic blast = new Infographic();
		blast.setCategory(category);
		String id = insertBlast(blast);
		uploadInfographic(infographic, id, Config.getProperty("blasts.folder")
				+ id + Config.getProperty("media.file.extension"));
	}

	public void uploadHeader(String category, File infographic) {
		log.debug("Entrando en uploadHeader");
		Infographic header = new Infographic();
		header.setCategory(category);
		String id = insertHeader(header);
		uploadInfographic(infographic, id, Config.getProperty("headers.folder")
				+ id + Config.getProperty("media.file.extension"));
	}

	public void uploadEnding(String category, File infographic) {
		log.debug("Entrando en uploadEnding");
		Infographic ending = new Infographic();
		ending.setCategory(category);
		String id = insertEnding(ending);
		uploadInfographic(infographic, id, Config.getProperty("endings.folder")
				+ id + Config.getProperty("media.file.extension"));
	}

	/**
	 * Uploads an {@link Infographic}
	 * 
	 * @param infographic
	 *            The {@link File} to be uploaded
	 * @param id
	 *            The id of the {@link Infographic}
	 * @param filename
	 *            The filename of the {@link Infographic}
	 */
	private void uploadInfographic(File infographic, String id, String filename) {
		Thread resizer = new Resizer(infographic, id, filename);
		resizer.start();
	}

	/**
	 * Inserts a blast into the database
	 * 
	 * @param blast
	 *            The blast to be saved
	 * @return The identifier of the blast, <code>null</code> if there is an
	 *         error
	 */
	private String insertBlast(Infographic blast) {
		String id = null;
		try {
			id = infographicDataService.addBlast(blast);
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			log.error(e.getCause() != null ? e.getCause().getMessage()
					: "Causa desconocida");
		}
		return id;
	}

	/**
	 * Inserts a header into the database
	 * 
	 * @param header
	 *            The header to be saved
	 * @return The identifier of the header, <code>null</code> if there is an
	 *         error
	 */
	private String insertHeader(Infographic header) {
		String id = null;
		try {
			id = infographicDataService.addHeader(header);
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			log.error(e.getCause() != null ? e.getCause().getMessage()
					: "Causa desconocida");
		}
		return id;
	}

	/**
	 * Inserts an ending into the database
	 * 
	 * @param ending
	 *            The ending to be saved
	 * @return The identifier of the ending, <code>null</code> if there is an
	 *         error
	 */
	private String insertEnding(Infographic ending) {
		String id = null;
		try {
			id = infographicDataService.addEnding(ending);
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			log.error(e.getCause() != null ? e.getCause().getMessage()
					: "Causa desconocida");
		}
		return id;
	}
}