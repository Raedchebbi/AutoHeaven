package models;

import java.util.Date;

public class Reservation {
    private int id_r;
    private Date date_res;
    private String status;
    private int id_v;
    private int id;

    public Reservation() {
    }

    public Reservation(int id_r, Date date_res, String status, int id_v, int id) {
        this.id_r = id_r;
        this.date_res = date_res;
        this.status = status;
        this.id_v = id_v;
        this.id = id;
    }

    public int getId_r() {
        return id_r;
    }

    public void setId_r(int id_r) {
        this.id_r = id_r;
    }

    public int getId_v() {
        return id_v;
    }

    public void setId_v(int id_v) {
        this.id_v = id_v;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id_r=" + id_r +
                ", date_res=" + date_res +
                ", status='" + status + '\'' +
                ", id_v=" + id_v +
                ", id=" + id +
                '}';
    }
}