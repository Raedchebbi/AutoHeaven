package models;

public class ResRemorquage {
    private int id_res_remo;
    private int id_res;
    private int id_cr;
    private String pickup_location;
    private String dropoff_location;

    public ResRemorquage() {}

    public ResRemorquage(int id_res_remo, int id_res, int id_cr, String pickup_location, String dropoff_location) {
        this.id_res_remo = id_res_remo;
        this.id_res = id_res;
        this.id_cr = id_cr;
        this.pickup_location = pickup_location;
        this.dropoff_location = dropoff_location;
    }

    public int getId_res_remo() { return id_res_remo; }
    public void setId_res_remo(int id_res_remo) { this.id_res_remo = id_res_remo; }

    public int getId_res() { return id_res; }
    public void setId_res(int id_res) { this.id_res = id_res; }

    public int getId_cr() { return id_cr; }
    public void setId_cr(int id_cr) { this.id_cr = id_cr; }

    public String getPickup_location() { return pickup_location; }
    public void setPickup_location(String pickup_location) { this.pickup_location = pickup_location; }

    public String getDropoff_location() { return dropoff_location; }
    public void setDropoff_location(String dropoff_location) { this.dropoff_location = dropoff_location; }

    @Override
    public String toString() {
        return "ResRemorquage{id_res_remo=" + id_res_remo + ", id_res=" + id_res + ", id_cr=" + id_cr +
                ", pickup_location='" + pickup_location + "', dropoff_location='" + dropoff_location + "'}";
    }
}