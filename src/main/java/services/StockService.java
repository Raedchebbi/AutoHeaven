package services;

import models.Equipement;
import models.Stock;
import utils.MyDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StockService {
    Connection conn;

    public StockService() {
        this.conn = MyDb.getInstance().getConn();
    }
    public Stock getStockById(int id) throws Exception {

        String sql = "select * from stock where id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {

            return new Stock(rs.getInt("id"), rs.getInt("quantite"), rs.getDouble("prixvente"), rs.getInt("id"));
        } else {
            throw new Exception("Stock not found with id: " + id);
        }
    }
}
