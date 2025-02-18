package models;

import java.util.Date;

public class Remorquage {
    private int id_rm;
    private User user;
    private Voiture voiture;
    private Date date_demande;
    private String adresse;
    private String destination;
    private String statut;
    private double prix;
    private Date date_fin;

    public Remorquage() {}

    public Remorquage(int id_rm, User user, Voiture voiture, Date date_demande, String adresse, String destination, String statut, double prix, Date date_fin) {
        this.id_rm = id_rm;
        this.user = user;
        this.voiture = voiture;
        this.date_demande = date_demande;
        this.adresse = adresse;
        this.destination = destination;
        this.statut = statut;
        this.prix = prix;
        this.date_fin = date_fin;
    }

    public int getId_rm() {
        return id_rm;
    }

    public void setId_rm(int id_rm) {
        this.id_rm = id_rm;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Voiture getVoiture() {
        return voiture;
    }
    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    public Date getDate_demande() {
        return date_demande;
    }

    public void setDate_demande(Date date_demande) {
        this.date_demande = date_demande;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    @Override
    public String toString() {
        return "Remorquage{" +
                "id_rm=" + id_rm +
                ", user_id=" + (user != null ? user.getId() : "null") +
                ", voiture_id=" + (voiture != null ? voiture.getId_v() : "null") +
                ", date_demande=" + date_demande +
                ", adresse='" + adresse + '\'' +
                ", destination='" + destination + '\'' +
                ", statut='" + statut + '\'' +
                ", prix=" + prix +
                ", date_fin=" + date_fin +
                '}';
    }
}