package services;

import models.Lignecommande;

import java.util.List;

public interface CrudLigneCommande<T>
{
    void create(T obj) throws Exception;

    List<T> getAll(int id) throws Exception;
    Lignecommande getById(int id) throws Exception;
}
