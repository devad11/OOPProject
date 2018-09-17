package application;

import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.util.Arrays;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
/*LEFT TO DO:
 * ***FIXED***CLOSE BUTTON
 * ***FIXED***ACTIVATE PRIZE TAB
 * ***FIXED***DISABLE OKAY BUTTON UNTIL 6 NUMBER
 * ***FIXED***NO MORE THAN 6 NUMBER
 * ***FIXED***UNCLICK RADIO BUTTONS WHEN RESET
 * ***FIXED***CHECK RESET BUTTON
 * ***FIXED***PROG CRUSH AFTER MORE THAN 1 TRIES
 */
public class lotto extends Tab{
	private static prize prizeTab = new prize();
	private int[] random = new int[6];
	private int[] userChoice = new int[6];
	private	int max = 50;
	private RadioButton[] rButtons = new RadioButton[50];
	Label labelC = new Label();
	Label labelD = new Label();
	Label labelE = new Label();
	Label labelF = new Label();
	private Button button= new Button("Okay");

	public lotto() {

		this.setText("Lotto Game");
		Stage window = new Stage();
		GridPane layout = new GridPane();
		layout.setStyle("-fx-background-color:RED; -fx-opacity:1;");
		layout.setPadding(new Insets(10, 10, 10, 10));
		layout.setVgap(10);
		layout.setHgap(10);
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Lotto");

		//CREATE RANDOM NUMBERS//
		for (int i = 0; i < 6; i++) {
			random[i] = (int)(Math.random() * 50 + 1);
			boolean b = check(random);
			if (b == true)
				random[i] = (int)(Math.random() * 50 + 1);
		}
		//PRINT THE RANDOM NUMBERS//
		for (int i = 0; i < 6; i++) {
			System.out.print(random[i] + "\t");
		}
		System.out.println("");

		//LABELS//
		Label label = new Label();
		label.setText("Guess 6 number\nbetween 1-50: ");
		GridPane.setConstraints(label, 0, 0);
		//RANDOM NUMS//
		Label labelB = new Label();
		GridPane.setConstraints(labelB, 0, 3);
		labelB.setText(Arrays.toString(random));
		//USER_CHOICE NUMS//
		labelC = new Label();
		GridPane.setConstraints(labelC, 0, 4);
		//MATCHES//
		labelD = new Label();
		GridPane.setConstraints(labelD, 0, 5);
		//SEND TO PRIZE//
		labelE = new Label();
		GridPane.setConstraints(labelE, 0, 9);
		labelF = new Label();
		GridPane.setConstraints(labelF, 0, 10);

		//VARIABLES FOR BUTTONS//
		int column = 2;
		int row = 0;


		//Button button= new Button("Okay");	
		GridPane.setConstraints(button, 1, 8);
		Button reset = new Button("Reset");
		GridPane.setConstraints(reset, 3, 8);
		Button closeButton = new Button("Close");
		GridPane.setConstraints(closeButton, 2, 8);

		//OUTPUT BUTTONS//

		for (int i = 0; i < max; i++) {
			int shows = i + 1;
			column++;
			RadioButton rb = rButtons[i] = new RadioButton("" + shows);
			rb.setDisable(false);
			rb.setOnAction(e -> {
				if (rb.isSelected())
					Global.count++;
				else
					Global.count--;
				if (Global.count == 6)
					button.setDisable(false);
				else if (Global.count < 6)
					button.setDisable(true); 
				else if (Global.count > 6)
					button.setDisable(true);

			});
			if ( i % 10 == 0) {
				column = 2;
				row++;
			}
			GridPane.setConstraints(rButtons[i], column, row);
		}

		//RESET BUTTON//
		reset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				for (int i = 0; i < max; i++) {
					if (rButtons[i].isSelected())
					{		
						rButtons[i].setSelected(false);
					}
				}
				for (int i = 0; i < 6; i++) {
					random[i] = (int)(Math.random() * 50 + 1);
					boolean b = check(random);
					if (b == true)
						random[i] = (int)(Math.random() * 50 + 1);
					labelB.setText(Arrays.toString(random));
				}
				for (int i = 0; i < 6; i++) {
					userChoice[i] = 0;
				}
				button.setDisable(true);
				labelC.setText(Arrays.toString(userChoice));
				Global.count = 0;
				labelD.setText("" + Global.count);

			}

		});

		//CLOSE BUTTON//
		closeButton.setOnAction(e -> Platform.exit());


		//OKAY BUTTON//
		button.setDisable(true);
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				pressOkButton();
			}

		});

		//ADDING TO LAYOUT//
		layout.getChildren().add(labelC);
		layout.getChildren().add(labelD);
		for (int i = 0; i < max; i++) {
			layout.getChildren().addAll( rButtons[i]);
		}
		layout.getChildren().addAll(label, button, closeButton, reset, labelB, labelE, labelF);
		this.setContent(layout);
	}

	private void pressOkButton() {
		int j = 0;
		for (int i = 0; i < max; i++) {
			if (rButtons[i].isSelected())
			{		
				userChoice[j] = (i + 1);
				j++;
			}
		}
		labelC.setText(Arrays.toString(userChoice));
		for (int i = 0; i < 6; i++) {
			System.out.print(userChoice[i] + "\t");
		}
		Global.match = score();
		labelD.setText("" + Global.match);
		System.out.println();
		System.out.println(Global.match);

		if (Global.match > 3 && Global.match < 7) {
			button.setDisable(true);
			labelF.setText("Click prize tab!!!");
			if (Global.match == 4) {
				this.getTabPane().getTabs().add(prizeTab);
				prizeTab.setDisable(false);
				prizeTab.fillHashMap(4);
				prizeTab.makeButton();	
				labelE.setText("You win a 4* prize!");
			}
			if (Global.match == 5) {
				this.getTabPane().getTabs().add(prizeTab);
				prizeTab.setDisable(false);
				prizeTab.fillHashMap(5);
				prizeTab.makeButton();
				labelE.setText("You win a 5* prize!");
			}
			if (Global.match == 6) {
				this.getTabPane().getTabs().add(prizeTab);
				prizeTab.setDisable(false);
				prizeTab.fillHashMap(6);
				prizeTab.makeButton();
				labelE.setText("You win a 6* prize!");
			}
		}
	}


	private int score() {
		int point = 0;
		for (int i = 0; i < 6; i++) {
			for (int c = 0; c < 6; c++) {
				if (this.random[i] == this.userChoice[c]) {
					point++;
				}
			}
		}
		return point;
	}

	private boolean check(int[] random) {
		for (int i = 0; i < 6; i++) {
			for (int c = 0; c < 6; c++) {
				if (random[i] == random[c]) {
					return true;
				}
			}
		}
		return false;
	}
	public prize getPrizeTab() {
		return prizeTab;
	}
}