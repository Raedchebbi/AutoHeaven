package services;

import models.Messagerie;
import services.Crud;
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
        String checkRecSql = "SELECT id_rec FROM reclamation WHERE id_rec = ?";
        PreparedStatement checkRecStmt = conn.prepareStatement(checkRecSql);
        checkRecStmt.setInt(1, obj.getId_rec());
        ResultSet rs = checkRecStmt.executeQuery();

        if (rs.next()) {
            String sql = "INSERT INTO messagerie (sender, message, id_rec, datemessage, receiver, id_user) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, obj.getSender());
            stmt.setString(2, obj.getMessage());
            stmt.setInt(3, obj.getId_rec());
            stmt.setTimestamp(4, Timestamp.valueOf(obj.getDateMessage()));
            stmt.setString(5, obj.getReceiver());
            stmt.setInt(6, obj.getId_user());
            stmt.executeUpdate();
        } else {
            throw new Exception("❌ La réclamation avec ID " + obj.getId_rec() + " n'existe pas.");
        }
    }

    @Override
    public void update(Messagerie obj) throws Exception {
        String sql = "UPDATE messagerie SET sender = ?, message = ?, id_rec = ?, datemessage = ?, receiver = ?, id_user = ? WHERE id_m = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, obj.getSender());
        stmt.setString(2, obj.getMessage());
        stmt.setInt(3, obj.getId_rec());
        stmt.setTimestamp(4, Timestamp.valueOf(obj.getDateMessage()));
        stmt.setString(5, obj.getReceiver());
        stmt.setInt(6, obj.getId_user());
        stmt.setInt(7, obj.getId_m());
        stmt.executeUpdate();
    }

    @Override
    public void delete(Messagerie obj) throws Exception {
        deleteById(obj.getId_m());
    }

    public void deleteById(int id_m) throws Exception {
        String sql = "DELETE FROM messagerie WHERE id_m = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id_m);
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
                    rs.getInt("id_rec"),
                    rs.getTimestamp("datemessage").toLocalDateTime(),
                    rs.getString("receiver"),
                    rs.getInt("id_user")
            );
            messages.add(msg);
        }
        return messages;
    }
}
