package ho.boris.homelibrary;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import ho.boris.config.Email;

@RestController
@RequestMapping("/homes")
public class HomeRestController {
	PasswordEncoder passwordEncoder;

	private MongoInteractor interactor;

	@Autowired
	private HomeJpaRepository homes;

	@Autowired
	private RealtorsRestController realtors;

	@Autowired
	GridFsTemplate gridFsTemplate;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(path = "", method = RequestMethod.POST)
	// @PreAuthorize("hasAuthority('ADMIN')")
	@Transactional
	public void create(@RequestBody Home newP) {
		homes.save(newP);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	// @PreAuthorize("hasAuthority('ADMIN')")
	@Transactional
	public void delete(@PathVariable int id) {
		homes.deleteById(id);

	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public Home retrieveHome(@PathVariable int id) {
		return homes.getOne(id);
	}

	@RequestMapping(path = "", method = RequestMethod.GET)
	public List<Home> retrieveAllHomes() {
		return homes.findAll();
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	@Transactional
	public void updateHomeFull(@RequestBody Home p, @PathVariable int id) {
		Home existing = homes.getOne(id);
		existing.copy(p);
		homes.save(existing);
	}

	@RequestMapping(path = "/{homeId}", method = RequestMethod.PATCH)
	@Transactional
	public void updateHomePartial(@PathVariable Integer homeId, @RequestBody Map<String, Object> updates) {
		Home home = retrieveHome(homeId);
		if (home == null) {
			throw new IllegalArgumentException("course id " + homeId + " does not exit");
		}
		for (String key : updates.keySet()) {
			if (key.equals("homeAddress")) {
				home.setHomeAddress((String) updates.get(key));
			} else if (key.equals("homeCity")) {
				home.setHomeCity((String) updates.get(key));
			} else if (key.equals("homeState")) {
				home.setHomeState((String) updates.get(key));
			} else if (key.equals("homePrice")) {
				home.setHomePrice((int) updates.get(key));
			} else if (key.equals("homeSize")) {
				home.setHomeSize((int) updates.get(key));
			}
		}
		homes.save(home);
	}

	@RequestMapping(path = "/{homeId}/realtors/{realtorId}", method = RequestMethod.POST)
	@Transactional
	public void addRealtorToHome(@PathVariable Integer homeId, @PathVariable Integer realtorId) {
		Home home = this.retrieveHome(homeId);
		Realtor realtor = this.realtors.retrieve(realtorId);
		home.setRealtor(realtor);
		homes.save(home);
	}

	@RequestMapping(path = "/findCity", method = RequestMethod.GET)
	public List<Home> findHomeByCity(@RequestParam String city) {
		return this.homes.queryByHomeCity(city);
	}

	@RequestMapping(path = "/register", method = RequestMethod.POST)
	public void register(@RequestBody User user) {

		userRepository.save(user);
	}

	@RequestMapping(path = "/change/{username}", method = RequestMethod.PUT)
	public void changePassword(@PathVariable String username, @RequestParam String password) {
		User existing = this.userRepository.findByUsername(username);
		if (existing != null)
			existing.setPassword(password);
		userRepository.save(existing);
	}

	@RequestMapping(path = "/login/{username}/{password}", method = RequestMethod.POST)
	public boolean userLogin(@PathVariable String username, @PathVariable String password) {

		User nUser = this.userRepository.findByUsername(username);

		if (nUser != null && nUser.getPassword().equals(password)) {
			nUser.getRoles().addAll(Arrays.asList("User"));
			return true;

		} else {
			throw new IllegalArgumentException();
		}
	}

	@RequestMapping(path = "/reset/{username}", method = RequestMethod.POST)
	public void resetPassword(@PathVariable String username) {
		User existing = this.userRepository.findByUsername(username);
		if (existing != null) {
			existing.setPassword("tempPassword001");
			ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			Email email = (Email) context.getBean("email");
			String sendEmailId = "bhospring@gmail.com";
			String receiverEmailId = existing.getEmail();
			String subject = "Temp Password";
			String message = "Temporary Password: " + existing.getPassword();
			email.sendEmail(sendEmailId, receiverEmailId, subject, message);
			userRepository.save(existing);
		}

	}

	@RequestMapping(path = "/addHomeImage/{bucketId}/{homeId}", method = RequestMethod.POST)
	public String addHomeImage(@PathVariable String bucketId, @PathVariable Integer homeId,
		@RequestParam MultipartFile file) throws Exception {
		File image = new File(file.getOriginalFilename());
		image.createNewFile();
		FileOutputStream fos = new FileOutputStream(image);
		fos.write(file.getBytes());
		fos.close();
		MongoInteractor interactor = new MongoInteractor();
		String id = interactor.addImageToMongo(image, bucketId);
		System.out.println("Image Added");
		return id;
	}

	@RequestMapping(path="/byte", method=RequestMethod.POST)
	public void byteImage(@RequestParam byte[] r) throws IOException {
		ByteArrayOutputStream box = new ByteArrayOutputStream();
		ByteArrayInputStream bis = new ByteArrayInputStream(r);
		BufferedImage bImage2 = ImageIO.read(bis);
		ImageIO.write(bImage2,  "jpg", new File("output.jpg"));
		System.out.println("Image Created");
	}
}
