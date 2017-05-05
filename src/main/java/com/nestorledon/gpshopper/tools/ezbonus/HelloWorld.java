package com.nestorledon.gpshopper.tools.ezbonus;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;


public class HelloWorld extends Application {

    final static String MAIN_VIEW = "/home_view.fxml";


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        //initialize(primaryStage);
        final URL resourceLocation = getClass().getResource(MAIN_VIEW);
        final Parent root = FXMLLoader.load(resourceLocation);
        primaryStage.setTitle("EZBonus");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    public void initialize(final Stage primaryStage) {

        primaryStage.setTitle("Hello World!");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

        final JFXTextField field = new JFXTextField("????");

        final StackPane root = new StackPane();
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
        root.getChildren().add(btn);
        root.getChildren().add(field);

    }
}