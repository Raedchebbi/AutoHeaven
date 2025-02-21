package models;

import java.util.Date;

public abstract class Reservation {
    private int id_res;
    private int id;
    private Date date_res;
    private String type;


    public Reservation(int id_res, int id,  Date date_res, String type) {
        this.id_res = id_res;
        this.id = id;
        this.date_res = date_res;
        this.type = type;
    }

    public Reservation(int id, Date date_res, String type) {
        this.id = id;
        this.date_res = date_res;
        this.type = type;
    }

    public Reservation() {}

    public int getId_res() {
        return id_res;
    }
    public void setId_res(int id_res) {
        this.id_res = id_res;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Date getDate_res() {
        return date_res;
    }
    public void setDate_res(Date date_res) {
        this.date_res = date_res;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id_reservation=" + id_res +
                ", id=" + id +
                ", date_reservation=" + date_res +
                ", type_reservation='" + type + '\'' +
                '}';
    }
}
