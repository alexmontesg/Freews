package test.persistence;

import impl.persistence.category.CategoryDAO;
import impl.persistence.infographic.InfographicDAO;
import impl.persistence.user.UserDAO;
import impl.persistence.video.ClipDAO;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.model.Category;
import com.model.Infographic;
import com.model.User;
import com.model.Video;
import com.persistence.CategoryDataService;
import com.persistence.ClipDataService;
import com.persistence.InfographicDataService;
import com.persistence.UserDataService;

public class PersistenceTest {

	private static UserDataService uds;
	private static ClipDataService cds;
	private static CategoryDataService catds;
	private static InfographicDataService ids;
	private User user;
	private Video video;
	private Category category;
	private Infographic header;

	@BeforeClass
	public static void setup() {
		uds = new UserDAO();
		uds.deleteAll();
		cds = new ClipDAO();
		cds.deleteAll();
		catds = new CategoryDAO();
		catds.deleteAll();
		ids = new InfographicDAO();
		ids.deleteAll();
	}

	@Before
	public void initUser() {
		user = new User();
		user.setAdmin(false);
		user.setCountry("Spain");
		user.setFacebookId("1234");
		user.setFirstName("Nombre");
		user.setLastName("Apellidos");
		user.setMail("nombre@apelli.dos");
		user.setPassword("1234");
		user.setUsername("admin");
	}

	@Before
	public void initCategory() {
		category = new Category();
		category.setValue("foo");
	}

	@Before
	public void initInfographic() {
		header = new Infographic();
		header.setCategory(category.getValue());
	}

	@Before
	public void initVideo() {
		video = new Video();
		Map<String, String> data = new HashMap<String, String>();
		Map<String, Vector<String>> tags = new HashMap<String, Vector<String>>();
		data.put("es", "foo");
		data.put("en", "foo");
		data.put("pt", "foo");
		Vector<String> v = new Vector<String>();
		v.add("tag");
		tags.put("es", v);
		tags.put("en", v);
		tags.put("pt", v);
		video.setDate(new Date());
		video.setDescription(data);
		video.setHeadline(data);
		video.setLat(40.0);
		video.setLon(-30.0);
		video.setTags(tags);
		video.setUploadDate(new Date());
		video.setUploadedBy("admin");
	}

	@Test
	public void testAdd() {
		Assert.assertFalse(uds.existsUsername(user.getUsername()));
		Assert.assertFalse(uds.existsMail(user.getMail()));
		int old = uds.numberOfUsers();
		uds.addUser(user);
		Assert.assertTrue(uds.existsUsername(user.getUsername()));
		Assert.assertTrue(uds.existsMail(user.getMail()));
		Assert.assertEquals(old + 1, uds.numberOfUsers());

		old = cds.getClips().size();
		cds.insertClip(video);
		Assert.assertEquals(old + 1, cds.getClips().size());

		old = catds.getCategories().size();
		catds.addCategory(category);
		Assert.assertEquals(old + 1, catds.getCategories().size());

		old = ids.getHeaders().size();
		ids.addHeader(header);
		Assert.assertEquals(old + 1, ids.getHeaders().size());
	}

	@Test
	public void testDelete() {
		uds.addUser(user);
		int old = uds.numberOfUsers();
		uds.deleteUser(user.getUsername());
		Assert.assertEquals(old - 1, uds.numberOfUsers());

		cds.insertClip(video);
		old = cds.getClips().size();
		cds.deleteClip(video.getId());
		Assert.assertEquals(old - 1, cds.getClips().size());

		catds.addCategory(category);
		old = catds.getCategories().size();
		catds.deleteCategory(category.getValue());
		Assert.assertEquals(old - 1, catds.getCategories().size());

		ids.addHeader(header);
		old = ids.getHeaders().size();
		ids.deleteHeader(header.getId());
		Assert.assertEquals(old - 1, ids.getHeaders().size());
	}

	@Test
	public void testGet() {
		uds.addUser(user);
		Assert.assertEquals(user.getUsername(), uds.getUser("admin", "1234")
				.getUsername());
		Assert.assertNull(uds.getUser("", ""));
		Assert.assertEquals(user.getUsername(), uds.getUserByFacebookId("1234")
				.getUsername());

		cds.insertClip(video);
		Assert.assertNotNull(cds.getClipByID(video.getId()));
		Assert.assertFalse(cds.getClipsByUser(user.getUsername()).size() <= 0);

		catds.addCategory(category);
		Assert.assertFalse(catds.getCategories().size() <= 0);

		Assert.assertNull(ids.getRandomHeader(header.getCategory()));
		ids.addHeader(header);
		Assert.assertNotNull(ids.getRandomHeader(header.getCategory()));
	}

	@Test
	public void testValidId() {
		uds.addUser(user);
		Assert.assertNotNull(user.getId());
		Assert.assertFalse(user.getId().trim().equalsIgnoreCase(""));

		cds.insertClip(video);
		Assert.assertNotNull(video.getId());
		Assert.assertFalse(video.getId().trim().equalsIgnoreCase(""));

		catds.addCategory(category);
		Assert.assertNotNull(category.getId());
		Assert.assertFalse(category.getId().trim().equalsIgnoreCase(""));

		ids.addHeader(header);
		Assert.assertNotNull(header.getId());
		Assert.assertFalse(header.getId().trim().equalsIgnoreCase(""));
	}

	@After
	public void delete() {
		uds.deleteUser(user.getUsername());
		cds.deleteClip(video.getId());
		catds.deleteCategory(category.getValue());
		ids.deleteHeader(header.getId());
	}

	@AfterClass
	public static void teardown() {
		uds.deleteAll();
		cds.deleteAll();
		catds.deleteAll();
		ids.deleteAll();
	}

}
