package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class javaFXtest extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Adam's Casino!");
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(8);
		grid.setHgap(10);

		TabPane tabPane = new TabPane();
		guess guessingGame = new guess();
		lotto lottoGame = new lotto();
		prize prize = guess.getPrizeTab();
		
		
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		tabPane.getTabs().add(guessingGame);
		tabPane.getTabs().add(lottoGame);
		
		tabPane.getTabs().add(prize);

		StackPane sp = new StackPane();
		sp.getChildren().add(tabPane);
		Scene scene = new Scene(sp, 800, 800, Color.WHITE);
		primaryStage.setScene(scene);
		primaryStage.show();
	}   
}