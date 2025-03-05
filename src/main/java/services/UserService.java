package services;

import models.User;
import utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements Crud<User> {
    Connection conn;

    public UserService() {
        this.conn = MyDb.getInstance().getConn();
    }

    @Override
    public void create(User obj) throws Exception {
        String checkSql = "SELECT COUNT(*) FROM user WHERE cin = ? OR username = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkSql);
        checkStmt.setInt(1, obj.getCin());
        checkStmt.setString(2, obj.getUsername());
        ResultSet rs = checkStmt.executeQuery();
        rs.next();

        if (rs.getInt(1) > 0) {
            throw new Exception("Un utilisateur avec ce CIN, cet email ou ce nom d'utilisateur existe déjà !");
        }

        // Insert new user
        String sql = "INSERT INTO user (cin, nom, prenom, tel, email, password, role, adresse, username, photo_profile, ban, question, reponse) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, obj.getCin());
        stmt.setString(2, obj.getNom());
        stmt.setString(3, obj.getPrenom());
        stmt.setInt(4, obj.getTel());
        stmt.setString(5, obj.getEmail());
        stmt.setString(6, obj.getPassword());
        stmt.setString(7, obj.getRole());
        stmt.setString(8, obj.getAdresse());
        stmt.setString(9, obj.getUsername());
        stmt.setString(10, obj.getPhotoProfile());
        stmt.setString(11, obj.getBan() == null ? "non" : obj.getBan());
        stmt.setString(12, obj.getQuestion());
        stmt.setString(13, obj.getReponse());

        int rowsInserted = stmt.executeUpdate();
        System.out.println("Nouvel utilisateur ajouté : " + obj.getNom() + " " + obj.getPrenom());

        if (rowsInserted > 0) {
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                obj.setId(generatedId);
                System.out.println("Utilisateur ajouté avec ID : " + obj.getId());
            }
        } else {
            throw new Exception("Échec de l'insertion de l'utilisateur.");
        }
    }

    @Override
    public void update(User obj) throws Exception {
        String checkSql = "SELECT COUNT(*) FROM user WHERE (cin = ? OR username = ?) AND id != ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkSql);
        checkStmt.setInt(1, obj.getCin());
        checkStmt.setString(2, obj.getUsername());
        checkStmt.setInt(3, obj.getId());
        ResultSet rs = checkStmt.executeQuery();
        rs.next();

        if (rs.getInt(1) > 0) {
            throw new Exception("Erreur : Un autre utilisateur avec ce CIN ou ce nom d'utilisateur existe déjà !");
        }

        // Update user (CIN can be updated now)
        String sql = "UPDATE user SET cin = ?, nom = ?, prenom = ?, tel = ?, email = ?, password = ?, role = ?, adresse = ?, username = ?, photo_profile = ?, ban = ?, question = ?, reponse = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, obj.getCin()); // Allow CIN to be updated
        stmt.setString(2, obj.getNom());
        stmt.setString(3, obj.getPrenom());
        stmt.setInt(4, obj.getTel());
        stmt.setString(5, obj.getEmail());
        stmt.setString(6, obj.getPassword());
        stmt.setString(7, obj.getRole());
        stmt.setString(8, obj.getAdresse());
        stmt.setString(9, obj.getUsername());
        stmt.setString(10, obj.getPhotoProfile());
        stmt.setString(11, obj.getBan() == null ? "non" : obj.getBan());
        stmt.setString(12, obj.getQuestion());
        stmt.setString(13, obj.getReponse());
        stmt.setInt(14, obj.getId());

        stmt.executeUpdate();
    }

    @Override
    public void delete(User obj) throws Exception {
        String sql = "DELETE FROM user WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, obj.getId());

        stmt.executeUpdate();
        System.out.println("Utilisateur supprimé.");
    }

    @Override
    public List<User> getAll() throws Exception {
        String sql = "SELECT * FROM user";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<User> users = new ArrayList<>();

        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setCin(rs.getInt("cin"));
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setTel(rs.getInt("tel"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
            user.setAdresse(rs.getString("adresse"));
            user.setUsername(rs.getString("username"));
            user.setPhotoProfile(rs.getString("photo_profile"));
            user.setBan(rs.getString("ban"));
            user.setQuestion(rs.getString("question"));
            user.setReponse(rs.getString("reponse"));

            users.add(user);
        }
        return users;
    }
    public boolean cinExists(String cin) throws Exception {
        String sql = "SELECT COUNT(*) FROM user WHERE cin = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, cin);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0; // Returns true if CIN exists
        }
        return false;
    }

    public boolean emailExists(String email) throws Exception {
        String sql = "SELECT COUNT(*) FROM user WHERE email = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0; // Returns true if email exists
        }
        return false;
    }

    public boolean usernameExists(String username) throws Exception {
        String sql = "SELECT COUNT(*) FROM user WHERE username = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0; // Returns true if username exists
        }
        return false;
    }

    public String getPhotoProfileById(int userId) {
        String query = "SELECT photo_profile FROM user WHERE id = ?";
        String photoProfile = null;

        // Create an instance of MyDb to get the connection
        MyDb connectNow = new MyDb();
        Connection conn = connectNow.getConn();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                photoProfile = rs.getString("photo_profile");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la photo de profil: " + e.getMessage());
        }

        return photoProfile;
    }

    @Override

    public void updateStatus(int userId, String newStatus) throws SQLException {
        String sql = "UPDATE user SET status = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }

    }

    @Override
    public void delete(int obj) throws Exception {

    }



    public User getById(int id) throws Exception {
        String sql = "SELECT * FROM user WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new User(
                    rs.getInt("id"),
                    rs.getInt("cin"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getInt("tel"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getString("adresse"),
                    rs.getString("username"),
                    rs.getString("photo_profile"),
                    rs.getString("ban"),
                    rs.getString("question"),
                    rs.getString("reponse")

            );
        }
        return null; // Aucun utilisateur trouvé
    }

    public List<User> getAllClients() throws Exception {
        List<User> clients = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE role = 'client'";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            User user = new User(
                    rs.getInt("id"),
                    rs.getInt("cin"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getInt("tel"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getString("adresse"),
                    rs.getString("username"),
                    rs.getString("photo_profile"),
                    rs.getString("ban"),
                    rs.getString("question"),
                    rs.getString("reponse")
            );
            clients.add(user);
        }
        return clients;
    }

    public List<User> getAllMechanics() throws Exception {
        List<User> mechanics = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE role = 'mecanicien'";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            User user = new User(
                    rs.getInt("id"),
                    rs.getInt("cin"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getInt("tel"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getString("adresse"),
                    rs.getString("username"),
                    rs.getString("photo_profile"),
                    rs.getString("ban"),
                    rs.getString("question"),
                    rs.getString("reponse")
            );
            mechanics.add(user);
        }
        return mechanics;
    }
    public List<String> getAllEmails() throws SQLException {
        List<String> emails = new ArrayList<>();
        String sql = "SELECT email FROM user";  // Modifier selon ton besoin (ex: WHERE role = 'client')

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                emails.add(rs.getString("email"));
            }
        }
        return emails;
    }




}