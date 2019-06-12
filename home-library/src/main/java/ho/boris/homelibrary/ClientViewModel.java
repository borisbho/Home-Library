package ho.boris.homelibrary;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpUtils;

import org.apache.http.HttpHeaders;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.aspectj.weaver.ast.Test;
import org.hibernate.validator.internal.util.privilegedactions.GetMethod;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.apache.http.client.methods.HttpDelete;

public class ClientViewModel {

	@Autowired
	HomeJpaRepository homes;

	@Autowired
	RealtorJpaRepository real;

	@FXML
	private Tab tab_login;

	@FXML
	private AnchorPane anchor_login;

	@FXML
	private TextField login_username_input;

	@FXML
	private TextField login_password_input;

	@FXML
	private Button login_submit_button;

	@FXML
	private Button login_reset_pw_button;

	@FXML
	private Button login_register_button;

	@FXML
	private TextField login_register_email_input;

	@FXML
	private Button login_register_sign_up_button;

	@FXML
	private Button login_create_user_button;

	@FXML
	private Button login_reset_button;

	@FXML
	private Tab tab_add;

	@FXML
	private AnchorPane anchor_add;

	@FXML
	private TextField add_home_address_input;

	@FXML
	private TextField add_home_city_input;

	@FXML
	private TextField add_home_state_input;

	@FXML
	private TextField add_home_size_input;

	@FXML
	private TextField add_home_price_input;

	@FXML
	private ImageView add_home_image_view;

	@FXML
	private TextField add_home_image_path_input;

	@FXML
	private Button add_home_image_button;

	@FXML
	private Button add_new_home_button;

	@FXML
	private Tab tab_delete;

	@FXML
	private AnchorPane anchor_delete;

	@FXML
	private TextField delete_home_id_input;

	@FXML
	private Button delete_button;

	@FXML
	private Tab tab_update;

	@FXML
	private AnchorPane anchor_update;

	@FXML
	private TextField update_home_address_input;

	@FXML
	private TextField update_home_city_input;

	@FXML
	private TextField update_home_state_input;

	@FXML
	private TextField update_home_size_input;

	@FXML
	private TextField update_home_price_input;

	@FXML
	private TextField update_home_id_search_input;

	@FXML
	private Button update_home_search_button;

	@FXML
	private Button update_save_button;

	@FXML
	private ImageView update_image_view;

	@FXML
	private TextField update_imagepath_input;

	@FXML
	private Button update_imagepath_button;

	@FXML
	private Tab tab_realtors;

	@FXML
	private AnchorPane anchor_realtors;

	@FXML
	private Label real_fname_label;

	@FXML
	private Label real_lname_label;

	@FXML
	private Label real_email_label;

	@FXML
	private Label real_phone_label;

	@FXML
	private TextField search_realtor_input;

	@FXML
	private Button search_realtor_button;

	@FXML
	private TextField realtor_add_home_input;

	@FXML
	private Button realtor_add_button;

	@FXML
	private TextField realtor_fname_input;

	@FXML
	private TextField realtor_lname_input;

	@FXML
	private TextField realtor_email_input;

	@FXML
	private TextField realtor_phone_input;

	@FXML
	private Button realtor_add_agent_button;

	@FXML
	private Tab tab_listings;

	@FXML
	private AnchorPane anchor_listings;

	@FXML
	private TableView<?> listings_table;

	@FXML
	private TableColumn<?, ?> list_column_id;

	@FXML
	private TableColumn<?, ?> list_column_address;

	@FXML
	private TableColumn<?, ?> list_city_column;

	@FXML
	private TableColumn<?, ?> list_state_column;

	@FXML
	private TableColumn<?, ?> list_price_column;

	@FXML
	private TableColumn<?, ?> list_size_column;

	@FXML
	private TableColumn<?, ?> list_agent_column;

	@FXML
	private TextField listings_search_city_input;

	@FXML
	private Button listings_search_city_button;

	@FXML
	private Tab tab_settings;

	@FXML
	private AnchorPane anchor_settings;

	@FXML
	private TextField setting_username_input;

	@FXML
	private TextField setting_new_password_input;

	@FXML
	private Button setting_change_password_button;

	public ClientViewModel() {
	}

