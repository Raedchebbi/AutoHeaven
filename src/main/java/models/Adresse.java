package models;

public class Adresse {
    private int id_ad;
    private String rue;
    private String ville;
    private String codePostal;
    private String nomBatiment;
    private String numeroPorte;
    private int userId;


    public Adresse() {}

    public Adresse(int id_ad, String rue, String ville, String codePostal, String nomBatiment, String numeroPorte, int userId) {
        this.id_ad = id_ad;
        this.rue = rue;
        this.ville = ville;
        this.codePostal = codePostal;
        this.nomBatiment = nomBatiment;
        this.numeroPorte = numeroPorte;
        this.userId = userId;
    }


    public int getId_ad() {
        return id_ad;
    }
    public void setId_ad(int id_ad) {
        this.id_ad = id_ad;
    }

    public String getRue() {
        return rue;
    }
    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getVille() {
        return ville;
    }
    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }
    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getNomBatiment() {
        return nomBatiment;
    }
    public void setNomBatiment(String nomBatiment) {
        this.nomBatiment = nomBatiment;
    }

    public String getNumeroPorte() {
        return numeroPorte;
    }
    public void setNumeroPorte(String numeroPorte) {
        this.numeroPorte = numeroPorte;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Adresse{" +
                "id_ad=" + id_ad +
                ", rue='" + rue + '\'' +
                ", ville='" + ville + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", nomBatiment='" + nomBatiment + '\'' +
                ", numeroPorte='" + numeroPorte + '\'' +
                ", userId=" + userId +
                '}';
    }
}
