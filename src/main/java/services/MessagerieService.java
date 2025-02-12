package services;

import models.Messagerie;
import utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessagerieService implements Crud<Messagerie> {
    private Connection conn;

    public MessagerieService() {
        this.conn = MyDb.getInstance().getConn();
    }

    @Override
    public void create(Messagerie obj) throws Exception {
        // Vérifier si la réclamation associée existe avant de créer le message
        String checkRecSql = "SELECT id_rec FROM reclamation WHERE id_rec = ?";
        PreparedStatement checkRecStmt = conn.prepareStatement(checkRecSql);
        checkRecStmt.setInt(1, obj.getId_rec());
        ResultSet rs = checkRecStmt.executeQuery();

        if (rs.next()) {
            // La réclamation existe, on peut ajouter le message
            String sql = "INSERT INTO messagerie (sender, message, id_rec, datemessage, receiver) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, obj.getSender());
            stmt.setString(2, obj.getMessage());
            stmt.setInt(3, obj.getId_rec());
            stmt.setTimestamp(4, Timestamp.valueOf(obj.getDatemessage()));
            stmt.setString(5, obj.getReceiver());
            stmt.executeUpdate();
        } else {
            throw new Exception("❌ La réclamation avec ID " + obj.getId_rec() + " n'existe pas.");
        }
    }

    @Override
    public void update(Messagerie obj) throws Exception {
        String sql = "UPDATE messagerie SET sender = ?, message = ?, id_rec = ?, datemessage = ?, receiver = ? WHERE id_m = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, obj.getSender());
        stmt.setString(2, obj.getMessage());
        stmt.setInt(3, obj.getId_rec());
        stmt.setTimestamp(4, Timestamp.valueOf(obj.getDatemessage()));
        stmt.setString(5, obj.getReceiver());
        stmt.setInt(6, obj.getId_m());
        stmt.executeUpdate();
    }

    @Override
    public void delete(int obj) throws Exception {
        String sql = "DELETE FROM messagerie WHERE id_m = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, obj);
        stmt.executeUpdate();
    }

    @Override
    public List<Messagerie> getAll() throws Exception {
        String sql = "SELECT * FROM messagerie";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<Messagerie> messages = new ArrayList<>();
        while (rs.next()) {
            Messagerie msg = new Messagerie(
                    rs.getInt("id_m"),
                    rs.getString("sender"),
                    rs.getString("message"),
                    rs.getString("receiver"),
                    rs.getTimestamp("datemessage").toLocalDateTime(),
                    rs.getInt("id_rec")
            );
            messages.add(msg);
        }
        return messages;
    }
}
