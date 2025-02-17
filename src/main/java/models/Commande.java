package models;

import java.time.LocalDateTime;

public class Commande {
    private int id_com;
    private String status;
    private LocalDateTime date_com;
    private double montant_total;
    private int id;


    // Constructeur par défaut
    public Commande() {}

    // Constructeur avec paramètres
    public Commande(int id_com, LocalDateTime date_com, String status, double montant_total, int id) {
        this.id_com = id_com;
        this.date_com = date_com;
        this.status = status;
        this.montant_total = montant_total;
        this.id = id;

    }

    // Getters et Setters
    public int getId_com() {
        return id_com;
    }

    public void setId_com(int id_com) {
        this.id_com = id_com;
    }

    public LocalDateTime getDate_com() {
        return date_com;
    }

    public void setDate_com(LocalDateTime date_com) {
        this.date_com = date_com;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getMontant_total() {
        return montant_total;
    }

    public void setMontant_total(double montant_total) {
        this.montant_total = montant_total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    @Override
    public String toString() {
        return "Commande{" +
                "id_com=" + id_com +
                ", status='" + status + '\'' +
                ", date_com=" + date_com +
                ", montant_total=" + montant_total +
                ", id=" + id +

                '}';
    }
}
