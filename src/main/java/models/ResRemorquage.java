package models;

import java.sql.Date;

public class ResRemorquage {
    private int id_rem;
    private int id_u;
    private int id_cr;
    private String point_ramassage;
    private String point_depot;
    private Date date;  // New date field

    public ResRemorquage() {}

    public ResRemorquage(int id_rem, int id_u, int id_cr, String point_ramassage, String point_depot, Date date) {
        this.id_rem = id_rem;
        this.id_u = id_u;
        this.id_cr = id_cr;
        this.point_ramassage = point_ramassage;
        this.point_depot = point_depot;
        this.date = date;  // Set the new date field
    }

    public int getId_rem() { return id_rem; }
    public void setId_rem(int id_rem) { this.id_rem = id_rem; }

    public int getId_u() { return id_u; }
    public void setId_u(int id_u) { this.id_u = id_u; }

    public int getId_cr() { return id_cr; }
    public void setId_cr(int id_cr) { this.id_cr = id_cr; }

    public String getPoint_ramassage() { return point_ramassage; }
    public void setPoint_ramassage(String point_ramassage) { this.point_ramassage = point_ramassage; }

    public String getPoint_depot() { return point_depot; }
    public void setPoint_depot(String point_depot) { this.point_depot = point_depot; }

    public Date getDate() { return date; }  // Getter for the date
    public void setDate(Date date) { this.date = date; }  // Setter for the date

    @Override
    public String toString() {
        return "ResRemorquage{ID Remorquage =" + id_rem + ", ID User =" + id_u + ", ID Camion Remorquage =" + id_cr +
                ", Point Ramassage = '" + point_ramassage + "', Point Depot ='" + point_depot + "', Date = '" + date + "'}";
    }
}