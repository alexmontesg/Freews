package impl.persistence.video;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import util.config.Config;

import com.model.Rating;
import com.model.Video;
import com.persistence.ClipDataService;

/**
 * Abstract implementation of {@link ClipDataService} that helps with the
 * management of <a href="http://www.springsource.org/spring-data">Spring
 * Data</a> and MongoDB
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 02/03/2013
 * @version 1.0
 */
public abstract class AbstractClipDAO implements ClipDataService {

	private ConfigurableApplicationContext ctx;

	protected final String CLIPS = "clips";
	protected final String SOLR_PATH = Config.getProperty("solr");

	public final Collection<Video> getClips() {
		Collection<Video> clips = getClipsImpl(getOperationHandler());
		ctx.close();
		return clips;
	}

	/**
	 * Implementation of the {@link #getClips()} method to work with Spring Data
	 * and MongoDB
	 * 
	 * @param op
	 *            The {@link MongoOperations} that performs the operation
	 * @return A {@link Collection} containing all the {@link Video}s
	 */
	protected abstract Collection<Video> getClipsImpl(MongoOperations op);

	public final Collection<Video> getClipsByUser(String username) {
		SolrServer server = new HttpSolrServer(SOLR_PATH);
		SolrQuery query = new SolrQuery();
		query.setQuery("uploadedBy:" + username);
		QueryResponse rsp;
		try {
			rsp = server.query(query);
		} catch (SolrServerException e) {
			RuntimeException re = new RuntimeException(
					"Error en el servidor Solr", e);
			throw re;
		}
		return iterateOverSolrResult(rsp);
	}

	public final Video getClipByID(String id) {
		Video clip = getClipByIdImpl(id, getOperationHandler());
		ctx.close();
		return clip;
	}

	/**
	 * Implementation of the {@link #getClipByID(String)} method to work with
	 * Spring Data and MongoDB
	 * 
	 * @param id
	 *            The identifier of the {@link Video}
	 * @param op
	 *            The {@link MongoOperations} that performs the operation
	 * @return The {@link Video} with the given identifier
	 */
	protected abstract Video getClipByIdImpl(String id, MongoOperations op);

	public final Collection<Video> freeTextSearch(String query, String lang) {
		SolrServer server = new HttpSolrServer(SOLR_PATH);
		SolrQuery solrQuery = new SolrQuery();
		query = "text_" + lang + ":" + query.replaceAll(":", "");
		solrQuery.setQuery(query);
		QueryResponse rsp;
		try {
			rsp = server.query(solrQuery);
		} catch (SolrServerException e) {
			RuntimeException re = new RuntimeException(
					"Error en el servidor Solr", e);
			throw re;
		}
		return iterateOverSolrResult(rsp);
	}

	public final String insertClip(Video video) {
		String id = insertClipImpl(video, getOperationHandler());
		ctx.close();
		try {
			insertClipSolr(video);
		} catch (SolrServerException e) {
			RuntimeException re = new RuntimeException(
					"Error en el servidor Solr", e);
			deleteClip(id);
			throw re;
		} catch (IOException e) {
			RuntimeException re = new RuntimeException(
					"Error en el servidor Solr", e);
			deleteClip(id);
			throw re;
		}
		return id;
	}

	/**
	 * Implementation of the {@link #insertClip(Video)} method to work with
	 * Spring Data and MongoDB
	 * 
	 * @param video
	 *            The {@link Video} to be inserted
	 * @param op
	 *            The {@link MongoOperations} that performs the operationF
	 * @return The identifier of the inserted {@link Video}
	 */
	protected abstract String insertClipImpl(Video video, MongoOperations op);

	public final void deleteClip(String id) {
		SolrServer server = new HttpSolrServer(SOLR_PATH);
		try {
			server.deleteByQuery("id:" + id);
			try {
				deleteClipImpl(id, getOperationHandler());
				ctx.close();
			} catch (RuntimeException e) {
				server.rollback();
				throw e;
			}
			server.commit();
		} catch (SolrServerException e) {
			RuntimeException re = new RuntimeException(
					"Error en el servidor Solr", e);
			throw re;
		} catch (IOException e) {
			RuntimeException re = new RuntimeException(
					"Error en el servidor Solr", e);
			throw re;
		}
	}

