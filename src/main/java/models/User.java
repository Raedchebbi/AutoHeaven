package models;

public class User {
    private int id, cin, tel, id_ad;
    private String nom, prenom, email, password, adresse, role;

    public User() {
    }

    public User(int cin, String nom, String prenom, int tel, String email, String password, String adresse, String role, int id_ad) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.password = password;
        this.adresse = adresse;
        this.role = role;
        this.id_ad = id_ad;
    }

    public User(int id, int cin, String nom, String prenom, int tel, String email, String password, String adresse, String role, int id_ad) {
        this.id = id;
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.password = password;
        this.adresse = adresse;
        this.role = role;
        this.id_ad = id_ad;
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

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public int getId_ad() { return id_ad; }
    public void setId_ad(int id_ad) { this.id_ad = id_ad; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", cin=" + cin +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", tel=" + tel +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", adresse='" + adresse + '\'' +
                ", role='" + role + '\'' +
                ", id_ad=" + id_ad +
                '}';
    }
}
