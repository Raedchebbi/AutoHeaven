package services;

import java.util.List;


    public interface Crud<T> {
        void create(T obj) throws Exception;

        void update(T obj) throws Exception;

        void delete(T obj) throws Exception;
        void delete(int obj) throws Exception;

        List<T> getAll() throws Exception;

    }

