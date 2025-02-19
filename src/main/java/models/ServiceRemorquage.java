package models;

import java.util.Date;

public class ServiceRemorquage {
    private int id_s;
    private int id_vr;
    private String nom_chauffeur;
    private String lieu;
    private Date date_service;
    private String statut;

    public ServiceRemorquage() {
    }

    public ServiceRemorquage(int id_s, int id_vr, String nom_chauffeur, String lieu, Date date_service, String statut) {
        this.id_s = id_s;
        this.id_vr = id_vr;
        this.nom_chauffeur = nom_chauffeur;
        this.lieu = lieu;
        this.date_service = date_service;
        this.statut = statut;
    }

    public int getId_s() {
        return id_s;
    }

    public void setId_s(int id_s) {
        this.id_s = id_s;
    }

    public int getId_vr() {
        return id_vr;
    }

    public void setId_vr(int id_vr) {
        this.id_vr = id_vr;
    }

    public String getNom_chauffeur() {
        return nom_chauffeur;
    }

    public void setNom_chauffeur(String nom_chauffeur) {
        this.nom_chauffeur = nom_chauffeur;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Date getDate_service() {
        return date_service;
    }

    public void setDate_service(Date date_service) {
        this.date_service = date_service;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "ServiceRemorquage{" +
                "id_s=" + id_s +
                ", id_vr=" + id_vr +
                ", nom_chauffeur='" + nom_chauffeur + '\'' +
                ", lieu='" + lieu + '\'' +
                ", date_service=" + date_service +
                ", statut='" + statut + '\'' +
                '}';
    }
}