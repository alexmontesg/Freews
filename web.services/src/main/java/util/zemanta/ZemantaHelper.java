package util.zemanta;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import util.config.Config;

import com.business.BulletinManagerService;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.Syntax;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.sparql.engine.binding.Binding;
import com.hp.hpl.jena.sparql.engine.http.QueryExceptionHTTP;
import com.zemanta.api.Zemanta;
import com.zemanta.api.ZemantaResult;
import com.zemanta.api.suggest.Keyword;

/**
 * Class that interacts with Zemanta
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 12/03/2013
 * @version 1.0
 */
public class ZemantaHelper {

	private final String API_SERVICE_URL = "http://api.zemanta.com/services/rest/0.0/";
	private static Logger log = Logger.getLogger(BulletinManagerService.class);

	/**
	 * Suggest tags from a text
	 * 
	 * @param text
	 *            The text to get the tags from
	 * @return The tags in three languages (es, en and pt)
	 */
	public Map<String, Vector<String>> suggestTags(String text) {
		Zemanta zem = new Zemanta(Config.getProperty("zemanta.api.key"),
				API_SERVICE_URL);
		Map<String, Vector<String>> keywords = new HashMap<String, Vector<String>>();
		ZemantaResult zemResult = zem.suggest(getParameters(text));
		if (!zemResult.isError) {
			keywords = translateTags(zemResult.keywords);
		}
		return keywords;
	}

	/**
	 * Translates keywords from English into Spanish and Portuguese
	 * 
	 * @param keywords
	 *            The {@link Keyword}s in English
	 * @return The tags in English, Spanish and Portuguese
	 */
	private Map<String, Vector<String>> translateTags(
			Collection<Keyword> keywords) {
		Map<String, Vector<String>> keywordMap = getEnglishKeywords(keywords);
		String query = buildQuery(keywords);
		ResultSet rs = executeQuery(query);
		while (rs.hasNext()) {
			addKeyword(keywordMap, rs.next());
		}
		return keywordMap;
	}

	/**
	 * Iterates over a {@link QuerySolution} adding tags to a {@link Map}
	 * 
	 * @param keywordMap
	 *            The {@link Map} where the tags will be added
	 * @param qs
	 *            The {@link QuerySolution} to iterate
	 */
	private void addKeyword(Map<String, Vector<String>> keywordMap,
			QuerySolution qs) {
		String lang = qs.getLiteral("lang").getString();
		String keyword = qs.getLiteral("keyword").getString();
		if (!keywordMap.containsKey(lang)) {
			keywordMap.put(lang, new Vector<String>());
		}
		keywordMap.get(lang).add(keyword);
	}

	/**
	 * Gets all the keywords in English
	 * 
	 * @param keywords
	 *            A collection of {@link Keyword}s
	 * @return The {@link Map} with the proper representation of the
	 *         {@link Keyword}s
	 */
	private Map<String, Vector<String>> getEnglishKeywords(
			Collection<Keyword> keywords) {
		Map<String, Vector<String>> keywordMap = new HashMap<String, Vector<String>>();
		keywordMap.put("en", new Vector<String>());
		for (Keyword keyword : keywords) {
			keywordMap.get("en").add(keyword.name);
		}
		return keywordMap;
	}

	/**
	 * Builds the query to translate {@link Keyword}s
	 * 
	 * @param keywords
	 *            The {@link Keyword}s to translate
	 * @return The query to perform
	 */
	private String buildQuery(Collection<Keyword> keywords) {
		boolean first = true;
		StringBuffer query = new StringBuffer(
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>")
				.append("SELECT DISTINCT (str(?name) as ?keyword) (lang(?name) as ?lang) WHERE {");
		for (Keyword keyword : keywords) {
			if (!first) {
				query = query.append(" UNION ");
			}
			first = false;
			query = query.append("{ ?entity rdfs:label \"")
					.append(keyword.name).append("\"@en .");
			query = query.append("?entity rdfs:label ?name .}");
		}
		query = query.append("FILTER (langMatches(lang(?name), \"pt\") || ")
				.append("langMatches(lang(?name), \"es\")) }");
		return query.toString();
	}

	/**
	 * Gets the parameters to call Zemanta
	 * 
	 * @param text
	 *            The text that will be processed
	 * @return The parameters to make the Zemanta call
	 */
	private Map<String, String> getParameters(String text) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("method", "zemanta.suggest");
		parameters.put("api_key", Config.getProperty("zemanta.api.key"));
		parameters.put("text", text);
		parameters.put("return_images", "0");
		parameters.put("articles_limit", "0");
		return parameters;
	}

	/**
	 * Executes the query to translate the tags
	 * 
	 * @param queryStr
	 *            The query to execute
	 * @return The resulting {@link ResultSet}
	 */
	private ResultSet executeQuery(String queryStr) {
		Query query = QueryFactory.create(queryStr, Syntax.syntaxARQ);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://dbpedia.org/sparql", query);
		ResultSet rs;
		try {
			rs = qexec.execSelect();
		} catch (QueryExceptionHTTP e) {
			qexec.abort();
			qexec.close();
			log.error("Error translating keywords");
			log.error(e.getMessage());
			rs = emptyResultSet();
		}
		return rs;
	}

	/**
	 * Creates an empty {@link ResultSet}
	 * 
	 * @return An empty {@link ResultSet}
	 */
	private ResultSet emptyResultSet() {
		return new ResultSet() {
			public boolean hasNext() {
				return false;
			}

			public QuerySolution next() {
				return null;
			}

			public QuerySolution nextSolution() {
				return null;
			}

			public Binding nextBinding() {
				return null;
			}

			public int getRowNumber() {
				return 0;
			}

			public List<String> getResultVars() {
				return null;
			}

			public Model getResourceModel() {
				return null;
			}

			public void remove() {
			}
		};
	}

}
