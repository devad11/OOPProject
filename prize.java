package application;

import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import javafx.geometry.*;

public class prize extends Tab{

	private Button[] buttons;
	private HashMap<String, String> hashMap;
	private GridPane layout = new GridPane();
	public prize() {

		Stage window = new Stage();	
		this.setText("Prize");
		this.setDisable(true);
		layout.setStyle("-fx-background-color:BLUE; -fx-opacity:1;");
		layout.setPadding(new Insets(10, 10, 10, 10));
		layout.setVgap(10);
		layout.setHgap(10);
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Prize");
		
		this.setContent(layout);
	}
	public void fillHashMap(int n) {
		hashMap = new HashMap<>();
		try { 
			File readMe = new File("prizeInfo.txt");
			FileReader fr = new FileReader(readMe);
			BufferedReader br = new BufferedReader(fr);
			String nextLine = br.readLine();
			while (nextLine != null) {
				String[] array = nextLine.split("-");
				int num = Integer.parseInt(array[1]);
				if (num == n) {
					String key = array[0];
					String value = array[2];
					hashMap.put(key, value);
				}
				nextLine = br.readLine();
			}
			
		}
		catch(IOException e){
			System.out.println("ERROR");
		}
		
	}
	public void makeButton () {
		int size = hashMap.size();
		buttons = new Button[size];
		int i = 0;
		for (HashMap.Entry<String, String> entry: this.hashMap.entrySet()) {
			buttons[i] = new Button(entry.getKey());
			int j = i;
			this.layout.getChildren().add(buttons[i]);
			buttons[i].setOnAction(e -> {
				Alert alert = new Alert(AlertType.CONFIRMATION, "Your prize is: " + entry.getValue(), ButtonType.CANCEL);
				alert.showAndWait();
				
				if (alert.getResult() == ButtonType.CANCEL) {
				    this.setDisable(true); 
				    this.layout.getChildren().removeAll(buttons);
				}
			});
			GridPane.setConstraints(buttons[i], 2, j);
			i++;			
		}
	}
}