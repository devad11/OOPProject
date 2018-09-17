package application;

import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
/*LEFT TO DO:
 * ***FIXED***STILL LOOSE IF LAST NUM CORRECT
 * ***FIXED***CLOSE BUTTON
 * ***FIXED***ACTIVATE PRIZE TAB
*/
public class guess extends Tab {
	public static int random = (int)(Math.random() * 100 + 1);
	private static prize prizeTab = new prize();
	
	public guess() {

		this.setText("Guess Game");

		System.out.println(random);
		
		Stage window = new Stage();
		GridPane layout = new GridPane();
		layout.setStyle("-fx-background-color:YELLOW; -fx-opacity:1;");
		//layout.setGridLinesVisible(true);
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(10, 10, 10, 10));
		layout.setVgap(10);
		layout.setHgap(10);
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Guess Game");

		//OUT PUTS//
		Label label = new Label();
		label.setText("Guess a number\nbetween 1-100: ");
		GridPane.setConstraints(label, 0, 0);

		Label labelB = new Label();
		GridPane.setConstraints(labelB, 1, 3);
		
		TextField num = new TextField();
		num.setPrefWidth(5);
		GridPane.setConstraints(num, 1, 1);

		Button reset = new Button("Reset");
		GridPane.setConstraints(reset, 3, 2);
		
		Button closeButton = new Button("Close");
		GridPane.setConstraints(closeButton, 2, 2);
		
		Button button = new Button("Okay");
		GridPane.setConstraints(button, 1, 2);
		
		//ACTIONS//
		
		
		reset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Global.c = 5;
				random = (int)(Math.random() * 100 + 1);
				System.out.println(random);
				labelB.setText("New Game");
				num.requestFocus();
			}
		});
		
		//CLOSE BUTTON//
		closeButton.setOnAction(e -> Platform.exit());
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				isInt(num, num.getText(), random, layout, labelB);
				num.requestFocus();
			}
		});
		
		this.setContent(layout);
		layout.getChildren().addAll(label, num, button, closeButton, reset, labelB);
	}
	
	private int isInt(TextField num, String message, int random, GridPane layout, Label labelB){
		try{
			int guessNumber = Integer.parseInt(message);
			if (Global.c == 0 && guessNumber != random) {
				labelB.setText("Looser!!!!!!!!!!!");
				Global.c = 5;
				return 2;
			}
			else if (guessNumber < random && Global.c > 0) {
				Global.c--;
				labelB.setText("The correct number is\nBIGGER than that.\nNumber of tries left: " + (Global.c + 1));
				return 4;
			}
			else if (guessNumber > random && Global.c > 0) {
				Global.c--;
				labelB.setText("The correct number is\nSMALLER than that.\nNumber of tries left: " + (Global.c + 1));
				return 3;
			}
			else {
				labelB.setText("You won!!!");
				this.getTabPane().getTabs().add(prizeTab);
				prizeTab.setDisable(false);
				prizeTab.fillHashMap(4);
				prizeTab.makeButton();
				
				return 1;
			}

		}
		catch(NumberFormatException e){
			labelB.setText("Error:input is not a whole number");
			return 0;
		}
	}
	
	public static prize getPrizeTab() {
		return prizeTab;
	}

}