	@FXML
	void addHome(ActionEvent event) throws UnsupportedCharsetException, ClientProtocolException, IOException {
		String address = add_home_address_input.getText();
		String city = add_home_city_input.getText();
		String state = add_home_state_input.getText();
		String size = add_home_size_input.getText().toString();
		String price = add_home_price_input.getText().toString();

		if (!address.isEmpty() && !city.isEmpty() && !state.isEmpty() && !size.isEmpty() && !price.isEmpty()) {
			String url = "http://localhost:8080/homes";
			HttpPost httpPost = new HttpPost(url);
			ObjectMapper mapper = new ObjectMapper();
			Home home = new Home();
			home.setHomeAddress(address);
			home.setHomeCity(city);
			home.setHomeState(state);
			home.setHomeSize(Float.parseFloat(size));
			home.setHomePrice(Float.parseFloat(price));

			StringEntity se = new StringEntity(mapper.writeValueAsString(home), ContentType.APPLICATION_JSON);
			httpPost.setEntity(se);
			CloseableHttpClient client = HttpClients.createDefault();
			CloseableHttpResponse response = client.execute(httpPost);
			System.out.println(response.getStatusLine().getStatusCode());
		}

	}

	@FXML
	void addNewAgent(ActionEvent event) throws ClientProtocolException, IOException {
		String fn = realtor_fname_input.getText();
		String ln = realtor_lname_input.getText();
		String email = realtor_email_input.getText();
		String phone = realtor_phone_input.getText();

		if (!fn.isEmpty() && !ln.isEmpty() && !email.isEmpty() && !phone.isEmpty()) {
			String url = "http://localhost:8080/realtors";

			Realtor real = new Realtor();
			HttpPost httPost = new HttpPost(url);
			ObjectMapper mapper = new ObjectMapper();
			real.setRealtorFirstName(fn);
			real.setRealtorLastName(ln);
			real.setRealtorEmail(email);
			real.setRealtorPhoneNumber(Long.parseLong(phone));

			StringEntity se = new StringEntity(mapper.writeValueAsString(real), ContentType.APPLICATION_JSON);
			httPost.setEntity(se);
			CloseableHttpClient client = HttpClients.createDefault();
			CloseableHttpResponse response = client.execute(httPost);
			System.out.println(response.getStatusLine().getStatusCode());
		}
	}

	@FXML
	void addNewUser(ActionEvent event) {

	}

	@FXML
	void addRealtorToHome(ActionEvent event) {
		
	}

	@FXML
	void addUploadImage(ActionEvent event) throws Exception {
		String file = add_home_image_path_input.getText();
		String bucketId = "001";
		int homeId = 2;

		String url = "http://localhost:8080/addHomeImage/" + bucketId + "/" + homeId + "?" + "file=" + file;
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = client.execute(httpPost);
		System.out.println(response.getStatusLine().getStatusCode());

		MongoInteractor interactor = new MongoInteractor();

		byte[] data = interactor.LoadImageLocal(add_home_image_path_input.getText());
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		BufferedImage bImage2 = ImageIO.read(bis);
		ImageIO.write(bImage2, "jpg", new File("output.jpg"));
		System.out.println("image created");
		ImageView image = (ImageView) add_home_image_view;
 		
		
		

 	}

	@FXML
	void changeUserPassword(ActionEvent event) throws ClientProtocolException, IOException {
		String username = setting_username_input.getText();
		String nPassword = setting_new_password_input.getText();

		if (!username.isEmpty() && !nPassword.isEmpty()) {
			String url = "http://localhost:8080/homes/change/" + username + "?password=" + nPassword;
			HttpPut httpPost = new HttpPut(url);
			CloseableHttpClient client = HttpClients.createDefault();
			CloseableHttpResponse response = client.execute(httpPost);
			System.out.println(response.getStatusLine().getStatusCode());

			setting_username_input.clear();
			setting_new_password_input.clear();
		}
	}

	@FXML
	void deleteHome(ActionEvent event) throws ClientProtocolException, IOException {
		int homeid = Integer.parseInt(delete_home_id_input.getText());

		if (homeid > 0) {
			String url = "http://localhost:8080/homes/" + homeid;
			HttpDelete del = new HttpDelete(url);

			CloseableHttpClient client = HttpClients.createDefault();
			CloseableHttpResponse response = client.execute(del);
			System.out.println(response.getStatusLine().getStatusCode());
		}
	}

	@FXML
	void findUpdateHome(ActionEvent event) {

		int i = Integer.parseInt(update_home_id_search_input.getText());
		System.out.println(i);
		Home home = homes.getOne(i);
		update_home_address_input.setText(home.getHomeAddress());
		update_home_city_input.setText(home.getHomeCity());
		update_home_state_input.setText(home.getHomeState());
		update_home_size_input.setText(String.valueOf(home.getHomeSize()));
		update_home_price_input.setText(String.valueOf(home.getHomePrice()));
	}

	@FXML
	void listingSearchCity(ActionEvent event) {
			homes.findHomeByHomeCity("");
	}

