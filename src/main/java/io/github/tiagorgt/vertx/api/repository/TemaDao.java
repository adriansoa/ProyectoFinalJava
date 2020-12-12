package io.github.tiagorgt.vertx.api.repository;


import io.github.tiagorgt.vertx.api.entity.Tema;
import io.netty.util.internal.StringUtil;
import io.vertx.core.json.JsonObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class TemaDao {
    private static TemaDao instance;
    protected EntityManager entityManager;

    public static TemaDao getInstance() {
        if (instance == null) {
            instance = new TemaDao();
        }

        return instance;
    }

    private TemaDao() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }

    public Tema getById(String id_tema) {
        Object result = entityManager.find(Tema.class, id_tema);
        if (result != null) {
            return (Tema) result;
        } else {
            return null;
        }
    }

    public List<Tema> findAll() {
        return entityManager.createQuery("FROM " + Tema.class.getName()).getResultList();
    }

    public List<Tema> getByFilter(JsonObject filter) {
        Query query = entityManager.createQuery(sqlFilter(filter));
        parametersFilter(filter, query);
        List<Tema> result = query.getResultList();

        return result;
    }

    private String sqlFilter(JsonObject filter) {
        String sqlQuery = "SELECT t FROM Tema t";
        String preParameter = " WHERE";
        String sqlParameter = "";

        if (!StringUtil.isNullOrEmpty(filter.getString("descripcion"))) {
            sqlParameter += preParameter + " upper(t.descripcion) LIKE upper(:descripcion)";
            preParameter = " OR";
        }

        return sqlQuery + sqlParameter;
    }

    private void parametersFilter(JsonObject filter, Query query) {
        if (!StringUtil.isNullOrEmpty(filter.getString("descripcion"))) {
            String likeNameParam = "%" + filter.getString("descripcion") + "%";
            query.setParameter("descripcion", likeNameParam);
        }

    }

    public void persist(Tema tema) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(tema);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(Tema tema) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(tema);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void remove(Tema tema) {
        try {
            entityManager.getTransaction().begin();
            tema = entityManager.find(Tema.class, tema.getId_tema());
            entityManager.remove(tema);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void removeById(String id_tema) {
        try {
            Tema tema = getById(id_tema);
            remove(tema);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
