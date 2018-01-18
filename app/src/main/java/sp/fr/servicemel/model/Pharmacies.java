package sp.fr.servicemel.model;

/**
 * Created by Formation on 18/01/2018.
 */

public class Pharmacies {

    private String name;

    private String typeBat;
    private String horaire;

    private double latitude;
    private double longitude;

    public String getName() {
        return name;
    }

    public Pharmacies setName(String name) {
        this.name = name;
        return this;
    }

    public String getTypeBat() {
        return typeBat;
    }

    public Pharmacies setTypeBat(String typeBat) {
        this.typeBat = typeBat;
        return this;
    }

    public String getHoraire() {
        return horaire;
    }

    public Pharmacies setHoraire(String horaire) {
        this.horaire = horaire;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public Pharmacies setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public Pharmacies setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }
}