	@FXML
	boolean login(ActionEvent event) throws ClientProtocolException, IOException {
		String username = login_username_input.getText();
		String password = login_password_input.getText();
		String url = "http://localhost:8080/homes/login/" + username + "/" + password;

		if (!username.isEmpty() && !password.isEmpty()) {

			HttpPost httpPost = new HttpPost(url);
			HashMap map = new HashMap<String, String>();

			map.put("username", username);
			map.put("password", password);

			CloseableHttpClient client = HttpClients.createDefault();
			CloseableHttpResponse response = client.execute(httpPost);
			System.out.println(response.getStatusLine().getStatusCode());
			int resp = response.getStatusLine().getStatusCode();

			if (resp == 200) {

				String encoding = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
				httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);
				anchor_login.setVisible(false);
			}
		}
		return false;

	}

	@FXML
	void registerNewUser(ActionEvent event) throws ClientProtocolException, IOException {
		String username = login_username_input.getText();
		String password = login_password_input.getText();
		String email = login_register_email_input.getText();

		if (!username.isEmpty() && !password.isEmpty() && !email.isEmpty()) {
			String url = "http://localhost:8080/homes/register";
			HttpPost httpPost = new HttpPost(url);

			ObjectMapper mapper = new ObjectMapper();
			User nUser = new User();
			nUser.setUsername(username);
			nUser.setPassword(password);
			nUser.setEmail(email);
			StringEntity se = new StringEntity(mapper.writeValueAsString(nUser), ContentType.APPLICATION_JSON);
			httpPost.setEntity(se);
			CloseableHttpClient client = HttpClients.createDefault();
			CloseableHttpResponse response = client.execute(httpPost);
			System.out.println(response.getStatusLine().getStatusCode());

		}
		login_register_email_input.setVisible(false);
		login_reset_pw_button.setVisible(true);
		login_submit_button.setVisible(true);
		login_create_user_button.setVisible(false);
		login_register_button.setVisible(true);
	}

	@FXML
	void resetUserPassword(ActionEvent event) throws ClientProtocolException, IOException {

		String username = login_username_input.getText();

		if (!username.isEmpty()) {
			String url = "http://localhost:8080/homes/reset/" + username;

			HttpPost httpPost = new HttpPost(url);
			CloseableHttpClient client = HttpClients.createDefault();
			CloseableHttpResponse response = client.execute(httpPost);
			System.out.println(response.getStatusLine().getStatusCode());
		}

		login_reset_button.setVisible(false);
		login_password_input.setVisible(true);
		login_reset_pw_button.setVisible(true);
		login_submit_button.setVisible(true);
		login_register_button.setVisible(true);
	}

	@FXML
	void searchRealtor(ActionEvent event) throws ClientProtocolException, IOException {
		String name = search_realtor_input.getText();
		List<Realtor> re = new ArrayList<>();

		String uri = "http://localhost:8080/realtors/findRealtor?name=" + name;
		StringBuilder result = new StringBuilder();
		URL url = new URL(uri);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

	}

	@FXML
	void setUpRegistration(ActionEvent event) {
		login_register_email_input.setVisible(true);
		login_reset_pw_button.setVisible(false);
		login_submit_button.setVisible(false);
		login_create_user_button.setVisible(true);
		login_register_button.setVisible(false);

	}

	@FXML
	void setupResetPassword(ActionEvent event) {
		login_reset_button.setVisible(true);
		login_password_input.setVisible(false);
		login_reset_pw_button.setVisible(false);
		login_submit_button.setVisible(false);
		login_register_button.setVisible(false);
	}

	@FXML
	void updateHome(ActionEvent event) throws UnsupportedCharsetException, ClientProtocolException, IOException {

		Home hMo = homes.getOne(Integer.parseInt(update_home_id_search_input.getText()));

		String address = update_home_address_input.getText();
		String city = update_home_city_input.getText();
		String state = update_home_state_input.getText();
		String size = update_home_size_input.getText();
		String price = update_home_price_input.getText();
		if (!address.isEmpty() && !city.isEmpty() && !state.isEmpty() && !size.isEmpty() && !price.isEmpty()) {
			String url = "http://localhost:8080/homes/" + hMo;
			HttpPut put = new HttpPut(url);
			ObjectMapper mapper = new ObjectMapper();
			hMo.setHomeAddress(address);
			hMo.setHomeCity(city);
			hMo.setHomeState(state);
			hMo.setHomeSize(Float.parseFloat(size));
			hMo.setHomePrice(Float.parseFloat(price));

			StringEntity se = new StringEntity(mapper.writeValueAsString(hMo), ContentType.APPLICATION_JSON);
			put.setEntity(se);
			CloseableHttpClient client = HttpClients.createDefault();
			CloseableHttpResponse response = client.execute(put);
			System.out.println(response.getStatusLine().getStatusCode());
		}
	}

	@FXML
	void uploadUpdateImage(ActionEvent event) {

	}

}
