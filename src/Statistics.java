import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Fraser McIntosh on 20/08/2016.
 */
public class Statistics {

    public ObservableList<WordStatistic> getWordStatistics() {
        ObservableList<WordStatistic> statistics = FXCollections.observableArrayList();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FileLogic.attemptedlist));
            String currentLine;
            while((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                statistics.add(new WordStatistic(trimmedLine));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return statistics;
    }

    public Scene constructScene() {

        TableColumn<WordStatistic, String> wordColumn = new TableColumn<>("Word");
        wordColumn.setMinWidth(200);
        wordColumn.setCellValueFactory(new PropertyValueFactory<>("word"));

        TableColumn<WordStatistic, Integer> faultedColumn = new TableColumn<>("Faults");
        faultedColumn.setMinWidth(100);
        faultedColumn.setCellValueFactory(new PropertyValueFactory<>("faulted"));

        TableColumn<WordStatistic, Integer> failedColumn = new TableColumn<>("Failures");
        failedColumn.setMinWidth(100);
        failedColumn.setCellValueFactory(new PropertyValueFactory<>("failed"));

        TableColumn<WordStatistic, Integer> masteredColumn = new TableColumn<>("Mastered");
        masteredColumn.setMinWidth(100);
        masteredColumn.setCellValueFactory(new PropertyValueFactory<>("mastered"));

        TableView<WordStatistic> table = new TableView<>();
        table.setItems(getWordStatistics());
        table.getColumns().addAll(wordColumn, faultedColumn, failedColumn, masteredColumn);

        Button menuButton = new Button("Menu");
        menuButton.setOnAction(event -> Main.setMenu());
        VBox root = new VBox();

        root.getChildren().addAll(table, menuButton);

        ScrollPane scrollpane = new ScrollPane();
        scrollpane.setFitToWidth(true);
        scrollpane.setFitToHeight(true);
        scrollpane.setPrefSize(Main.applicationWidth, Main.applicationHeight);
        scrollpane.setContent(root);
        return new Scene(scrollpane);
    }


}
