package models;

public class mentions_j_aime {

    private int idMention;
    private int idUser;
    private int idAvis;

    // Constructeur
    public mentions_j_aime(int idMention, int idUser, int idAvis) {
        this.idMention = idMention;
        this.idUser = idUser;
        this.idAvis = idAvis;
    }

    public mentions_j_aime() {

    }

    // Getters et setters
    public int getIdMention() {
        return idMention;
    }

    public void setIdMention(int idMention) {
        this.idMention = idMention;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdAvis() {
        return idAvis;
    }

    public void setIdAvis(int idAvis) {
        this.idAvis = idAvis;
    }

    @Override
    public String toString() {
        return "Mentions_j_aime{" +
                "idMention=" + idMention +
                ", idUser=" + idUser +
                ", idAvis=" + idAvis +
                '}';
    }
}
