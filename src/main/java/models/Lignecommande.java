package models;

public class Lignecommande {
    private int id_l;
    private int quantite;
    private double prix_unitaire ;
    private int id_e;
    private int idc;

    public Lignecommande() {}

    public Lignecommande(int id_l, int quantite, double prix_unitaire, int id_e, int idc) {
        this.id_l = id_l;
        this.quantite = quantite;
        this.prix_unitaire = prix_unitaire;
        this.id_e = id_e;
        this.idc = idc;
    }
    public Lignecommande(int quantite, double prix_unitaire, int id_e, int idc) {

        this.quantite = quantite;
        this.prix_unitaire = prix_unitaire;
        this.id_e = id_e;
        this.idc = idc;
    }
    public int getQuantite() {
        return quantite;
    }
    public double getPrix_unitaire() {
        return prix_unitaire;
    }
    public int getId_e() {
        return id_e;
    }
    public int getIdc() {
        return idc;
    }
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    public void setPrix_unitaire(double prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }
    public void setId_e(int id_e) {
        this.id_e = id_e;
    }
    public void setIdc(int idc) {
        this.idc = idc;
    }

    public int getId_l() {
        return id_l;
    }

    public void setId_l(int id_l) {
        this.id_l = id_l;
    }

    @Override
    public String toString() {
        return "Commandeligne{" +
                "id_l=" + id_l +
                ", quantite=" + quantite +
                ", prix_unitaire=" + prix_unitaire +
                ", id_e=" + id_e +
                ", idc=" + idc +
                '}';
    }
}