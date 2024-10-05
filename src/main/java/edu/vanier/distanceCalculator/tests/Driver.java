package edu.vanier.distanceCalculator.tests;

import edu.vanier.distanceCalculator.controllers.PostalCodeControllers;
import edu.vanier.distanceCalculator.models.PostalCode;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.function.Consumer;
import static edu.vanier.distanceCalculator.controllers.PostalCodeControllers.postalCodesMap;

/**
 * Class that creates the GUI application
 * and allows user interaction while also defining the nature of those interactions
 *
 * @author ahmetyusufyildirim
 */
public class Driver extends Application {

    public static ObservableList<PostalCode> data = FXCollections.observableArrayList();
    public static void main(String[] args) {
        launch(args);
        System.out.println("jl");
    }

    /**
     * Method that for a given string, checks whether it is a double.
     * If successful, it returns true; otherwise, it returns false.
     * @param str entered string.
     * @return boolean value that corresponds with its nature.
     */
    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Method that parses through the csv file before creating the GUI application.
     */
    @Override
    public void init() {
        PostalCodeControllers.csvParsePostalCodes();
    }

    /**
     * Method that allows the GUI application to be created
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     */
    @Override
    public void start(Stage primaryStage) {
        createMainPage(primaryStage);
    }

    /**
     * Method that creates the main menu page of the GUI application.
     * @param primaryStage the stage created with the help of the start method.
     */
    public void createMainPage(Stage primaryStage) {
        VBox vBox = new VBox();
        vBox.setAlignment(javafx.geometry.Pos.CENTER);

        HBox topBox = new HBox(50);
        topBox.setAlignment(javafx.geometry.Pos.CENTER);
        Button distanceButton = new Button("Compute Distance");
        Button locationButton = new Button("Search Locations");
        topBox.getChildren().addAll(distanceButton, locationButton);

        HBox distanceBox = new HBox(10);
        distanceBox.setAlignment(javafx.geometry.Pos.CENTER);
        Label distanceLabel = new Label("Distance Calculated");
        TextField distanceField = new TextField();
        distanceBox.getChildren().addAll(distanceLabel, distanceField);

        TableView<PostalCode> tableView = new TableView<>();
        tableView.setEditable(true);

        TableColumn<PostalCode, String> idColumn = new TableColumn<>("ID");
        idColumn.setPrefWidth(100);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<PostalCode, String> countryColumn = new TableColumn<>("Country");
        countryColumn.setPrefWidth(100);
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));

        TableColumn<PostalCode, String> postalCodeColumn = new TableColumn<>("Postal Code");
        postalCodeColumn.setPrefWidth(100);
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        TableColumn<PostalCode, String> provinceColumn = new TableColumn<>("Province");
        provinceColumn.setPrefWidth(100);
        provinceColumn.setCellValueFactory(new PropertyValueFactory<>("province"));

        TableColumn<PostalCode, String> latitudeColumn = new TableColumn<>("Latitude");
        latitudeColumn.setPrefWidth(100);
        latitudeColumn.setCellValueFactory(new PropertyValueFactory<>("latitude"));

        TableColumn<PostalCode, String> longitudeColumn = new TableColumn<>("Longitude");
        longitudeColumn.setPrefWidth(100);
        longitudeColumn.setCellValueFactory(new PropertyValueFactory<>("longitude"));

        tableView.getColumns().addAll(idColumn, countryColumn, postalCodeColumn, provinceColumn, latitudeColumn, longitudeColumn);

        tableView.getItems().clear();
        tableView.setItems(data);

        vBox.getChildren().addAll(topBox, distanceBox);
        distanceButton.setOnAction(e -> createDistance(result -> distanceField.setText(Double.toString(result))));
        locationButton.setOnAction(e -> createLocation());

        BorderPane root = new BorderPane();
        root.setTop(vBox);
        root.setCenter(tableView);

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Distance Calculator");
        primaryStage.show();
    }

    /**
     * Method that creates the scene containing the location calculating scene
     * while also simultaneously implementing GUI logic.
     */
    public void createLocation() {
        Stage secondaryStage2 =  new Stage();
        secondaryStage2.initModality(Modality.APPLICATION_MODAL);
        BorderPane root = new BorderPane();

        VBox centerBox = new VBox(10);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPrefSize(100, 200);

        HBox fromBox = new HBox();
        fromBox.setPrefSize(200, 100);
        Label fromLabel = new Label("From:");
        TextField fromField = new TextField();
        fromField.setPromptText("Enter Point");
        fromBox.getChildren().addAll(fromLabel, fromField);

        HBox radiusBox = new HBox();
        radiusBox.setPrefSize(200, 100);
        Label radiusLabel = new Label("Radius:");
        TextField radiusField = new TextField();
        radiusField.setPromptText("Enter radius (km)");
        radiusBox.getChildren().addAll(radiusLabel, radiusField);

        centerBox.getChildren().addAll(fromBox, radiusBox);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20));
        Button cancelButton = new Button("Cancel");
        Button computeButton = new Button("Compute");
        buttonBox.getChildren().addAll(cancelButton, computeButton);

        cancelButton.setOnAction(event -> secondaryStage2.close());
        computeButton.setOnAction(event -> {
            String pointA = fromField.getText().toUpperCase();
            String radius = radiusField.getText();
            if (pointA.isEmpty() || radius.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("Please enter all the values");
                alert.setContentText("Please enter all the boxes.");
                alert.showAndWait();
            } else if (!postalCodesMap.containsKey(pointA)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("Not existing value.");
                alert.setContentText("The entered postal code does not exist! Please enter again.");
                alert.showAndWait();
            } else if (!isDouble(String.valueOf(Double.parseDouble(radius)))) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("Parsing error");
                alert.setContentText("The entered radius is not a number!");
                alert.showAndWait();
            }
            double area = Double.parseDouble(radius);
            PostalCodeControllers.nearbyLocations(data, pointA, area);
            secondaryStage2.close();
        });

        centerBox.getChildren().add(buttonBox);

        root.setCenter(centerBox);

        Scene scene = new Scene(root, 400, 300);
        secondaryStage2.setScene(scene);
        secondaryStage2.setTitle("Distance Radius Calculator");
        secondaryStage2.show();
    }

    /**
     * Method that creates the scene of the distance calculator while also simultaneously implementing GUI logic.
     * @param callback returns the result of the calculation to the main menu.
     */
    public void createDistance(Consumer<Double> callback) {
        Stage secondaryStage1 = new Stage();
        secondaryStage1.initModality(Modality.APPLICATION_MODAL);
        BorderPane root = new BorderPane();

        VBox centerBox = new VBox(10);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPrefSize(100, 200);


        HBox pointABox = new HBox();
        pointABox.setPrefSize(200, 100);
        Label pointALabel = new Label("Point A:");
        TextField pointAField = new TextField();
        pointAField.setPromptText("Enter Point A");
        pointABox.getChildren().addAll(pointALabel, pointAField);

        HBox pointBBox = new HBox();
        pointBBox.setPrefSize(200, 100);
        Label pointBLabel = new Label("Point B:");
        TextField pointBField = new TextField();
        pointBField.setPromptText("Enter Point B");
        pointBBox.getChildren().addAll(pointBLabel, pointBField);
        centerBox.getChildren().addAll(pointABox, pointBBox);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20));
        Button cancelButton = new Button("Cancel");
        Button computeButton = new Button("Compute");
        buttonBox.getChildren().addAll(cancelButton, computeButton);

        cancelButton.setOnAction(event -> secondaryStage1.close());
        computeButton.setOnAction(event -> {
            String pointA = pointAField.getText();
            String pointB = pointBField.getText();
            double num;

            if (pointA.isEmpty() || pointB.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("Please enter all the values");
                alert.setContentText("Please enter all the boxes.");
                alert.showAndWait();
            } else if (PostalCodeControllers.haversineCalculator(pointA, pointB) == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("Not existing value.");
                alert.setContentText("The entered postal code does not exist! Please enter again.");
                alert.showAndWait();
            } else {
                num = PostalCodeControllers.haversineCalculator(pointA, pointB);
                callback.accept(num);
                secondaryStage1.close();
            }
        });

        centerBox.getChildren().add(buttonBox);

        root.setCenter(centerBox);

        Scene scene = new Scene(root, 400, 300);
        secondaryStage1.setScene(scene);
        secondaryStage1.setTitle("Point Distance Calculator");
        secondaryStage1.show();
    }

}
