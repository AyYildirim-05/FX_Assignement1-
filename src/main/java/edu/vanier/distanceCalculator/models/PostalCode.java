package edu.vanier.distanceCalculator.models;

/**
 * Java class that defines the postal code objects
 *
 * @author ahmetyusufyildirim
 */
public class PostalCode {
    String id;
    String country;
    String postalCode;
    String province;
    double latitude;
    double longitude;

    /**
     * Method that initializes a PostalCode object's fields with the provided parameters.
     * @param id the id of the PostalCode
     * @param country the country where the PostalCode belongs to
     * @param postalCode the postal code value of the PostalCode
     * @param province the province where the PostalCode belongs to
     * @param latitude the latitude of the PostalCode
     * @param longitude the longitude of the PostalCode
     */
    public PostalCode(String id, String country, String postalCode, String province, double latitude, double longitude) {
        this.id = id;
        this.country = country;
        this.postalCode = postalCode;
        this.province = province;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Method that gets the id of a PostalCode object
     * @return String id of the object
     */
    public String getId() {
        return id;
    }

    /**
     * Method that gets the country of a PostalCode object
     * @return String country of the object
     */
    public String getCountry() {
        return country;
    }

    /**
     * Method that gets the postal code of a PostalCode object
     * @return String postal code of the object
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Method that gets the province of a PostalCode object
     * @return String province of the object
     */
    public String getProvince() {
        return province;
    }

    /**
     * Method that gets the latitude of a PostalCode object
     * @return Double latitude of the object
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Method that gets the longitude of a PostalCode object
     * @return Double longitude of the object
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Returns a string representation of the object which includes class name
     * and the values of the object's fields.
     * @return string representing a PostalCode object
     */
    @Override
    public String toString() {
        return "PostalCode{" +
                "id='" + id + '\'' +
                ", country='" + country + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", province='" + province + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
