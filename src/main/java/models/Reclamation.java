package models;

import java.time.LocalDate;

public class Reclamation {
    private int id_rec, id;
    private String titre, contenu, status;
    private LocalDate dateCreation;

    // Ajout des attributs utilisateur
    private String cin, nom, prenom, tel, email;

    public Reclamation() {
    }

    // Constructeur sans les détails utilisateur
    public Reclamation(String titre, String contenu, String status, LocalDate dateCreation, int id) {
        this.titre = titre;
        this.contenu = contenu;
        this.status = status;
        this.dateCreation = dateCreation;
        this.id = id;
    }

    // Constructeur avec les détails utilisateur
    public Reclamation(int id_rec, String titre, String contenu, String status, LocalDate dateCreation, int id) {
        this.id_rec = id_rec;
        this.titre = titre;
        this.contenu = contenu;
        this.status = status;
        this.dateCreation = dateCreation;
        this.id = id;
    }

    // Constructeur avec les informations de l'utilisateur
    public Reclamation(int id_rec, String titre, String contenu, String status, LocalDate dateCreation, int id,
                       String cin, String nom, String prenom, String tel, String email) {
        this.id_rec = id_rec;
        this.titre = titre;
        this.contenu = contenu;
        this.status = status;
        this.dateCreation = dateCreation;
        this.id = id;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
    }

    // Getters et Setters pour les attributs de réclamation
    public int getIdRec() { return id_rec; }
    public void setIdRec(int idRec) { this.id_rec = idRec; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDate dateCreation) { this.dateCreation = dateCreation; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    // Getters et Setters pour les attributs utilisateur
    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Méthode pour récupérer l'ID utilisateur
    public int getIdUser() {
        return id;
    }

    // toString pour afficher les informations de la réclamation
    @Override
    public String toString() {
        return "Reclamation{" +
                "idRec=" + id_rec +
                ", titre='" + titre + '\'' +
                ", contenu='" + contenu + '\'' +
                ", status='" + status + '\'' +
                ", dateCreation=" + dateCreation +
                ", id=" + id +
                ", cin='" + cin + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
