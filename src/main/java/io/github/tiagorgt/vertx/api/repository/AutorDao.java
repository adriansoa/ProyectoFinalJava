package io.github.tiagorgt.vertx.api.repository;

import io.github.tiagorgt.vertx.api.entity.Autor;
import io.netty.util.internal.StringUtil;
import io.vertx.core.json.JsonObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;


public class AutorDao {

    private static AutorDao instance;
    protected EntityManager entityManager;

    public static AutorDao getInstance() {
        if (instance == null) {
            instance = new AutorDao();
        }

        return instance;
}
    private AutorDao() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }

    public Autor getById(String id_autor) {
        Object result = entityManager.find(Autor.class,id_autor );
        if (result != null) {
            return (Autor) result;
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Autor> findAll() {
        return entityManager.createQuery("FROM " + Autor.class.getName()).getResultList();
    }

    public List<Autor> getByFilter(JsonObject filter) {
        Query query = entityManager.createQuery(sqlFilter(filter));
        parametersFilter(filter, query);
        List<Autor> result = query.getResultList();

        return result;
    }

    private String sqlFilter(JsonObject filter) {
        String sqlQuery = "SELECT l FROM Autor l";
        String preParameter = " WHERE";
        String sqlParameter = "";

        if (!StringUtil.isNullOrEmpty(filter.getString("nombre"))) {
            sqlParameter += preParameter + " upper(l.nombre) LIKE upper(:nombre)";
            preParameter = " OR";
        }

        if (!StringUtil.isNullOrEmpty(filter.getString("telefono"))) {
            sqlParameter += preParameter + " upper(l.telefono) LIKE upper(:telefono)";
            preParameter = " OR";
        }

        if (!StringUtil.isNullOrEmpty(filter.getString("email"))) {
            sqlParameter += preParameter + " upper(l.email) LIKE upper(:email)";
            preParameter = " OR";
        }

        return sqlQuery + sqlParameter;
    }

    private void parametersFilter(JsonObject filter, Query query) {
        if (!StringUtil.isNullOrEmpty(filter.getString("nombre"))) {
            String likeNameParam = "%" + filter.getString("nombre") + "%";
            query.setParameter("nombre", likeNameParam);
        }

        if (!StringUtil.isNullOrEmpty(filter.getString("telefono"))) {
            String likeNameParam = "%" + filter.getString("telefono") + "%";
            query.setParameter("telefono", likeNameParam);
        }

        if (!StringUtil.isNullOrEmpty(filter.getString("email"))) {
            String likeNameParam = "%" + filter.getString("email") + "%";
            query.setParameter("email", likeNameParam);
        }

    }

    public void persist(Autor autor) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(autor);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(Autor autor) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(autor);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void remove(Autor autor) {
        try {
            entityManager.getTransaction().begin();
            autor = entityManager.find(Autor.class, autor.getId_autor());
            entityManager.remove(autor);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void removeById(String id_autor) {
        try {
            Autor autor = getById(id_autor);
            remove(autor);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


