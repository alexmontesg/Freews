package impl.business.videomanager;

import java.util.Vector;

import org.apache.log4j.Logger;

import util.config.Config;
import util.videos.VideosUtil;

import com.business.BulletinManagerService;
import com.model.Bulletin;
import com.persistence.BulletinDataService;
import com.persistence.InfographicDataService;

/**
 * Defines the operations that can be done with {@link Bulletin}s
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 05/09/2012
 * @version 1.3
 */
public class BulletinManager implements BulletinManagerService {

	private static Logger log = Logger.getLogger(BulletinManagerService.class);
	private BulletinDataService bulletinDataService;
	private InfographicDataService infographicDataService;

	public void setBulletinDataService(BulletinDataService bulletinDataService) {
		this.bulletinDataService = bulletinDataService;
	}

	public void setInfographicDataService(
			InfographicDataService infographicDataService) {
		this.infographicDataService = infographicDataService;
	}

	public String createBulletin(String[] ids, String category,
			String username, String title, String lang) {
		Bulletin bulletin = new Bulletin();
		bulletin.setCategory(category);
		bulletin.setTitle(title);
		bulletin.setUser(username);
		String[] urls = new String[ids.length * 2 + 1];
		String id = null;
		try {
			Vector<String> videoIDs = setClips(ids, urls);
			setInfographics(category, urls);
			bulletin.setVideoIDs(videoIDs);
			id = bulletinDataService.insertBulletin(bulletin);
			VideosUtil.append(urls, Config.getProperty("bulletins.folder") + id
					+ Config.getProperty("media.file.extension"));
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			log.error(e.getCause() != null ? e.getCause().getMessage()
					: "Causa desconocida");
		}
		return id;
	}

	public Bulletin getBulletinByID(String id) {
		log.debug("Entrando en getBulletinByID con id=" + id);
		Bulletin bulletin = null;
		try {
			bulletin = bulletinDataService.getBulletinByID(id);
		} catch (RuntimeException e) {
			log.error(e.getMessage());
			log.error(e.getCause() != null ? e.getCause().getMessage()
					: "Causa desconocida");
		}
		return bulletin;
	}

	/**
	 * Sets the {@link com.model.Infographic infographics} to be used
	 * 
	 * @param category
	 *            The value of the {@link com.model.Category Category} for the
	 *            {@link Bulletin}
	 * @param urls
	 *            An array containing all the urls of the
	 *            {@link com.model.Video}s that form part of the
	 *            {@link Bulletin}
	 */
	private void setInfographics(String category, String[] urls) {
		urls[0] = Config.getProperty("headers.folder")
				+ infographicDataService.getRandomHeader(category).getId()
				+ Config.getProperty("media.file.extension");
		setBlasts(category, urls);
		urls[urls.length - 1] = Config.getProperty("endings.folder")
				+ infographicDataService.getRandomEnding(category).getId()
				+ Config.getProperty("media.file.extension");
	}

	/**
	 * Sets the {@link com.model.Infographic infographics} to be used
	 * 
	 * @param category
	 *            The value of the {@link com.model.Category Category} for the
	 *            {@link Bulletin}
	 * @param urls
	 *            An array containing all the urls of the
	 *            {@link com.model.Video}s that form part of the
	 *            {@link Bulletin}
	 */
	private void setBlasts(String category, String[] urls) {
		for (int i = 2; i < urls.length; i += 2) {
			urls[i] = Config.getProperty("blasts.folder")
					+ infographicDataService.getRandomBlast(category).getId()
					+ Config.getProperty("media.file.extension");
		}
	}

	/**
	 * Sets the {@link com.model.Video clips} to be used
	 * 
	 * @param ids
	 *            The identifiers of the {@link com.model.Video clips} to be
	 *            used
	 * @param urls
	 *            An array containing all the urls of the
	 *            {@link com.model.Video}s that form part of the
	 *            {@link Bulletin}
	 * @return The identifiers of the {@link com.model.Video clips} to be used
	 *         as a {@link Vector}
	 */
	private Vector<String> setClips(String[] ids, String[] urls) {
		Vector<String> videoIDs = new Vector<String>();
		for (int i = 0; i < ids.length; i++) {
			String output = Config.getProperty("media.folder") + ids[i]
					+ Config.getProperty("media.file.extension");
			urls[2 * i + 1] = output;
			videoIDs.add(ids[i]);
		}
		return videoIDs;
	}

}
