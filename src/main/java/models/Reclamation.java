package models;

import java.time.LocalDate;

public class Reclamation {
    private int id_rec, id;
    private String titre, contenu, status;
    private LocalDate dateCreation;

    public Reclamation() {
    }

    public Reclamation(String titre, String contenu, String status, LocalDate dateCreation, int id) {
        this.titre = titre;
        this.contenu = contenu;
        this.status = status;
        this.dateCreation = dateCreation;
        this.id = id;
    }

    public Reclamation(int id_rec, String titre, String contenu, String status, LocalDate dateCreation, int id) {
        this.id_rec = id_rec;
        this.titre = titre;
        this.contenu = contenu;
        this.status = status;
        this.dateCreation = dateCreation;
        this.id = id;
    }

    public int getIdRec() { return id_rec; }
    public void setIdRec(int idRec) { this.id_rec = idRec; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDate dateCreation) { this.dateCreation = dateCreation; }

    public int getId() { return id; }
    public void setId (int id) { this.id = id; }

    @Override
    public String toString() {
        return "Reclamation{" +
                "idRec=" + id_rec +
                ", titre='" + titre + '\'' +
                ", contenu='" + contenu + '\'' +
                ", status='" + status + '\'' +
                ", dateCreation=" + dateCreation +
                ", id=" + id +
                '}';
    }
}
