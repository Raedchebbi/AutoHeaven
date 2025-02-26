package services;

import models.Equipement;
import models.Notification;
import utils.MyDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    Connection conn;
    public NotificationService(){
        this.conn = MyDb.getInstance().getConn();
    }
    public void checkStockLevels() throws Exception {
        String query = "SELECT e.id, e.nom FROM equipement e " +
                "JOIN stock s ON e.id = s.id WHERE s.quantite = 0";


             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idEquipement = rs.getInt("id");
                String nomEquipement = rs.getString("nom");

                if (!isNotificationAlreadyExists(idEquipement)) {
                    insertNotification(idEquipement, nomEquipement);
                }
            }

    }

    public void insertNotification(int idEquipement, String equipement) throws Exception {
       // EquipementService es = new EquipementService();
       // Equipement e=new Equipement();
       // e=es.getEquipementById(idEquipement);
       // String nom = e.getNom();
        String insertQuery = "INSERT INTO notification (ide, message, dateNotification, etat) " +
                "VALUES (?, ?, NOW(), 'non lu')";
        try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
            stmt.setInt(1, idEquipement);
            stmt.setString(2, equipement + " est en rupture de stock");
            stmt.executeUpdate();
        }
        }

    public void removeResolvedNotifications() throws Exception {
        String query = "DELETE FROM notification WHERE ide IN " +
                "(SELECT s.id FROM stock s WHERE s.quantite > 0)";


             PreparedStatement stmt = conn.prepareStatement(query);
            stmt.executeUpdate();

    }
    public void getAllNotifications() throws Exception {
        String query = "SELECT * FROM notification";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        List<Notification> notifications = new ArrayList<>();
        while (rs.next()) {
            Notification notification = new Notification(
                    rs.getInt("id"),
                    rs.getString("message"),
                    rs.getTimestamp("date_notif").toLocalDateTime(),
                    rs.getInt("ide"),

                    rs.getString("etat")
            );
            notifications.add(notification);
        }


    }
    public void checkStockAndNotify() throws Exception {
        String query = "SELECT e.id, e.nom FROM equipement e JOIN stock s ON e.id = s.id WHERE s.quantite = 0";


             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idEquipement = rs.getInt("id");
                String nomEquipement = rs.getString("nom");

                // Vérifier si la notification existe déjà pour éviter les doublons
                if (!isNotificationAlreadyExists(idEquipement)) {
                    insertNotification(idEquipement, nomEquipement);
                }

            }}



    private boolean isNotificationAlreadyExists( int idEquipement) throws Exception {
        String checkQuery = "SELECT COUNT(*) FROM notification WHERE ide = ? AND etat = 'non lu'";
        try (PreparedStatement stmt = conn.prepareStatement(checkQuery)) {
            stmt.setInt(1, idEquipement);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

}

