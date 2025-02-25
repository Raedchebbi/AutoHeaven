package services;

import models.Equipement;

import java.util.List;
import java.util.Map;

public interface CrudEquipement <T,R>{
    //void create(T obj) throws Exception;

    void create(Equipement obj, int quantite, double prixvente) throws Exception;



    void update(Equipement obj, int quantite, double prixvente) throws Exception;

    void delete(int id) throws Exception;
    void updateQuantite(int id ,int quantite) throws Exception;

    List<R> getAll() throws Exception;
    T getEquipementById(int id) throws Exception;
    List<Equipement> rechercherEquipement(String critere) throws Exception;
    Map<String ,Integer> eqRef() throws Exception;
}