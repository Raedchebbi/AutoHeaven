package models;

public class Equipement {
    private int id ;
    private String nom;
    private String description ;
    private String image;
    private String reference;
    private String marque ;
    public Equipement() {

    }

    public Equipement(int id, String nom, String description, String image , String reference, String marque) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.reference = reference;
        this.marque = marque;
    }
    public Equipement(String nom, String description, String image , String reference, String marque) {

        this.nom = nom;
        this.description = description;
        this.image = image;
        this.reference = reference;
        this.marque = marque;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getReference() {
        return reference;
    }
    public void setReference(String reference) {
        this.reference = reference;
    }
    public String getMarque() {
        return marque;
    }
    public void setMarque(String marque) {
        this.marque = marque;
    }

    @Override
    public String toString() {
        return "Equipement{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", reference='" + reference + '\'' +
                ", marque='" + marque + '\'' +
                '}';
    }
}
