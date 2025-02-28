package models;

public class Panier {
    private int id_p;
    private int quantite;
    private int id ;
    private int id_e ;
    public Panier() {}
    public Panier(int id_p, int quantite,int id , int id_e) {
        this.id_p = id_p;
        this.quantite = quantite;
        this.id = id;
        this.id_e = id_e;

    }
    public Panier(int quantite,int id , int id_e) {

        this.quantite = quantite;
        this.id = id;
        this.id_e = id_e;

    }
    public int getId_p() {
        return id_p;
    }
    public void setId_p(int id_p) {
        this.id_p = id_p;
    }
    public int getQuantite() {
        return quantite;
    }
    public void setQuantite(int quantite) {
        this.quantite = quantite;

    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId_e() {
        return id_e;
    }
    public void setId_e(int id_e) {
        this.id_e = id_e;
    }

    @Override
    public String toString() {
        return "Panier{" +
                "id_p=" + id_p +
                ", quantite=" + quantite +
                ", id=" + id +
                ", id_e=" + id_e +
                '}';
    }
}
