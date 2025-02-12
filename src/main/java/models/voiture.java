package models;

public class voiture {
    private int id_v, kilometrage ;
    private String marque, description,couleur;
    private float prix;

    public voiture() {
    }

    public voiture(int kilometrage, String marque, String description, String couleur,float prix ) {

        this.kilometrage = kilometrage;
        this.marque = marque;
        this.description = description;
        this.couleur = couleur;
        this.prix = prix;
    }

    public voiture(int id_v, int kilometrage, String marque, String description , String couleur,float prix  ) {
        this.id_v = id_v;
        this.kilometrage = kilometrage;
        this.marque = marque;
        this.description = description;
        this.couleur = couleur;
        this.prix = prix;

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

    public String getCouleur() {return couleur; }

    public void setCouleur(String couleur) { this.couleur = couleur;  }

    public float getPrix() {return prix;}
    public void setPrix(float prix) {this.prix = prix;}




    @Override
    public String toString() {
        return "voiture{" +
                "id_v=" + id_v +
                ", kilometrage=" + kilometrage +
                ", marque='" + marque + '\'' +
                ", description='" + description + '\'' +
                ", couleur='" + couleur + '\'' +
                ", prix=" + prix +  '\'' +
                '}';
    }
}
