package ho.boris.homelibrary;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@Entity
@Table(name = "realtors")
public class Realtor {

	@Id
	@Min(0)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String realtorFirstName;

	private String realtorLastName;

	private String realtorEmail;

	private long realtorPhoneNumber;

	@OneToMany(mappedBy = "realtor")
	private List<Home> homes;

	public Realtor() {

	}

	public Realtor(String fn, String ln, String e, long pn) {
		this.setRealtorFirstName(fn);
		this.setRealtorLastName(ln);
		this.setRealtorEmail(e);
		this.setRealtorPhoneNumber(pn);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRealtorFirstName() {
		return realtorFirstName;
	}

	public void setRealtorFirstName(String realtorFirstName) {
		this.realtorFirstName = realtorFirstName;
	}

	public String getRealtorLastName() {
		return realtorLastName;
	}

	public void setRealtorLastName(String realtorLastName) {
		this.realtorLastName = realtorLastName;
	}

	public String getRealtorEmail() {
		return realtorEmail;
	}

	public void setRealtorEmail(String realtorEmail) {
		this.realtorEmail = realtorEmail;
	}

	public long getRealtorPhoneNumber() {
		return realtorPhoneNumber;
	}

	public void setRealtorPhoneNumber(long realtorPhoneNumber) {
		this.realtorPhoneNumber = realtorPhoneNumber;
	}

	public List<Home> getHomes() {
		return homes;
	}

	public void setHomes(List<Home> homes) {
		this.homes = homes;
	}

}
