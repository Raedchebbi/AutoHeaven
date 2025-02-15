package models;

import java.time.LocalDate;

public class Reclamation {
    private int idRec;
    private String titre;
    private String contenu;
    private String status;
    private LocalDate dateCreation;
    private int idUser;
    private String cin;
    private String nom;
    private String prenom;
    private String tel;
    private String email;

    // Constructeur avec les informations utilisateur
    public Reclamation(int idRec, String titre, String contenu, String status, LocalDate dateCreation, int idUser, String cin, String nom, String prenom, String tel, String email) {
        this.idRec = idRec;
        this.titre = titre;
        this.contenu = contenu;
        this.status = status;
        this.dateCreation = dateCreation;
        this.idUser = idUser;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
    }

    // Constructeur sans les informations utilisateur
    public Reclamation(int idRec, String titre, String contenu, String status, LocalDate dateCreation, int idUser) {
        this.idRec = idRec;
        this.titre = titre;
        this.contenu = contenu;
        this.status = status;
        this.dateCreation = dateCreation;
        this.idUser = idUser;
    }

    // Getters et setters
    public int getIdRec() {
        return idRec;
    }

    public void setIdRec(int idRec) {
        this.idRec = idRec;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
