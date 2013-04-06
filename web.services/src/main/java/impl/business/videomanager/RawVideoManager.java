package impl.business.videomanager;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import util.config.Config;
import util.videos.VideosUtil;

import com.business.RawVideoManagerService;
import com.model.Video;
import com.persistence.RawVideoDataService;

/**
 * Implementation of {@link RawVideoManagerService}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 05/09/2012
 * @version 1.3
 */
public class RawVideoManager implements RawVideoManagerService {

	/**
	 * Reencodes a {@link Video raw video} in a separate {@link Thread}
	 * 
	 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
	 *         García</a>
	 * @since 27/08/2012
	 * @version 1.1
	 */
	private static class Reencoder extends Thread {

		private String id;
		private File clip;

		public Reencoder(String id, File clip) {
			this.id = id;
			this.clip = clip;
		}

		@Override
		public void run() {
			String tempFileName = Config.getProperty("media.folder") + "raw"
					+ id;
			File tempFile = new File(tempFileName);
			clip.renameTo(tempFile);
			VideosUtil.reencode(tempFileName,
					Config.getProperty("media.folder") + id);
			tempFile.delete();
			clip.delete();
		}
	}

	private static Logger log = Logger.getLogger(RawVideoManager.class);
	private RawVideoDataService rawVideoDataService;

	public void setRawVideoDataService(RawVideoDataService rawVideoDataService) {
		this.rawVideoDataService = rawVideoDataService;
	}

	public Collection<Video> getRawVideos() {
		log.debug("Entrando en getVideos");
		Collection<Video> videos = new Vector<Video>();
		try {
			videos = rawVideoDataService.getRawVideos();
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			log.error(e.getCause() != null ? e.getCause().getMessage()
					: "Causa desconocida");
		}
		return videos;
	}

	public void uploadRaw(String description, String headline,
			Vector<String> tagsVector, String username, File clip, Date date,
			Double lat, Double lon, String lang) {
		log.debug("Entrando en uploadRaw");
		Video video = new Video();
		video.setUploadDate(new Date());
		video.setUploadedBy(username);
		video.setDate(date);
		video.setLat(lat);
		video.setLon(lon);
		Map<String, String> descriptions = new HashMap<String, String>();
		Map<String, String> headlines = new HashMap<String, String>();
		Map<String, Vector<String>> tags = new HashMap<String, Vector<String>>();
		descriptions.put(lang, description);
		headlines.put(lang, headline);
		tags.put(lang, tagsVector);
		video.setDescription(descriptions);
		video.setHeadline(headlines);
		video.setTags(tags);
		String id = insertRaw(video);
		Thread reencoder = new Reencoder(id, clip);
		reencoder.start();
	}

	public Video getRawVideoByID(String id) {
		log.debug("Entrando en getRawVideoByID con id=" + id);
		Video video = null;
		try {
			video = rawVideoDataService.getRawVideoByID(id);
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			log.error(e.getCause() != null ? e.getCause().getMessage()
					: "Causa desconocida");
		}
		return video;
	}

	public void deleteRawVideo(String id) {
		log.debug("Entrando en deleteRawVideo con id=" + id);
		try {
			rawVideoDataService.deleteRawVideo(id);
			File mp4 = new File(Config.getProperty("media.folder") + id
					+ Config.getProperty("media.file.extension"));
			mp4.delete();
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			log.error(e.getCause() != null ? e.getCause().getMessage()
					: "Causa desconocida");
		}
	}

	/**
	 * Inserts a {@link Video raw video} into the database
	 * 
	 * @param raw
	 *            The {@link Video raw video} to be inserted
	 * @return The identifier of the {@link Video raw video}, <code>null</code>
	 *         if there is an error
	 */
	private String insertRaw(Video raw) {
		String id = null;
		try {
			id = rawVideoDataService.insertRawVideo(raw);
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			log.error(e.getCause() != null ? e.getCause().getMessage()
					: "Causa desconocida");
		}
		return id;
	}

}