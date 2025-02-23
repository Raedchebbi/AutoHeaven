package models;

public class Offre {
    private int id_offre;
    private String type_offre;
    private String description;
    private double taux_reduction;
    private String date_debut;
    private String date_fin;
    private int id_equipement;
    private String image;

    public Offre() {}

    public Offre(int id_offre, String type_offre, String description, double taux_reduction, String date_debut, String date_fin, int id_equipement, String image) {
        this.id_offre = id_offre;
        this.type_offre = type_offre;
        this.description = description;
        this.taux_reduction = taux_reduction;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.id_equipement = id_equipement;
        this.image = image;
    }

    public int getId_offre() {
        return id_offre;
    }
    public void setId_offre(int id_offre) {
        this.id_offre = id_offre;
    }

    public String getType_offre() {
        return type_offre;
    }
    public void setType_offre(String type_offre) {
        this.type_offre = type_offre;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public double getTaux_reduction() {
        return taux_reduction;
    }
    public void setTaux_reduction(double taux_reduction) {
        this.taux_reduction = taux_reduction;
    }

    public String getDate_debut() {
        return date_debut;
    }
    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }
    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public int getId_equipement() {
        return id_equipement;
    }
    public void setId_equipement(int id_equipement) {
        this.id_equipement = id_equipement;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "Offre{" +
                "id_offre=" + id_offre +
                ", type_offre='" + type_offre + '\'' +
                ", description='" + description + '\'' +
                ", taux_reduction=" + taux_reduction +
                ", date_debut='" + date_debut + '\'' +
                ", date_fin='" + date_fin + '\'' +
                ", id_equipement=" + id_equipement +
                ", image='" + image + '\'' +
                '}';
    }
}
