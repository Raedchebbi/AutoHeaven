package models;

import java.util.Date;

public class Mecanicien extends Reservation {
    private int id_mec;
    private String note;

    public Mecanicien(int id_res, int id, Date date_res, String type, int id_mec, String note) {
        super(id_res, id, date_res, "mecanicien");
        this.id_mec = id_mec;
        this.note = note;
    }

    public Mecanicien(int id, Date date_res, String type, int id_mec, String note) {
        super(id, date_res, "mecanicien");
        this.id_mec = id_mec;
        this.note = note;
    }

    public int getId_mec() {
        return id_mec;
    }
    public void setId_mec(int id_mec) {
        this.id_mec = id_mec;
    }

    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Mechanic{" +
                "ID Mecanicien = '" + id_mec + '\'' +
                ", Note = '" + note + '\'' +
                "} " + super.toString();
    }
}
