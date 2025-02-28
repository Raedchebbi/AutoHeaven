package services;

import models.Lignecommande;
import models.User;

import java.util.List;
import java.util.Map;

public interface CrudLigneCommande<T>
{
    void create(T obj) throws Exception;

    List<T> getAllByClient(int id) throws Exception;
    List<T> getAllByIDC(int id) throws Exception;
    List<T> getByIDE(int id) throws Exception;


    Lignecommande getById(int id) throws Exception;
    User getByID(int id) throws Exception;


    int somEq(int idc, int id_e) throws Exception;
    public Map<String, Integer> getTop5Equipements() throws Exception ;
}
