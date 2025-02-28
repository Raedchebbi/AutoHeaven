package services;

import java.sql.SQLException;
import java.util.List;

public interface Crud<T> {
    void create(T obj) throws Exception;

    void update(T obj) throws Exception;

    void updateStatus(int idRec, String newStatus) throws SQLException;

    void delete(T obj) throws Exception;

    void delete(int obj) throws Exception;

    List<T> getAll() throws Exception;

}
