
package models;

public class Voiture {
    private int id_v, kilometrage, id_c;
    private double prix;
    private String marque, description, couleur, image, disponibilite;


    public Voiture() {
    }

    public Voiture(String marque, String description, int kilometrage, String couleur, double prix, String image, int id_c, String disponibilite) {
        this.marque = marque;
        this.description = description;
        this.kilometrage = kilometrage;
        this.couleur = couleur;
        this.prix = prix;
        this.image = image;
        this.id_c = id_c;
        this.disponibilite = disponibilite;
    }

    public Voiture(int id_v, String marque, String description, int kilometrage, String couleur, double prix, String image, int id_c, String disponibilite) {
        this.id_v = id_v;
        this.marque = marque;
        this.description = description;
        this.kilometrage = kilometrage;
        this.couleur = couleur;
        this.prix = prix;
        this.image = image;
        this.id_c = id_c;
        this.disponibilite = disponibilite;
    }

    public int getId_v() {
        return id_v;
    }

    public void setId_v(int id_v) {
        this.id_v = id_v;
    }

    public int getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(int kilometrage) {
        this.kilometrage = kilometrage;
    }

    public int getId_c() {
        return id_c;
    }

    public void setId_c(int id_c) {
        this.id_c = id_c;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(String disponibilite) {
        this.disponibilite = disponibilite;
    }

    @Override
    public String toString() {
        return "Voiture{" +
                "id_v=" + id_v +
                ", marque='" + marque + '\'' +
                ", description='" + description + '\'' +
                ", kilometrage=" + kilometrage +
                ", couleur='" + couleur + '\'' +
                ", prix=" + prix +
                ", image='" + image + '\'' +
                ", id_c=" + id_c +
                ", disponibilite='" + disponibilite + '\'' +
                '}';
    }
}
