package services;

import models.Commande;

import java.util.List;

public interface CrudCommande<T> {
    int create(int id) throws Exception;
    void update(int id , double montantTotal) throws Exception;


    double getPrixEquipement(int id) throws Exception;

    List<T> getAllByidU(int id) throws Exception;
    Commande getCommande(int id) throws Exception;
    List<T> getAll(int id) throws Exception;
}
