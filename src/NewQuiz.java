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
    private Scene startScene, spellWordScene, correctScene, incorrectFirstScene, incorrectSecondScene, noWordsScene;
    private int _sceneWidth, _sceneHeight;
    private QuizLogic logic = new QuizLogic("wordlist.txt");

    public void setUp() {
        _sceneWidth = 300;
        _sceneHeight = 300;

        //Block user interaction with other windows until this window is
        // dealt with
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Spelling Quiz");
        window.setMinWidth(250);
    }

    public void display() {
        window = new Stage();
        setUp();
        buildScenes();

        //Window
        window.setScene(startScene);
        //Needs to be closed before returning
        window.showAndWait();
    }

    private void buildScenes() {
        // ----------------------------------------------- Start quiz scene
        //Components
        Label label = new Label("Spelling quiz");
        Button startButton = new Button("Start Quiz");
        startButton.setOnAction(e -> startQuiz());

        //Layout
        VBox layout1 = new VBox(10);
        layout1.getChildren().addAll(label, startButton);
        layout1.setAlignment(Pos.CENTER);

        //Scene
        startScene = new Scene(layout1, _sceneWidth, _sceneHeight);

        // ----------------------------------------------- New quiz scene
        //Components 2
        TextField input = new TextField("Spell word here");
        Button checkButton = new Button ("Check");
        checkButton.setOnAction(event -> checkAnswer(input.getText()));

        //Layout
        VBox layout2 = new VBox(2);
        layout2.getChildren().addAll(input, checkButton);
        layout2.setAlignment(Pos.CENTER);

        spellWordScene = new Scene(layout2, _sceneWidth, _sceneHeight);

        // ----------------------------------------------- Correct scene
        //Components
        Label label3 = new Label("Correct");
        Button nextButton = new Button("Next Question");
        nextButton.setOnAction(e -> newQuestion());
        //Layout
        VBox layout3 = new VBox(10);
        layout3.getChildren().addAll(label3, nextButton);
        layout3.setAlignment(Pos.CENTER);

        correctScene = new Scene(layout3, _sceneWidth, _sceneHeight);

        // ----------------------------------------------- Incorrect First scene
        //Components
        Label label4 = new Label("Incorrect");
        Button againButton = new Button("Try Again");
        againButton.setOnAction(e -> failed());
        //Layout
        VBox layout4 = new VBox(10);
        layout4.getChildren().addAll(label4, againButton);
        layout4.setAlignment(Pos.CENTER);

        incorrectFirstScene = new Scene(layout4, _sceneWidth, _sceneHeight);

        // ----------------------------------------------- noWords scene
    }


    private void startQuiz() {
        System.out.println("Quiz started");
        if(logic._hasWords) {
            newQuestion();
        } else {
            displayNoWords();
        }
    }

    private void newQuestion() {
        logic.nextWord();
        window.setScene(spellWordScene);
        System.out.println("New Question");
    }

    private void failed() {
        if(logic.isSecondAttempt) {
            System.out.println("ASD");
            window.setScene(incorrectFirstScene);
        } else {
            System.out.println("ESDFF");
            logic.isSecondAttempt = true;
            window.setScene(spellWordScene);
        }
        System.out.println("New Question");
    }


    private void checkAnswer(String attempt){
        System.out.println("checked");
        boolean isCorrect = logic.checkAnswer(attempt);
        System.out.println(isCorrect);
        if(isCorrect) {
            displayCorrectScene();
        } else {
            displayIncorrectScene();
        }
    }

    private void displayIncorrectScene(){
        if(logic.isSecondAttempt) {
            System.out.println("second attempt screen");
        } else {
            window.setScene(incorrectFirstScene);
        }
    }

    private void displayCorrectScene(){

        //Scene
        window.setScene(correctScene);
    }

    private void displayNoWords() {
        window.setScene(noWordsScene);
    }


}
