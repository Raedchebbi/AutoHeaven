package services;

import models.Equipement;

import java.util.List;

public interface CrudPanier<T> {
    void create(T obj) throws Exception;
    void update(T obj) throws Exception;


    void delete(int id) throws Exception;
    public void deleteByid(int id) throws Exception;

    List<T> getAll(int id) throws Exception;
    Equipement getEquipementId(int id) throws Exception;

}
