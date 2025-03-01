package models;

import java.util.Date;

public class ResMecanicien {
    private int id_res_m;
    private int id_u; // ID de l'utilisateur
    private int id_mec; // ID du mécanicien
    private String adresse; // Adresse
    private String note; // Note
    private Date date; // Date de la réservation
    private String status; // Statut de la réservation

    public ResMecanicien() {}

    public ResMecanicien(int id_res_m, int id_u, int id_mec, String adresse, String note, Date date, String status) {
        this.id_res_m = id_res_m;
        this.id_u = id_u;
        this.id_mec = id_mec;
        this.adresse = adresse;
        this.note = note;
        this.date = date;
        this.status = status; // Initialiser le statut
    }

    public int getId_res_m() { return id_res_m; }
    public void setId_res_m(int id_res_m) { this.id_res_m = id_res_m; }

    public int getId_u() { return id_u; }
    public void setId_u(int id_u) { this.id_u = id_u; }

    public int getId_mec() { return id_mec; }
    public void setId_mec(int id_mec) { this.id_mec = id_mec; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; } // Getter et setter pour le statut

    @Override
    public String toString() {
        return "ResMecanicien{id_res_m=" + id_res_m + ", id_u=" + id_u + ", id_mec=" + id_mec + ", adresse='" + adresse + "', note='" + note + "', date=" + date + ", status='" + status + "'}";
    }
}