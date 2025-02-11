package service;

import models.avis;
import utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvisService  implements Crud<avis> {
    private Connection conn;

    public AvisService() {
        this.conn = MyDb.getInstance().getConn();
    }

    @Override
    public void create(avis obj) throws Exception {
        String sql = "INSERT INTO avis (note, commentaire, dateavis, id, id_v) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, obj.getNote());
        stmt.setString(2, obj.getCommentaire());
        stmt.setDate(3, obj.getDateavis());
        stmt.setInt(4, obj.getIdUser());
        stmt.setInt(5, obj.getIdVoiture());
        stmt.executeUpdate();
    }

    @Override
    public void update(avis obj) throws Exception {
        String sql = "UPDATE avis SET note = ?, commentaire = ?, dateavis = ?, id = ?, id_v = ? WHERE id_a = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, obj.getNote());
        stmt.setString(2, obj.getCommentaire());
        stmt.setDate(3, obj.getDateavis());
        stmt.setInt(4, obj.getIdUser());
        stmt.setInt(5, obj.getIdVoiture());
        stmt.setInt(6, obj.getId_a());
        stmt.executeUpdate();
    }

    @Override
    public void delete(avis obj) throws Exception {
        String sql = "DELETE FROM avis WHERE id_a = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, obj.getId_a());
        stmt.executeUpdate();
    }

    @Override
    public List<avis> getAll() throws Exception {
        String sql = "SELECT * FROM avis";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<avis> avisList = new ArrayList<>();
        while (rs.next()) {
            avis a = new avis();
            a.setId_a(rs.getInt("id_a"));
            a.setNote(rs.getInt("note"));
            a.setCommentaire(rs.getString("commentaire"));
            a.setDateavis(rs.getDate("dateavis"));
            a.setIdUser(rs.getInt("id"));
            a.setIdVoiture(rs.getInt("id_v"));
            avisList.add(a);
        }
        return avisList;
    }
}
