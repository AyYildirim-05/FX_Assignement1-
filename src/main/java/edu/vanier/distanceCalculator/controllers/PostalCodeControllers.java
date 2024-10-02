package edu.vanier.distanceCalculator.controllers;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import edu.vanier.distanceCalculator.models.PostalCode;
import edu.vanier.distanceCalculator.tests.Driver;

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

    public static void nearbyLocations(PostalCode postalCode, double radius) {
        String postal = postalCode.getPostalCode().toUpperCase();
        Iterator<Map.Entry<String, PostalCode>> iterator = postalCodesMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, PostalCode> entry = iterator.next();
            double distance = distanceHaversine(postalCode.getLatitude(), postalCode.getLongitude(), entry.getValue().getLatitude(), entry.getValue().getLongitude());
            if (distance <= radius) {
                postalCodesArray.add(entry.getValue());
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
