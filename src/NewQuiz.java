import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Fraser McIntosh on 18/08/2016.
 */
public class NewQuiz {

    public static void display() {
        Stage window = new Stage();

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
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, startButton);
        layout.setAlignment(Pos.CENTER);

        //Scene
        Scene scene = new Scene(layout, 300, 300);

        //Window
        window.setScene(scene);
        //Needs to be closed before returning
        window.showAndWait();


    }
    private static void startQuiz() {
        System.out.println("Quiz started");
    }

}
