package models;

public class CamionRemorquage {
    private int id_cr;
    private String modele;
    private int annee;
    private String num_tel;
    private String statut;

    public CamionRemorquage() {}

    public CamionRemorquage(int id_cr, String modele, int annee, String num_tel, String statut) {
        this.id_cr = id_cr;
        this.modele = modele;
        this.annee = annee;
        this.num_tel = num_tel;
        this.statut = statut;
    }

    public int getId_cr() { return id_cr; }
    public void setId_cr(int id_cr) { this.id_cr = id_cr; }

    public String getModele() { return modele; }
    public void setModele(String modele) { this.modele = modele; }

    public int getAnnee() { return annee; }
    public void setAnnee(int annee) { this.annee = annee; }

    public String getNum_tel() { return num_tel; }
    public void setNum_tel(String num_tel) { this.num_tel = num_tel; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    @Override
    public String toString() {
        return "CamionRemorquage{id_cr=" + id_cr + ", modele='" + modele + "', annee=" + annee +
                ", num_tel='" + num_tel + "', statut='" + statut + "'}";
    }
}