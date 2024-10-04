package edu.vanier.distanceCalculator.controllers;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import edu.vanier.distanceCalculator.models.PostalCode;
import edu.vanier.distanceCalculator.tests.Driver;
import javafx.collections.ObservableList;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Java class that contains the logical implementation of this assignment
 *
 * @author ahmetyusufyildirim
 */
public class PostalCodeControllers {
    public static final double radius = 6371.009;
    final static String csvFilePath = "/data/postalcodes.csv";
    public static HashMap<String, PostalCode> postalCodesMap = new HashMap<>();

    /**
     * Method that gets two postal codes and calculates the distance between both of them by
     * using both of their latitudes and longitudes.
     * @param latitude1  latitude of the fist postal code.
     * @param longitude1 longitude of the fist postal code.
     * @param latitude2  latitude of the fist second code.
     * @param longitude2 longitude of the fist second code.
     * @return returns the distance in kilometers.
     */
    public static double distanceHaversine(double latitude1, double longitude1, double latitude2, double longitude2) {
        double latDistance = (latitude2 - latitude1) * Math.PI/180;
        double lonDistance = (longitude2 - longitude1) * Math.PI/180;
        double lat1 = latitude1 * Math.PI / 180;
        double lat2 = latitude2 * Math.PI / 180;

        double inside = Math.sin(Math.pow(latDistance / 2, 2)) + Math.sin(Math.pow(lonDistance / 2, 2)) * Math.cos(lat1) * Math.cos(lat2);
        double outside = 2*Math.asin(Math.sqrt(inside)) * 6371.009;

        return Math.floor(outside * 100 + .5 ) / 100;
    }

    /**
     * Method that implements the haversine formula while allowing the ability to access their latitudes and longitudes
     * while simultaneously checking if they exist.
     * @param postalCode1 the first postal code string entered.
     * @param postalCode2 the first postal code string entered.
     * @return the distance between both of them in kilometers.
     */
    public static double haversineCalculator(String postalCode1,String postalCode2){
        String postal1 = postalCode1.toUpperCase();
        String postal2 = postalCode2.toUpperCase();

        if (!postalCodesMap.containsKey(postal1) && !postalCodesMap.containsKey(postal2)) {
            return 0;
        }

        ArrayList<Double> list1 = parseLatAndLong(postal1);
        ArrayList<Double> list2 = parseLatAndLong(postal2);

        return distanceHaversine(list1.get(0), list1.get(1), list2.get(0), list2.get(1));
    }

    /**
     * Method that calculates how far the postal code objects and stores them in an ObservableList by comparing them
     * to the radius value.
     * @param storage the data structure that will contain the postal code objects.
     * @param postalCodeString entered postal code.
     * @param radius (in km) the entered value that will define which objects the program will return.
     */
    public static void nearbyLocations(ObservableList<PostalCode> storage, String postalCodeString, double radius) {

        String postal = postalCodeString.toUpperCase();

        if (!postalCodesMap.containsKey(postal)) {
            return;
        }
        PostalCode basePostalCode = postalCodesMap.get(postal);

        for (Map.Entry<String, PostalCode> entry : postalCodesMap.entrySet()) {
            if (!basePostalCode.equals(entry)) {
                PostalCode currentPostalCode = entry.getValue();
                double distance = distanceHaversine(basePostalCode.getLatitude(), basePostalCode.getLongitude(), currentPostalCode.getLatitude(), currentPostalCode.getLongitude());
                if (distance <= radius && !storage.contains(currentPostalCode)) {
                    storage.add(currentPostalCode);
                }
            }
        }
    }

    /**
     * Method that gets the csv file containing all the information for the postal codes.
     */
    public static void csvParsePostalCodes() {
        try {
            String csvPath = Driver.class.getResource(csvFilePath).getFile();

            CSVReader reader = new CSVReaderBuilder(new FileReader(csvPath)).build();
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                String id = nextLine[0];
                String country = nextLine[1];
                String postalCode = nextLine[2];
                String province = nextLine[nextLine.length - 3];
                double latitude = Double.parseDouble(nextLine[nextLine.length - 2]);
                double longitude = Double.parseDouble(nextLine[nextLine.length - 1]);

                PostalCode codeInstance = new PostalCode(id, country, postalCode, province, latitude, longitude);
                postalCodesMap.put(postalCode, codeInstance);
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that gets a string postal code and gets information from it.
     * @param postalCode entered object.
     * @return the latitude and longitude of the entered postal code.
     */
    public static ArrayList<Double> parseLatAndLong(String postalCode) {
        ArrayList<Double> list = new ArrayList<>();
        for (Map.Entry<String, PostalCode> entry : postalCodesMap.entrySet()) {
            String key = entry.getKey();

            if (postalCode.equalsIgnoreCase(key)) {
                list.add(entry.getValue().getLatitude());
                list.add(entry.getValue().getLongitude());
            }
        }
        return list;
    }
}
