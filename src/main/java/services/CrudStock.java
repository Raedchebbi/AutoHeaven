package services;

public interface CrudStock <T>{
    T getStockById(int id) throws Exception;
}
