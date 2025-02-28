package models;

public class EquipementAffichage {
    private String nom;
    private String description;
    private String image;
    private int quantite;
    private double prixvente;
    private String reference;
    private String marque;
    private int id ;
    public EquipementAffichage(int id ,String nom, String description, String image,String reference, String marque, int quantite, double prixvente) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.quantite = quantite;
        this.prixvente = prixvente;
        this.reference = reference;
        this.marque = marque;

    }

    public EquipementAffichage(String nom, String description, String image,String reference, String marque, int quantite, double prixvente) {
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.quantite = quantite;
        this.prixvente = prixvente;
        this.reference = reference;
        this.marque = marque;

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
    public int getQuantite() {
        return quantite;
    }
    public double getPrixvente() {
        return prixvente;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    public void setPrixvente(double prixvente) {
        this.prixvente = prixvente;
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
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "EquipementAffichage{" +
                "nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", quantite=" + quantite +
                ", prixvente=" + prixvente +
                '}';
    }
}
