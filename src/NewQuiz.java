import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by Fraser McIntosh on 18/08/2016.
 */
public class NewQuiz {
    private Stage window;
    private Scene scene1, scene2;
    private int width, height;
    public void display() {
        window = new Stage();
        int width = 300;
        int height = 300;

        //Block user interaction with other windows until this window is
        // dealt with
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Spelling Quiz");
        window.setMinWidth(250);

        //Components
        Label label = new Label();
        label.setText("Spelling quiz");
        Button startButton = new Button("Start Quiz");
        startButton.setOnAction(e -> startQuiz());

        //Layout
        VBox layout1 = new VBox(10);
        layout1.getChildren().addAll(label, startButton);
        layout1.setAlignment(Pos.CENTER);

        //Scene
        scene1 = new Scene(layout1, width, height);

        //Components 2
        TextField input = new TextField("Spell word here");
        Button checkButton = new Button ("Check");
        checkButton.setOnAction(event -> System.out.println("Check!"));

        //Layout
        VBox layout2 = new VBox(2);
        layout2.getChildren().addAll(input, checkButton);
        layout2.setAlignment(Pos.CENTER);

        scene2 = new Scene(layout2, width, height);

        //Window
        window.setScene(scene1);
        //Needs to be closed before returning
        window.showAndWait();


    }
    private void startQuiz() {
        window.setScene(scene2);
        System.out.println("Quiz started");
        QuizLogic logic = new QuizLogic("wordlist.txt");
        ArrayList<String> words = logic.getWords();
        int numWords = logic._numWords;
        window.setScene(scene2);

    }

}
