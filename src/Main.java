import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage window;
    private static Scene scene;
    private double _buttonSize = 200;
    public static double applicationHeight = 500;
    public static double applicationWidth = 500;
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
        quizButton.setPrefWidth(_buttonSize);
        quizButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new NewQuiz(FileLogic.wordlist, false).display();
            }
        });

        Button reviewButton = new Button("Review");
        reviewButton.setPrefWidth(_buttonSize);
        reviewButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new NewQuiz(FileLogic.reviewlist, true).display();
            }
        });


        Button statisticsButton = new Button("Statistics");
        statisticsButton.setPrefWidth(_buttonSize);
        statisticsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.setScene(new Statistics().constructScene());
            }
        });

        Button clearButton = new Button("Clear History");
        clearButton.setPrefWidth(_buttonSize);
        clearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new ClearHistory().display();
            }
        });


        //Layout
        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, quizButton, reviewButton, statisticsButton, clearButton);
        layout.setAlignment(Pos.CENTER);

        //Scene
        scene = new Scene(layout, 500, 500);


        window.setScene(scene);
        window.show();

    }

    public static void setMenu() {
        window.setScene(scene);
    }

}
