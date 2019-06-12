package ho.boris.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

public class ButtonViewModel {

	private ButtonModel model;

	public ButtonViewModel() {
		model = new ButtonModel();
	}

	@FXML
	private Label counter;

	@FXML
	void press(ActionEvent event) {
		model.increaseCounter();
		update();
	}
	
	public void update() {
		counter.setText("Counter: " + model.getCounter());
	}

}
