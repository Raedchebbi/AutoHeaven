package service;
import java.util.List;

import models.avis;

public interface Crud<T> {
    void create(T obj) throws Exception;
    void update(T obj) throws Exception;
    void delete(int id) throws Exception;
    List<T> getAll() throws Exception;
}

