package models;

import java.util.Date;

public class Reservation {
    private int id_r;
    private Date date_res;
    private String status;
    private Voiture voiture;
    private User user;

    public Reservation() {}

    public Reservation(Date date_res, String status, Voiture voiture, User user) {
        this.date_res = date_res;
        this.status = status;
        this.voiture = voiture;
        this.user = user;
    }

    public Reservation(int id_r, Date date_res, String status, Voiture voiture, User user) {
        this.id_r = id_r;
        this.date_res = date_res;
        this.status = status;
        this.voiture = voiture;
        this.user = user;
    }

    public int getId_r() {
        return id_r;
    }
    public void setId_r(int id_r) {
        this.id_r = id_r;
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

    public Voiture getVoiture() {
        return voiture;
    }
    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id_r=" + id_r +
                ", date_res=" + date_res +
                ", status='" + status + '\'' +
                ", voiture_id=" + (voiture != null ? voiture.getId_v() : "null") +
                ", user_id=" + (user != null ? user.getId() : "null") +
                '}';
    }
}