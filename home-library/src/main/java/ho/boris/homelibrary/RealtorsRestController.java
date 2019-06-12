package ho.boris.homelibrary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
@RequestMapping("/realtors")
public class RealtorsRestController {

	@Autowired
	private RealtorJpaRepository realtors;

	@RequestMapping(path = "/findRealtor", method = RequestMethod.GET)
	public List<Realtor> findRealtorByRealtorFirstName(@RequestParam String name) {
		return this.realtors.queryByRealtorName(name);
	}
	@RequestMapping(path = "", method = RequestMethod.POST)
	public void create(@RequestBody Realtor realtor) {
		realtors.save(realtor);
	}

	@RequestMapping(path = "/{realtorId}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) {
		realtors.deleteById(id);
	}

	@RequestMapping(path = "/{realtorId}", method = RequestMethod.GET)
	public Realtor retrieve(@PathVariable Integer realtorId) {
		return realtors.getOne(realtorId);
	}

	@RequestMapping(path = "", method = RequestMethod.GET)
	public List<Realtor> retrieveAll() {
		return realtors.findAll();
	}
 

}
