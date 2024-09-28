package edu.vanier.distanceCalculator.tests;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import edu.vanier.distanceCalculator.controllers.PostalCodeControllers;

import java.io.FileReader;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) {
//        PostalCodeControllers postalCodeControllers = new PostalCodeControllers();
//        postalCodeControllers.parsePostalCodes();
        try {
            String csvPath = Driver.class.getResource("/data/postalcodes.csv").getFile();

            CSVReader reader = new CSVReaderBuilder(new FileReader(csvPath)).build();
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                System.out.println(nextLine[0]);
                System.out.println(nextLine[1]);
                System.out.println(nextLine[2]);
                System.out.println(nextLine[3] + ", " + nextLine[4]);
                System.out.println(nextLine[4]);
                System.out.println(nextLine[5]);
                System.out.println(nextLine[6]);
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}
