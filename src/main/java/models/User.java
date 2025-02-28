package models;

public class User {
    private int id;
    private int cin;
    private String nom;
    private String prenom;
    private int tel;
    private String email;
    private String password;
    private String role;
    private String adresse;
    private String username;
    private String photoProfile; // New attribute for profile picture
    private String ban = "non"; // New attribute with default value "non"

    public User() {}

    public User(int id, int cin, String nom, String prenom, int tel, String email, String password, String role, String adresse, String username, String photoProfile, String ban) {
        this.id = id;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.password = password;
        this.role = role;
        this.adresse = adresse;
        this.username = username;
        this.photoProfile = photoProfile;
        this.ban = (ban != null) ? ban : "non"; // Ensures default value
    }

    public User(int cin, String nom, String prenom, int tel, String email, String password, String role, String adresse, String username, String photoProfile, String ban) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.password = password;
        this.role = role;
        this.adresse = adresse;
        this.username = username;
        this.photoProfile = photoProfile;
        this.ban = (ban != null) ? ban : "non";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCin() { return cin; }
    public void setCin(int cin) { this.cin = cin; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public int getTel() { return tel; }
    public void setTel(int tel) { this.tel = tel; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPhotoProfile() { return photoProfile; }
    public void setPhotoProfile(String photoProfile) { this.photoProfile = photoProfile; }

    public String getBan() { return ban; }
    public void setBan(String ban) { this.ban = (ban != null) ? ban : "non"; }

    @Override
    public String toString() {
        return "User{id=" + id + ", cin=" + cin + ", nom='" + nom + "', prenom='" + prenom + "', tel=" + tel +
                ", email='" + email + "', username='" + username + "', role='" + role + "', adresse='" + adresse +
                "', photoProfile='" + photoProfile + "', ban='" + ban + "'}";
    }
}