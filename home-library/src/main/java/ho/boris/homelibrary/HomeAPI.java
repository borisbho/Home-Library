package ho.boris.homelibrary;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
 import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;


 @SpringBootApplication
@ComponentScan
@ComponentScan("ho.boris.*")
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 public class HomeAPI extends Application {

	public static void main(String[] args) {
		SpringApplication.run(HomeAPI.class, args);
		 launch(args);
	}
	
	@SuppressWarnings("restriction")
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ClientView.fxml"));
		primaryStage.setTitle("Button");
		Scene s = new Scene(root);
		primaryStage.setScene(s);
		primaryStage.show();
	}
}
