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

    public static double distanceHaversine(double lat1, double lon1, double lat2, double lon2) {
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

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

    public static void parsePostalCodes() {
        try {
            String csvPath = Driver.class.getResource(csvFilePath).getFile();

            CSVReader reader = new CSVReaderBuilder(new FileReader(csvPath)).build();
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                String id = nextLine[0];
                String country = nextLine[1];
                String postalCode = nextLine[2];
                String province = nextLine[3] + ", " + nextLine[4];
                double latitude = Double.parseDouble(nextLine[5]);
                double longitude = Double.parseDouble(nextLine[6]);

                PostalCode codeInstance = new PostalCode(id, country, postalCode, province, latitude, longitude);

                postalCodesMap.put(postalCode, codeInstance);
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