	public final void deleteAll() {
		Collection<Video> clips = getClips();
		SolrServer server = new HttpSolrServer(SOLR_PATH);
		try {
			for (Video clip : clips) {
				server.deleteByQuery("id:" + clip.getId());
			}
			try {
				deleteAllImpl(getOperationHandler());
				ctx.close();
			} catch (RuntimeException e) {
				server.rollback();
				throw e;
			}
			server.commit();
		} catch (SolrServerException e) {
			RuntimeException re = new RuntimeException(
					"Error en el servidor Solr", e);
			throw re;
		} catch (IOException e) {
			RuntimeException re = new RuntimeException(
					"Error en el servidor Solr", e);
			throw re;
		}
	}

	/**
	 * Implementation of the {@link #deleteAll()} method to work with Spring
	 * Data and MongoDB
	 * 
	 * @param op
	 *            The {@link MongoOperations} that performs the operation
	 */
	protected abstract void deleteAllImpl(MongoOperations op);

	/**
	 * Implementation of the {@link #deleteClip(String)} method to work with
	 * Spring Data and MongoDB
	 * 
	 * @param id
	 *            The identifier of the {@link Video} to be deleted
	 * @param op
	 *            The {@link MongoOperations} that performs the operation
	 */
	protected abstract void deleteClipImpl(String id, MongoOperations op);

	public void rate(Rating rating) {
		rateImpl(rating, getOperationHandler());
	}

	/**
	 * Implementation of the {@link #rate(Rating)} method to work with Spring
	 * Data and MongoDB
	 * 
	 * @param rating
	 *            The rating to be added
	 * @param op
	 *            The {@link MongoOperations} that performs the operation
	 */
	protected abstract void rateImpl(Rating rating, MongoOperations op);

	/**
	 * Adds a {@link Video} to Solr
	 * 
	 * @param video
	 *            The {@link Video} to be added
	 * @throws SolrServerException
	 *             If there is an error in Solr
	 * @throws IOException
	 *             If there is an error adding or commiting to Solr
	 */
	protected void insertClipSolr(Video video) throws SolrServerException,
			IOException {
		SolrServer server = new HttpSolrServer(SOLR_PATH);
		SolrInputDocument clipSolr = new SolrInputDocument();
		clipSolr.addField("id", video.getId());
		clipSolr.addField("headline_es", video.getHeadline().get("es"));
		clipSolr.addField("description_es", video.getDescription().get("es"));
		clipSolr.addField("tags_es", video.getTags().get("es"));

		clipSolr.addField("headline_en", video.getHeadline().get("en"));
		clipSolr.addField("description_en", video.getDescription().get("en"));
		clipSolr.addField("tags_en", video.getTags().get("en"));

		clipSolr.addField("headline_pt", video.getHeadline().get("pt"));
		clipSolr.addField("description_pt", video.getDescription().get("pt"));
		clipSolr.addField("tags_pt", video.getTags().get("pt"));

		clipSolr.addField("uploadedBy", video.getUploadedBy());
		if (video.getDate() != null) {
			clipSolr.addField("date", video.getDate());
		}
		if (video.getLat() != null) {
			String geo = "" + video.getLat().doubleValue() + ", "
					+ video.getLon().doubleValue();
			clipSolr.addField("geo", geo);
		}
		server.add(clipSolr);
		server.commit();
	}

	/**
	 * Gets the {@link MongoOperations} item for this class
	 * 
	 * @return The {@link MongoOperations} item for this class
	 */
	private MongoOperations getOperationHandler() {
		ctx = new GenericXmlApplicationContext("mongo-config.xml");
		return (MongoOperations) ctx.getBean("mongoTemplate");
	}

	/**
	 * Iterates over a {@link QueryResponse}, retrieving the {@link Video}s
	 * contained in that result
	 * 
	 * @param rsp
	 *            The response of a query made in Solr
	 * @return A {@link Collection} containing the {@link Video}s from the
	 *         response
	 */
	private Collection<Video> iterateOverSolrResult(QueryResponse rsp) {
		Iterator<SolrDocument> iter = rsp.getResults().iterator();
		Collection<Video> videos = new ArrayDeque<Video>();
		while (iter.hasNext()) {
			SolrDocument resultDoc = iter.next();
			String id = resultDoc.getFieldValue("id").toString();
			videos.add(getClipByID(id));
		}
		return videos;
	}
}
