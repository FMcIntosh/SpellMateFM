import javafx.event.ActionEvent;

import javafx.event.EventHandler;

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

public class InvalidInput {



    public void display() {

        //Clear history, maybe could be in a better place in terms of responsibilities

        final Stage window = new Stage();



        //Block user interaction with other windows until this window is

        // dealt with

        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("Invalid Input");

        window.setMinWidth(300);



        //Components

        Label label = new Label();

        label.setText("Please enter valid input. Alphabetical characters only");

        Button closeButton = new Button("OK");

        closeButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override

            public void handle(ActionEvent event) {

                window.close();

            }

        });





        //Layout

        VBox layout = new VBox(10);

        layout.getChildren().addAll(label, closeButton);

        layout.setAlignment(Pos.CENTER);



        //Scene

        Scene scene = new Scene(layout, 400, 100);



        //Window

        window.setScene(scene);

        //Needs to be closed before returning

        window.showAndWait();



    }

}