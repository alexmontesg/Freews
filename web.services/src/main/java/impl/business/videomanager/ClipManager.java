package impl.business.videomanager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import org.apache.log4j.Logger;

import util.config.Config;
import util.videos.VideosUtil;
import util.zemanta.ZemantaHelper;

import com.business.ClipManagerService;
import com.model.Video;
import com.persistence.ClipDataService;
import com.persistence.RawVideoDataService;

/**
 * Defines the operations that can be done with {@link Video clips}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 05/09/2012
 * @version 1.5
 */
public class ClipManager implements ClipManagerService {

	/**
	 * Resizes a {@link Video clip} in a separate {@link Thread}
	 * 
	 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
	 *         García</a>
	 * @since 27/08/2012
	 * @version 1.2
	 */
	private static class Resizer extends Thread {

		private String newId;
		private File clip;

		public Resizer(String newId, File clip) {
			this.newId = newId;
			this.clip = clip;
		}

		@Override
		public void run() {
			String tempFileName = Config.getProperty("media.folder") + "temp"
					+ newId + Config.getProperty("media.file.extension");
			File tempFile = new File(tempFileName);
			clip.renameTo(tempFile);
			VideosUtil.resize(tempFileName, Config.getProperty("media.folder")
					+ newId + Config.getProperty("media.file.extension"));
			clip.delete();
			tempFile.delete();
			VideosUtil.takeScreenshot(Config.getProperty("media.folder")
					+ newId + Config.getProperty("media.file.extension"),
					Config.getProperty("poster.folder") + newId + ".jpeg");
		}
	}

	private static Logger log = Logger.getLogger(ClipManagerService.class);
	private RawVideoDataService rawVideoDataService;
	private ClipDataService clipDataService;

	public void setRawVideoDataService(RawVideoDataService rawVideoDataService) {
		this.rawVideoDataService = rawVideoDataService;
	}

	public void setClipDataService(ClipDataService clipDataService) {
		this.clipDataService = clipDataService;
	}

	public Collection<Video> getClips() {
		log.debug("Entrando en getVideos");
		Collection<Video> videos = new Vector<Video>();
		try {
			videos = clipDataService.getClips();
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			log.error(e.getCause() != null ? e.getCause().getMessage()
					: "Causa desconocida");
		}
		return videos;
	}

	public Collection<Video> getClipsByID(String[] ids) {
		log.debug("Entrando en getVideosByID");
		Collection<Video> videos = new ArrayList<Video>(ids.length);
		for (int i = 0; i < ids.length; i++) {
			videos.add(getClipByID(ids[i]));
		}
		return videos;
	}

	public Video getClipByID(String id) {
		log.debug("Entrando en getClipByID con id=" + id);
		Video video = null;
		try {
			video = clipDataService.getClipByID(id);
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			log.error(e.getCause() != null ? e.getCause().getMessage()
					: "Causa desconocida");
		}
		return video;
	}

	public void deleteClip(String id) {
		log.debug("Entrando en deleteClip con id=" + id);
		try {
			clipDataService.deleteClip(id);
			File mp4 = new File(Config.getProperty("media.folder") + id
					+ Config.getProperty("media.file.extension"));
			mp4.delete();
			File jpeg = new File(Config.getProperty("poster.folder") + id
					+ ".jpeg");
			jpeg.delete();
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			log.error(e.getCause() != null ? e.getCause().getMessage()
					: "Causa desconocida");
		}
	}

	public Collection<Video> freeTextSearch(String query, String lang) {
		log.debug("Entrando en freeTextSearch");
		Collection<Video> videos = new Vector<Video>();
		try {
			videos = clipDataService.freeTextSearch(query, lang);
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			log.error(e.getCause() != null ? e.getCause().getMessage()
					: "Causa desconocida");
		}
		return videos;
	}

	public Collection<Video> getClipsByUser(String username) {
		log.debug("Entrando en getVideosByUser");
		Collection<Video> videos = new Vector<Video>();
		try {
			videos = clipDataService.getClipsByUser(username);
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			log.error(e.getCause() != null ? e.getCause().getMessage()
					: "Causa desconocida");
		}
		return videos;
	}

