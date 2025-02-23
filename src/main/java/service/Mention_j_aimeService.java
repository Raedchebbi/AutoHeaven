package service;

import models.mentions_j_aime;
import utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Mention_j_aimeService implements Crud<mentions_j_aime> {
    private Connection conn;

    // Constructeur qui récupère la connexion à la base de données
    public Mention_j_aimeService() {
        this.conn = MyDb.getInstance().getConn();  // Connexion à la base de données via la classe MyDb
    }

    @Override
    public void create(mentions_j_aime obj) throws Exception {
        // Insertion dans la table mention_j_aime avec les colonnes id_user et id_a (id_avis)
        String sql = "INSERT INTO mention_j_aime (id_user, id_a) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, obj.getIdUser());
            stmt.setInt(2, obj.getIdAvis());  // Utilisation de id_a pour l'avis
            stmt.executeUpdate();
        }
    }

    @Override
    public void update(mentions_j_aime obj) throws Exception {

    }


    @Override
    public void delete(int idMention) throws Exception {
        // Suppression d'une mention par son id_mention
        String sql = "DELETE FROM mention_j_aime WHERE id_mention = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMention);  // Paramètre d'ID de la mention
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Mention supprimée avec succès.");
            } else {
                System.out.println("Aucune mention trouvée avec cet ID.");
            }
        }
    }

    @Override
    public List<mentions_j_aime> getAll() throws Exception {
        // Récupérer toutes les mentions de la table mention_j_aime
        String sql = "SELECT * FROM mention_j_aime";
        List<mentions_j_aime> mentionJAimeList = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                mentions_j_aime m = new mentions_j_aime();
                m.setIdMention(rs.getInt("id_mention"));
                m.setIdUser(rs.getInt("id_user"));
                m.setIdAvis(rs.getInt("id_a"));  // Récupérer l'id_a pour l'avis
                mentionJAimeList.add(m);
            }
        }
        return mentionJAimeList;
    }
}
