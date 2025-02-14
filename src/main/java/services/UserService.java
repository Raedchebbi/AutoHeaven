package services;

import models.User;
import utils.MyDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserService implements Crud<User> {
    Connection conn;

    public UserService() {
        this.conn = MyDb.getInstance().getConn();
    }

    @Override
    public void create(User obj) throws Exception {
        String checkSql = "SELECT COUNT(*) FROM user WHERE cin = ? OR email = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkSql);
        checkStmt.setInt(1, obj.getCin());
        checkStmt.setString(2, obj.getEmail());
        ResultSet rs = checkStmt.executeQuery();
        rs.next();

        if (rs.getInt(1) > 0) {
            throw new Exception("Un utilisateur avec ce CIN ou cet email existe déjà !");
        }

        String sql = "INSERT INTO user (cin, nom, prenom, tel, email, password, role, adresse) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, obj.getCin());
        stmt.setString(2, obj.getNom());
        stmt.setString(3, obj.getPrenom());
        stmt.setInt(4, obj.getTel());
        stmt.setString(5, obj.getEmail());
        stmt.setString(6, obj.getPassword());
        stmt.setString(7, obj.getRole());
        stmt.setString(8, obj.getAdresse());

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
        String checkSql = "SELECT COUNT(*) FROM user WHERE (cin = ? OR email = ?) AND id != ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkSql);
        checkStmt.setInt(1, obj.getCin());
        checkStmt.setString(2, obj.getEmail());
        checkStmt.setInt(3, obj.getId());
        ResultSet rs = checkStmt.executeQuery();
        rs.next();

        if (rs.getInt(1) > 0) {
            throw new Exception("Erreur : Un autre utilisateur avec ce CIN ou cet email existe déjà !");
        }

        String sql = "UPDATE user SET cin = ?, nom = ?, prenom = ?, tel = ?, email = ?, role = ?, adresse = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, obj.getCin());
        stmt.setString(2, obj.getNom());
        stmt.setString(3, obj.getPrenom());
        stmt.setInt(4, obj.getTel());
        stmt.setString(5, obj.getEmail());
        stmt.setString(6, obj.getRole());
        stmt.setString(7, obj.getAdresse());
        stmt.setInt(8, obj.getId());

        stmt.executeUpdate();
    }

    @Override
    public void delete(User obj) throws Exception {
        String sql = "DELETE FROM user WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, obj.getId());

        stmt.executeUpdate();
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

            users.add(user);
        }
        return users;
    }
}
