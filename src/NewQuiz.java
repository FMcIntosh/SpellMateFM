import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Fraser McIntosh on 18/08/2016.
 */
public class NewQuiz {
    private static Stage window;
    private static Scene scene1, scene2;
    private static int width, height;
    public static void display() {
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
        startButton.setOnAction(e -> NewQuiz.startQuiz());

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
    private static void startQuiz() {
        System.out.println("Quiz started");
        window.setScene(scene2);
    }

}
