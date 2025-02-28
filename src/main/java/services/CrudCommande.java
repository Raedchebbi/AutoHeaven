package services;

import models.Commande;

import java.util.List;
import java.util.Map;

public interface CrudCommande<T> {
    int create(int id) throws Exception;
    void update(int id , double montantTotal) throws Exception;
    void updateStatus(int id , String status) throws Exception;


    double getPrixEquipement(int id) throws Exception;

    List<T> getAllByidU(int id) throws Exception;
    Commande getCommande(int id) throws Exception;
    List<T> getAll() throws Exception;
    Map<String , Integer> countCOM() throws Exception;
    Map<String , Double> countVente() throws Exception;
    void deleteCommande(int id) throws Exception;
}
