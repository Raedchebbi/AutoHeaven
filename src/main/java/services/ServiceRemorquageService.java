package services;

import models.ServiceRemorquage;
import utils.MyDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceRemorquageService {
    private Connection conn;

    public ServiceRemorquageService() {
        this.conn = MyDb.getInstance().getConn();
    }

    public void create(ServiceRemorquage service) throws Exception {
        String sql = "INSERT INTO service_remorquage (id_vr, nom_chauffeur, lieu, date_service, statut) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, service.getId_vr());
        stmt.setString(2, service.getNom_chauffeur());
        stmt.setString(3, service.getLieu());
        stmt.setTimestamp(4, new Timestamp(service.getDate_service().getTime()));
        stmt.setString(5, service.getStatut());

        int rowsInserted = stmt.executeUpdate();

        if (rowsInserted > 0) {
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                service.setId_s(generatedKeys.getInt(1));
                System.out.println("Service ajoute avec ID : " + service.getId_s());
            }
        } else {
            throw new Exception("Échec de l'insertion du service.");
        }
    }

    // Méthode pour mettre à jour un service de remorquage
    public void update(ServiceRemorquage service) throws Exception {
        String sql = "UPDATE service_remorquage SET id_vr = ?, nom_chauffeur = ?, lieu = ?, date_service = ?, statut = ? WHERE id_s = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, service.getId_vr());
        stmt.setString(2, service.getNom_chauffeur());
        stmt.setString(3, service.getLieu());
        stmt.setTimestamp(4, new Timestamp(service.getDate_service().getTime()));
        stmt.setString(5, service.getStatut());
        stmt.setInt(6, service.getId_s());

        stmt.executeUpdate();
    }

    // Méthode pour supprimer un service de remorquage
    public void delete(int id_s) throws Exception {
        String sql = "DELETE FROM service_remorquage WHERE id_s = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id_s);
        stmt.executeUpdate();
    }

    // Méthode pour récupérer un service de remorquage par son ID
    public ServiceRemorquage getById(int id_s) throws Exception {
        String sql = "SELECT * FROM service_remorquage WHERE id_s = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id_s);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new ServiceRemorquage(
                    rs.getInt("id_s"),
                    rs.getInt("id_vr"),
                    rs.getString("nom_chauffeur"),
                    rs.getString("lieu"),
                    rs.getTimestamp("date_service"),
                    rs.getString("statut")
            );
        }
        return null; // Aucun service trouvé
    }

    // Méthode pour récupérer tous les services de remorquage
    public List<ServiceRemorquage> getAll() throws Exception {
        String sql = "SELECT * FROM service_remorquage";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<ServiceRemorquage> services = new ArrayList<>();

        while (rs.next()) {
            ServiceRemorquage service = new ServiceRemorquage(
                    rs.getInt("id_s"),
                    rs.getInt("id_vr"),
                    rs.getString("nom_chauffeur"),
                    rs.getString("lieu"),
                    rs.getTimestamp("date_service"),
                    rs.getString("statut")
            );
            services.add(service);
        }
        return services;
    }
}