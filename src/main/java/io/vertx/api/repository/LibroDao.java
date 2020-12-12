package io.vertx.api.repository;

import io.vertx.api.entity.Libro;
import io.netty.util.internal.StringUtil;
import io.vertx.core.json.JsonObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class LibroDao {
    private static LibroDao instance;
    protected EntityManager entityManager;

    public static LibroDao getInstance() {
        if (instance == null) {
            instance = new LibroDao();
        }

        return instance;
    }

    private LibroDao() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }

    public Libro getById(String id_libro) {
        Object result = entityManager.find(Libro.class, id_libro);
        if (result != null) {
            return (Libro) result;
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Libro> findAll() {
        return entityManager.createQuery("FROM " + Libro.class.getName()).getResultList();
    }

    public List<Libro> getByFilter(JsonObject filter) {
        Query query = entityManager.createQuery(sqlFilter(filter));
        parametersFilter(filter, query);
        List<Libro> result = query.getResultList();

        return result;
    }

    private String sqlFilter(JsonObject filter) {
        String sqlQuery = "SELECT l FROM Libro l";
        String preParameter = " WHERE";
        String sqlParameter = "";

        if (!StringUtil.isNullOrEmpty(filter.getString("titulo"))) {
            sqlParameter += preParameter + " upper(l.titulo) LIKE upper(:titulo)";
            preParameter = " OR";
        }

        if (!StringUtil.isNullOrEmpty(filter.getString("autor"))) {
            sqlParameter += preParameter + " upper(l.autor) LIKE upper(:autor)";
            preParameter = " OR";
        }

        if (!StringUtil.isNullOrEmpty(filter.getString("edicion"))) {
            sqlParameter += preParameter + " upper(l.edicion) LIKE upper(:edicion)";
            preParameter = " OR";
        }

        return sqlQuery + sqlParameter;
    }

    private void parametersFilter(JsonObject filter, Query query) {
        if (!StringUtil.isNullOrEmpty(filter.getString("titulo"))) {
            String likeNameParam = "%" + filter.getString("titulo") + "%";
            query.setParameter("titulo", likeNameParam);
        }

        if (!StringUtil.isNullOrEmpty(filter.getString("autor"))) {
            String likeNameParam = "%" + filter.getString("autor") + "%";
            query.setParameter("autor", likeNameParam);
        }

        if (!StringUtil.isNullOrEmpty(filter.getString("edicion"))) {
            String likeNameParam = "%" + filter.getString("edicion") + "%";
            query.setParameter("edicion", likeNameParam);
        }

    }

    public void persist(Libro libro) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(libro);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(Libro libro) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(libro);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void remove(Libro libro) {
        try {
            entityManager.getTransaction().begin();
            libro = entityManager.find(Libro.class, libro.getId_libro());
            entityManager.remove(libro);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void removeById(String id_libro) {
        try {
            Libro libro = getById(id_libro);
            remove(libro);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