	public void uploadClip(Map<String, String> description,
			Map<String, String> headline, String id, File clip, Date takenDate,
			Double lat, Double lon, Map<String, String> subtitles) {
		log.debug("Entrando en uploadClip");
		try {
			Video raw = rawVideoDataService.getRawVideoByID(id);
			raw.setTags(new ZemantaHelper().suggestTags(description.get("en")));
			raw.setDescription(description);
			raw.setHeadline(headline);
			raw.setDate(takenDate);
			raw.setLat(lat);
			raw.setLon(lon);
			String newId = insertClip(raw);
			Thread resizer = new Resizer(newId, clip);
			resizer.start();
			saveSubtitles(subtitles, newId);
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			log.error(e.getCause() != null ? e.getCause().getMessage()
					: "Causa desconocida");
		}
	}

	public void editClip(Map<String, String> descriptions,
			Map<String, String> headlines, Map<String, Vector<String>> tags,
			String id, Date takenDate, Double lat, Double lon) {
		Video video = new Video();
		video.setTags(tags);
		video.setDescription(descriptions);
		video.setHeadline(headlines);
		video.setDate(takenDate);
		video.setLat(lat);
		video.setLon(lon);
		Video oldVideo = getClipByID(id);
		video.setUploadedBy(oldVideo.getUploadedBy());
		video.setUploadDate(oldVideo.getUploadDate());
		try {
			String newId = insertClip(video);
			deleteClip(id);
			renameFiles(id, newId);
		} catch (RuntimeException e) {
			log.error("Cannot update clip: " + e.getMessage(), e);
		}
	}

	/**
	 * Renames the files related to a {@link Video clip} when the identifier
	 * changes
	 * 
	 * @param id
	 *            The old identifier
	 * @param newId
	 *            The new identifier
	 */
	private void renameFiles(String id, String newId) {
		moveFile(id, newId, Config.getProperty("media.folder"),
				Config.getProperty("media.file.extension"));
		moveFile(id, newId, Config.getProperty("poster.folder"), ".jpeg");
		moveFile(id, newId, Config.getProperty("subtitles.folder"), "_es.srt");
		moveFile(id, newId, Config.getProperty("subtitles.folder"), "_en.srt");
		moveFile(id, newId, Config.getProperty("subtitles.folder"), "_pt.srt");
	}

	/**
	 * Renames a file related to a {@link Video clip} when the identifier
	 * changes
	 * 
	 * @param oldId
	 *            The old identifier
	 * @param newId
	 *            The new identifier
	 * @param folder
	 *            The path where the file is
	 * @param ext
	 *            The extension of the file
	 */
	private void moveFile(String oldId, String newId, String folder, String ext) {
		String oldFileName = folder + oldId + ext;
		String newFileName = folder + newId + ext;
		try {
			if (!new File(oldFileName).renameTo(new File(newFileName))) {
				Files.copy(Paths.get(oldFileName), Paths.get(newFileName));
				Files.delete(Paths.get(oldFileName));
			}
		} catch (IOException e) {
			log.error("Cannot rename file " + oldFileName + " to "
					+ newFileName);
			log.error(e.getMessage());
		}
	}

	/**
	 * Inserts a {@link Video clip} into the database
	 * 
	 * @param clip
	 *            The {@link Video clip} to be inserted
	 * @return The identifier of the {@link Video clip}, <code>null</code> if
	 *         there is an error
	 */
	private String insertClip(Video clip) {
		String id = null;
		try {
			id = clipDataService.insertClip(clip);
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			log.error(e.getCause() != null ? e.getCause().getMessage()
					: "Causa desconocida");
		}
		return id;
	}

	/**
	 * Saves the subtitles of a {@link Video clip}
	 * 
	 * @param subtitles
	 *            The subtitles to be saved
	 * @param id
	 *            The identifier of the {@link Video clip}
	 */
	private void saveSubtitles(Map<String, String> subtitles, String id) {
		Iterator<Entry<String, String>> it = subtitles.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it
					.next();
			String filename = Config.getProperty("subtitles.folder") + id + "_"
					+ entry.getKey() + ".srt";
			File file = new File(filename);
			try {
				if (!file.exists()) {
					file.createNewFile();
				}
				BufferedWriter bw = new BufferedWriter(new FileWriter(
						file.getAbsoluteFile()));
				bw.write(entry.getValue());
				bw.close();
			} catch (IOException e) {
				log.error("Error salvando subtítulos");
				log.error(e.getMessage());
			}
		}
	}

	public void rateClip(String video_id, String user_id, double score) {
		// TODO Auto-generated method stub
	}

}
