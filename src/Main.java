import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

public class Main extends Application {

    private Stage window;

    public static void main(String[] args) {
        FileLogic.createFiles();
	    launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Main window with menu
        window = primaryStage;
        window.setTitle("Spelling Mate");

        //Components
        Label label = new Label("Welcome to SpellMate!");
        Button quizButton = new Button("New Spelling Quiz");
        quizButton.setOnAction(event -> new NewQuiz().display());

        Button reviewButton = new Button("Review");
        reviewButton.setOnAction(event -> new ReviewQuiz().display());

        Button statisticsButton = new Button("Statistics");
        statisticsButton.setOnAction(event -> System.out.println("Statistics"));

        Button clearButton = new Button("Clear History");
        clearButton.setOnAction(event -> new ClearHistory().display());


        //Layout
        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, quizButton, reviewButton, statisticsButton, clearButton);
        layout.setAlignment(Pos.CENTER);

        //Scene
        Scene scene = new Scene(layout, 500, 500);

        window.setScene(scene);
        window.show();


    }

}
