package io.github.tiagorgt.vertx.api.repository;


import io.github.tiagorgt.vertx.api.entity.Facultad;
import io.netty.util.internal.StringUtil;
import io.vertx.core.json.JsonObject;
import org.graalvm.compiler.nodes.extended.PluginFactory_NullCheckNode;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class FacultadDao {

    private static FacultadDao instance;
    protected EntityManager entityManager;

    public static FacultadDao getInstance() {
        if (instance == null) {
            instance = new FacultadDao();
        }

        return instance;
    }

    private FacultadDao() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }

    public Facultad getById(String id_facultad) {
        Object result = entityManager.find(Facultad.class, id_facultad);
        if (result != null) {
            return (Facultad) result;
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Facultad> findAll() {
        return entityManager.createQuery("FROM " + Facultad.class.getName()).getResultList();
    }


    public List<Facultad> getByFilter(JsonObject filter) {
        Query query = entityManager.createQuery(sqlFilter(filter));
        parametersFilter(filter, query);
        List<Facultad> result = query.getResultList();

        return result;
    }

    private String sqlFilter(JsonObject filter) {
        String sqlQuery = "SELECT l FROM facultad l";
        String preParameter = " WHERE";
        String sqlParameter = "";

        if (!StringUtil.isNullOrEmpty(filter.getString("descripcion"))) {
            sqlParameter += preParameter + " upper(l.descripcion) LIKE upper(:descripcion)";
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

    public void persist(Facultad facultad) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(facultad);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }


    public void merge(Facultad facultad) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(facultad);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void remove(Facultad facultad) {
        try {
            entityManager.getTransaction().begin();
            facultad = entityManager.find(Facultad.class, facultad.getId_facultad());
            entityManager.remove(facultad);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void removeById(String id_facultad) {
        try {
            Facultad facultad = getById(id_facultad);
            remove(facultad);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
