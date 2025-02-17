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
    private String username; // New attribute

    public User() {}

    public User(int id, int cin, String nom, String prenom, int tel, String email, String password, String role, String adresse, String username) {
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
    }

    // Overloaded constructor without ID (for new users)
    public User(int cin, String nom, String prenom, int tel, String email, String password, String role, String adresse, String username) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.password = password;
        this.role = role;
        this.adresse = adresse;
        this.username = username;
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

    public String getUsername() { return username; } // Getter for username
    public void setUsername(String username) { this.username = username; } // Setter for username

    @Override
    public String toString() {
        return "User{id=" + id + ", cin=" + cin + ", nom='" + nom + "', prenom='" + prenom + "', tel=" + tel +
                ", email='" + email + "', username='" + username + "', role='" + role + "', adresse='" + adresse + "'}";
    }
}
