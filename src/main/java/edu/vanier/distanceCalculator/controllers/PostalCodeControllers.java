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
import java.util.Iterator;
import java.util.Map;

public class PostalCodeControllers {
    public static final double radius = 6371;
    final static String csvFilePath = "/data/postalcodes.csv";
    public static ArrayList<PostalCode> postalCodesArray = new ArrayList<>();
    public static HashMap<String, PostalCode> postalCodesMap = new HashMap<>();

    public static double distanceHaversine(double latitude1, double longitude1, double latitude2, double longitude2) {
        double lat1 = Math.toRadians(latitude1);
        double lat2 = Math.toRadians(latitude2);
        double lon1 = Math.toRadians(longitude1);
        double lon2 = Math.toRadians(longitude2);

        double latDistance = lat2 - lat1;
        double lonDistance = lon2 - lon1;

        // Inside part of haversine formula
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        // Outside part of the haversine formula
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return radius * c;
    }

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
    public static void nearbyLocations(ObservableList<PostalCode> storage, String postalCodeString, double radius) {
        String postal = postalCodeString.toUpperCase();
        radius = radius * 1000;

        if (!postalCodesMap.containsKey(postal)) {
            return;
        }
        PostalCode basePostalCode = postalCodesMap.get(postal);

        for (Map.Entry<String, PostalCode> entry : postalCodesMap.entrySet()) {
            PostalCode currentPostalCode = entry.getValue();
            double distance = distanceHaversine(
                    basePostalCode.getLatitude(),
                    basePostalCode.getLongitude(),
                    currentPostalCode.getLatitude(),
                    currentPostalCode.getLongitude()
            );

            if (distance <= radius) {
                storage.add(currentPostalCode);
            }
        }
    }

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

    public static int doesExist(String code) {
        return 1;
    }
    //Todo: should it return integer or postalCode object
}
