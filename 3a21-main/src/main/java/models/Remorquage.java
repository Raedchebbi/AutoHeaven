package models;

import java.util.Date;

public class Remorquage extends Reservation {
    private int id_cr;
    private String pickup_location;
    private String dropoff_location;

    public Remorquage(int id_res, int id, Date date_res, String type, int id_cr, String pickup_location, String dropoff_location) {
        super(id_res, id, date_res, "remorquage");
        this.id_cr = id_cr;
        this.pickup_location = pickup_location;
        this.dropoff_location = dropoff_location;
    }

    public Remorquage(int id, Date date_res, String type, int id_cr, String pickup_location, String dropoff_location) {
        super(id, date_res, "remorquage");
        this.id_cr = id_cr;
        this.pickup_location = pickup_location;
        this.dropoff_location = dropoff_location;
    }

    public int getId_cr() {
        return id_cr;
    }
    public void setId_cr(int id_cr) {
        this.id_cr = id_cr;
    }

    public String getPickup_location() {
        return pickup_location;
    }
    public void setPickup_location(String pickup_location) {
        this.pickup_location = pickup_location;
    }

    public String getDropoff_location() {
        return dropoff_location;
    }
    public void setDropoff_location(String dropoff_location) {
        this.dropoff_location = dropoff_location;
    }

    @Override
    public String toString() {
        return "Remorquage{" +
                "ID Camion Remorquage = " + id_cr +
                "Lieu de Prise = " + pickup_location + '\'' +
                ", Dropoff_Location = " + dropoff_location + '\'' +
                "} " + super.toString();
    }
}
