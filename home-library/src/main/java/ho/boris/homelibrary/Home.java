package ho.boris.homelibrary;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "homes")
public class Home {

	@Id
	@Min(0)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String homeAddress;
	private String homeCity;
	private String homeState;
	private float homePrice;
	private float homeSize;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	private Realtor realtor;

	public Home() {

	}

	public Home(String hAddress, String hCity, String hState, float hPrice, float hSize, Realtor r) {
		this.setHomeAddress(hAddress);
		this.setHomeCity(hCity);
		this.setHomeState(hState);
		this.setHomePrice(hPrice);
		this.setHomeSize(hSize);
		this.setRealtor(r);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public String getHomeCity() {
		return homeCity;
	}

	public void setHomeCity(String homeCity) {
		this.homeCity = homeCity;
	}

	public String getHomeState() {
		return homeState;
	}

	public void setHomeState(String homeState) {
		this.homeState = homeState;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public float getHomePrice() {
		return homePrice;
	}

	public void setHomePrice(float homePrice) {
		this.homePrice = homePrice;
	}

	public float getHomeSize() {
		return homeSize;
	}

	public void setHomeSize(float homeSize) {
		this.homeSize = homeSize;
	}

	public Realtor getRealtor() {
		return realtor;
	}

	public void setRealtor(Realtor realtor) {
		this.realtor = realtor;
	}

	@Override
	public boolean equals(Object obj) {
		Home other = (Home) obj;
		return this.getHomeAddress().equals(other.getHomeAddress());
	}

	@Override
	public String toString() {
		return this.getHomeAddress() + ", " + this.getHomeCity() + ", " + this.getHomeState() + ", "
				+ this.getHomePrice() + ", " + this.getHomeSize();
	}

	public void copy(Home src) {
		this.setHomeAddress(src.getHomeAddress());
		this.setHomeCity(src.getHomeCity());
		this.setHomeState(src.getHomeState());
		this.setHomePrice(src.getHomePrice());
		this.setHomeSize(src.getHomeSize());
		this.setRealtor(src.getRealtor());

	}

}

//{
//	"homeAddress": "1025 W 800 N",
//	"homeCity": "Salt Lake City",
//	"homeState": "Utah",
//	"homePrice":335000,
//	"homeSize":1500,
//	"homeImage":"image here",
//	"homeVideo": "video here"
//}