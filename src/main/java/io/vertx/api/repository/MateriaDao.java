package io.vertx.api.repository;


import io.vertx.api.entity.Materia;
import io.netty.util.internal.StringUtil;
import io.vertx.core.json.JsonObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class MateriaDao {
    private static MateriaDao instance;
    protected EntityManager entityManager;

    public static MateriaDao getInstance() {
        if (instance == null) {
            instance = new MateriaDao();
        }
        return instance;
    }

    private MateriaDao() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }

    public Materia getById(String id_materia) {
        Object result = entityManager.find(Materia.class, id_materia);
        if (result != null) {
            return (Materia) result;
        } else {
            return null;
        }
    }

    public List<Materia> findAll() {
        return entityManager.createQuery("FROM " + Materia.class.getName()).getResultList();
    }

    public List<Materia> getByFilter(JsonObject filter) {
        Query query = entityManager.createQuery(sqlFilter(filter));
        parametersFilter(filter, query);
        List<Materia> result = query.getResultList();

        return result;
    }

    private String sqlFilter(JsonObject filter) {
        String sqlQuery = "SELECT m FROM Materia m";
        String preParameter = " WHERE";
        String sqlParameter = "";

        if (!StringUtil.isNullOrEmpty(filter.getString("nombre"))) {
            sqlParameter += preParameter + " upper(m.nombre) LIKE upper(:nombre)";
            preParameter = " OR";
        }
        if (!StringUtil.isNullOrEmpty(filter.getString("cant_creditos"))) {
            sqlParameter += preParameter + " upper(m.cant_creditos) LIKE upper(:cant_creditos)";
            preParameter = " OR";
        }

        if (!StringUtil.isNullOrEmpty(filter.getString("tipo_materia"))) {
            sqlParameter += preParameter + " upper(m.tipo_materia) LIKE upper(:tipo_materia)";
            preParameter = " OR";
        }

        return sqlQuery + sqlParameter;
    }

    private void parametersFilter(JsonObject filter, Query query) {
        if (!StringUtil.isNullOrEmpty(filter.getString("nombre"))) {
            String likeNameParam = "%" + filter.getString("nombre") + "%";
            query.setParameter("nombre", likeNameParam);
        }

        if (!StringUtil.isNullOrEmpty(filter.getString("tipo_materia"))) {
            String likeNameParam = "%" + filter.getString("tipo_materia") + "%";
            query.setParameter("tipo_materia", likeNameParam);
        }

    }

    public void persist(Materia materia) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(materia);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(Materia materia) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(materia);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void remove(Materia materia) {
        try {
            entityManager.getTransaction().begin();
            materia = entityManager.find(Materia.class, materia.getId_materia());
            entityManager.remove(materia);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void removeById(String id_materia) {
        try {
            Materia materia = getById(id_materia);
            remove(materia);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
