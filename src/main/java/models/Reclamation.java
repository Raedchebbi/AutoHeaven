package models;

import java.time.LocalDate;

public class Reclamation {
    private int idRec;
    private String titre;
    private String contenu;
    private String status;
    private LocalDate dateCreation;
    private int idUser;
    private String cin;
    private String nom;
    private String prenom;
    private String tel;
    private String email;

    // ✅ Constructeur complet avec informations utilisateur
    public Reclamation(int idRec, String titre, String contenu, String status, LocalDate dateCreation, int idUser, String cin, String nom, String prenom, String tel, String email) {
        this.idRec = idRec;
        this.titre = titre;
        this.contenu = contenu;
        this.status = status;
        this.dateCreation = dateCreation;
        this.idUser = idUser;
        this.cin = cin;
        this.nom = nom != null ? nom : "N/A";
        this.prenom = prenom != null ? prenom : "N/A";
        this.tel = tel != null ? tel : "N/A";
        this.email = email != null ? email : "N/A";
    }

    // ✅ Constructeur utilisé pour `getAll()`
    public Reclamation(int idRec, String titre, String contenu, String status, LocalDate dateCreation, int idUser) {
        this.idRec = idRec;
        this.titre = titre;
        this.contenu = contenu;
        this.status = status;
        this.dateCreation = dateCreation;
        this.idUser = idUser;
    }

    // ✅ Getters obligatoires
    public int getIdRec() { return idRec; }
    public int getIdUser() { return idUser; }
    public String getTitre() { return titre; }
    public String getContenu() { return contenu; }
    public String getStatus() { return status; }
    public LocalDate getDateCreation() { return dateCreation; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getTel() { return tel; }
    public String getEmail() { return email; }
}