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
public class ClearHistory {

    public void display() {
        //Clear history, maybe could be in a better place in terms of responsibilities
        FileLogic.clearFiles();

        Stage window = new Stage();

        //Block user interaction with other windows until this window is
        // dealt with
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("History Cleared");
        window.setMinWidth(250);

        //Components
        Label label = new Label();
        label.setText("History has been cleared");
        Button closeButton = new Button("OK");
        closeButton.setOnAction(e -> window.close());

        //Layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        //Scene
        Scene scene = new Scene(layout);

        //Window
        window.setScene(scene);
        //Needs to be closed before returning
        window.showAndWait();


    }
}
