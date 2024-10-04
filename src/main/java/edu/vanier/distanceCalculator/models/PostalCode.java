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
     * Method that gets the latitude of a postal code object
     * @return nothing
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Method that gets the longitude of a postal code object
     * @return nothing
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Returns a string representation of the object which includes class name
     * and the values of the object's fields.
     * @return string representing a postal code object
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
