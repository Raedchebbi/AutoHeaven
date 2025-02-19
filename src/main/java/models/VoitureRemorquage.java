package models;

public class VoitureRemorquage {
    private int id_vr;
    private String modele;
    private int annee;
    private String num_agence;
    private String statut;

    public VoitureRemorquage() {
    }

    public VoitureRemorquage(int id_vr, String modele, int annee, String num_agence, String statut) {
        this.id_vr = id_vr;
        this.modele = modele;
        this.annee = annee;
        this.num_agence = num_agence;
        this.statut = statut;
    }

    public int getId_vr() {
        return id_vr;
    }

    public void setId_vr(int id_vr) {
        this.id_vr = id_vr;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String getNum_agence() {
        return num_agence;
    }

    public void setNum_agence(String num_agence) {
        this.num_agence = num_agence;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "VoitureRemorquage{" +
                "id_vr=" + id_vr +
                ", modele='" + modele + '\'' +
                ", annee=" + annee +
                ", num_agence='" + num_agence + '\'' +
                ", statut='" + statut + '\'' +
                '}';
    }
}