package models;

import java.util.Date;

public class Review {
    private Date date;
    private int note;  // Note de l'avis
    private String carModel;  // Modèle de la voiture associée à l'avis

    public Review(Date date, int note, String carModel) {
        this.date = date;
        this.note = note;
        this.carModel = carModel;
    }

    public Date getDate() {
        return date;
    }

    public int getNote() {
        return note;
    }

    public String getCarModel() {
        return carModel;
    }
}
