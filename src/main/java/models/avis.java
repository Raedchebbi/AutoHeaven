package models;

import java.sql.Date;

public class avis {
    private int id_a;         // Identifiant de l'avis (clé primaire)
    private int note;         // Note donnée dans l'avis (de 1 à 5, par exemple)
    private String commentaire;  // Commentaire laissé par l'utilisateur
    private Date dateavis;    // Date de l'avis
    private int idUser;       // Clé étrangère vers la table User (utilisateur qui a laissé l'avis)
    private int idVoiture;    // Clé étrangère vers la table Voiture (voiture concernée par l'avis)

    // Constructeur sans paramètres
    public avis() {
    }

    // Constructeur avec paramètres (sans id_a)
    public avis(int note, String commentaire, Date dateavis, int idUser, int idVoiture) {
        this.note = note;
        this.commentaire = commentaire;
        this.dateavis = dateavis;
        this.idUser = idUser;
        this.idVoiture = idVoiture;
    }

    // Constructeur avec id_a (utilisé lors de la récupération d'un avis de la base de données)
    public avis(int id_a, int note, String commentaire, Date dateavis, int idUser, int idVoiture) {
        this.id_a = id_a;
        this.note = note;
        this.commentaire = commentaire;
        this.dateavis = dateavis;
        this.idUser = idUser;
        this.idVoiture = idVoiture;
    }

    // Getters et setters pour tous les attributs
    public int getId_a() {
        return id_a;
    }

    public void setId_a(int id_a) {
        this.id_a = id_a;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Date getDateavis() {
        return dateavis;
    }

    public void setDateavis(Date dateavis) {
        this.dateavis = dateavis;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdVoiture() {
        return idVoiture;
    }

    public void setIdVoiture(int idVoiture) {
        this.idVoiture = idVoiture;
    }

    // Méthode toString pour afficher un objet Avis sous forme de chaîne
    @Override
    public String toString() {
        return "Avis{" +
                "id_a=" + id_a +
                ", note=" + note +
                ", commentaire='" + commentaire + '\'' +
                ", dateavis=" + dateavis +
                ", idUser=" + idUser +
                ", idVoiture=" + idVoiture +
                '}';
    }
}
