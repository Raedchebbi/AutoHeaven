package models;

public class Stock {
    private int id_s ;
    private int quantite ;
    private double prixvente ;
    private int id;

    public Stock() {
    }
    public Stock(int id_s, int quantite, double prixvente, int id) {
        this.id_s = id_s;
        this.quantite = quantite;
        this.prixvente = prixvente;
        this.id = id;
    }
    public Stock(int quantite, double prixvente, int id) {

        this.quantite = quantite;
        this.prixvente = prixvente;
        this.id = id;
    }
    public int getId_s() {
        return id_s;
    }
    public int getQuantite() {
        return quantite;
    }
    public double getPrixvente() {
        return prixvente;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setId_s(int id_s) {
        this.id_s = id_s;
    }
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    public void setPrixvente(double prixvente) {
        this.prixvente = prixvente;
    }


    @Override
    public String toString() {
        return "Stock{" +
                "id_s=" + id_s +
                ", quantite=" + quantite +
                ", prixvente=" + prixvente +
                ", id=" + id +
                '}';
    }
}