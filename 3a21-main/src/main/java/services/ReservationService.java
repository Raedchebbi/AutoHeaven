package services;

import utils.MyDb;

import java.util.List;

public abstract class ReservationService<T> implements Crud<T> {

    public abstract void create(T reservation) throws Exception;

    public abstract void update(T reservation) throws Exception;

    @Override
    public void delete(T obj) throws Exception {
        delete((Integer) obj); // Assuming obj is of type Integer (id_reservation)
    }

    @Override
    public void delete(int id) throws Exception {
        String query = "DELETE FROM reservation WHERE id_reservation = ?";
        try (var pst = MyDb.getInstance().getConn().prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Reservation deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error deleting reservation.");
        }
    }

    public abstract List<T> getAll() throws Exception;

    public abstract T getById(int id) throws Exception;
}
