package edu.vanier.distanceCalculator.tests;

import edu.vanier.distanceCalculator.controllers.DriverFxmlController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Driver extends Application {
    public static void main(String[] args) {
        launch(args);

    }
    @Override
    public void init() {
        System.out.println("Initialization in progress...");
    }

    public void stop() {
        System.out.println("Shutting down application...");
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("resources/fxml/MainPage.fxml"));
        fxmlLoader.setController(new DriverFxmlController());
    }
}
