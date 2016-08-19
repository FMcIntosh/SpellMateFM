import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

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
        wordColumn.setCellValueFactory(new PropertyValueFactory<>("_word"));

        TableColumn<WordStatistic, String> faultedColumn = new TableColumn<>("Faults");
        faultedColumn.setMinWidth(100);
        wordColumn.setCellValueFactory(new PropertyValueFactory<>("_faulted"));

        TableColumn<WordStatistic, String> failedColumn = new TableColumn<>("Failures");
        failedColumn.setMinWidth(100);
        wordColumn.setCellValueFactory(new PropertyValueFactory<>("_failed"));

        TableColumn<WordStatistic, String> masteredColumn = new TableColumn<>("Mastered");
        masteredColumn.setMinWidth(100);
        wordColumn.setCellValueFactory(new PropertyValueFactory<>("_mastered"));

        TableView<WordStatistic> table = new TableView<>();
        table.setItems(getWordStatistics());
        table.getColumns().addAll(wordColumn, faultedColumn, failedColumn, masteredColumn);

        VBox layout = new VBox();
        layout.getChildren().addAll(table);

        return new Scene(layout);
    }


}
