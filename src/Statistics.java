import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;

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

        /*
         * Sort the statistics by most times mastered
         */
        FXCollections.sort(statistics, new Comparator<WordStatistic>() {
            @Override
            public int compare(WordStatistic s1, WordStatistic s2) {
                if(s1.getMastered() < s2.getMastered()){
                    return 1;
                } else if (s1.getMastered()== s2.getMastered()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });

        return statistics;
    }

    public Scene constructScene() {
        TableColumn<WordStatistic, String> wordColumn = new TableColumn<>("Word");
        wordColumn.setMinWidth(200);
        wordColumn.setCellValueFactory(new PropertyValueFactory<WordStatistic, String>("word"));

        TableColumn<WordStatistic, Integer> faultedColumn = new TableColumn<>("Faults");
        faultedColumn.setMinWidth(100);
        faultedColumn.setCellValueFactory(new PropertyValueFactory<WordStatistic, Integer>("faulted"));

        TableColumn<WordStatistic, Integer> failedColumn = new TableColumn<>("Failures");
        failedColumn.setMinWidth(100);
        failedColumn.setCellValueFactory(new PropertyValueFactory<WordStatistic, Integer>("failed"));

        TableColumn<WordStatistic, Integer> masteredColumn = new TableColumn<>("Mastered");
        masteredColumn.setMinWidth(100);
        masteredColumn.setCellValueFactory(new PropertyValueFactory<WordStatistic, Integer>("mastered"));

        TableView<WordStatistic> table = new TableView<>();
        table.setItems(getWordStatistics());
        boolean b = table.getColumns().addAll(wordColumn, faultedColumn, failedColumn, masteredColumn);

        Button menuButton = new Button("Menu");
        menuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Main.setMenu();
            }
        });
        VBox root = new VBox();

        root.getChildren().addAll(table, menuButton);
        root.setAlignment(Pos.CENTER);

        ScrollPane scrollpane = new ScrollPane();
        scrollpane.setFitToWidth(true);
        scrollpane.setFitToHeight(true);
        scrollpane.setPrefSize(Main.applicationWidth, Main.applicationHeight);
        scrollpane.setContent(root);
        return new Scene(scrollpane);
    }


}