package models;

public class categorie {

    private int id_c, nbr_porte ;
    private String type, type_carburant ,type_utilisation,transmission;

    public categorie(int id_c, int nbr_porte, String type, String type_carburant, String type_utilisation, String transmission) {
        this.id_c = id_c;
        this.nbr_porte = nbr_porte;
        this.type = type;
        this.type_carburant = type_carburant;
        this.type_utilisation = type_utilisation;
        this.transmission = transmission;
    }

    public categorie(int nbr_porte, String type, String type_carburant, String type_utilisation, String transmission) {
        this.nbr_porte = nbr_porte;
        this.type = type;
        this.type_carburant = type_carburant;
        this.type_utilisation = type_utilisation;
        this.transmission = transmission;
    }

    public categorie() {
    }

    public int getId_c() {
        return id_c;
    }

    public void setId_c(int id_c) {
        this.id_c = id_c;
    }

    public int getNbr_porte() {
        return nbr_porte;
    }

    public void setNbr_porte(int nbr_porte) {
        this.nbr_porte = nbr_porte;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType_carburant() {
        return type_carburant;
    }

    public void setType_carburant(String type_carburant) {
        this.type_carburant = type_carburant;
    }

    public String getType_utilisation() {
        return type_utilisation;
    }

    public void setType_utilisation(String type_utilisation) {
        this.type_utilisation = type_utilisation;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    @Override
    public String toString() {
        return "categorie{" +
                "id_c=" + id_c +
                ", nbr_porte=" + nbr_porte +
                ", type='" + type + '\'' +
                ", type_carburant='" + type_carburant + '\'' +
                ", type_utilisation='" + type_utilisation + '\'' +
                ", transmission='" + transmission + '\'' +
                '}';
    }
}
