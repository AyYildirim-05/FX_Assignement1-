package edu.vanier.distanceCalculator.controllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DriverFxmlController {
    @FXML
    private Button distance;
    @FXML
    private Button locations;

    public void calcDistance(Event event) {
        distance.setOnAction(event1 -> {

        });
    }


    public void calcLocation(Event event) {
        locations.setOnAction(event1 -> {

        });
    }
}
