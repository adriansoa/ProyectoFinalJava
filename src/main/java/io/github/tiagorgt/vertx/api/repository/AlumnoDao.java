package io.github.tiagorgt.vertx.api.repository;


import io.github.tiagorgt.vertx.api.entity.Alumno;
import io.netty.util.internal.StringUtil;
import io.vertx.core.json.JsonObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;



public class AlumnoDao {

    private static AlumnoDao instance;
    protected EntityManager entityManager;

    public static AlumnoDao getInstance() {
        if (instance == null) {
            instance = new AlumnoDao();
        }

        return instance;
    }


    private AlumnoDao() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }

    public Alumno getById(String id_alumno) {
        Object result = entityManager.find(Alumno.class,id_alumno );
        if (result != null) {
            return (Alumno) result;
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Alumno> findAll() {
        return entityManager.createQuery("FROM " + Alumno.class.getName()).getResultList();
    }

    public List<Alumno> getByFilter(JsonObject filter) {
        Query query = entityManager.createQuery(sqlFilter(filter));
        parametersFilter(filter, query);
        List<Alumno> result = query.getResultList();

        return result;
    }

    private String sqlFilter(JsonObject filter) {
        String sqlQuery = "SELECT l FROM Alumno l";
        String preParameter = " WHERE";
        String sqlParameter = "";

        if (!StringUtil.isNullOrEmpty(filter.getString("nro_documento"))) {
            sqlParameter += preParameter + " upper(l.nro_documento) LIKE upper(:nro_documento)";
            preParameter = " OR";
        }

        if (!StringUtil.isNullOrEmpty(filter.getString("tipo_documento"))) {
            sqlParameter += preParameter + " upper(l.tipo_documento) LIKE upper(:tipo_documento)";
            preParameter = " OR";
        }

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

        if (!StringUtil.isNullOrEmpty(filter.getString("promocion"))) {
            sqlParameter += preParameter + " upper(l.promocion) LIKE upper(:promocion)";
            preParameter = " OR";
        }

        return sqlQuery + sqlParameter;
    }


    private void parametersFilter(JsonObject filter, Query query) {
        if (!StringUtil.isNullOrEmpty(filter.getString("nro_documento"))) {
            String likeNameParam = "%" + filter.getString("nro_documento") + "%";
            query.setParameter("nro_documento", likeNameParam);
        }

        if (!StringUtil.isNullOrEmpty(filter.getString("tipo_documento"))) {
            String likeNameParam = "%" + filter.getString("tipo_documento") + "%";
            query.setParameter("tipo_documento", likeNameParam);
        }

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

        if (!StringUtil.isNullOrEmpty(filter.getString("promocion"))) {
            String likeNameParam = "%" + filter.getString("promocion") + "%";
            query.setParameter("promocion", likeNameParam);
        }

    }

    public void persist(Alumno alumno) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(alumno);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(Alumno alumno) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(alumno);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void remove(Alumno alumno) {
        try {
            entityManager.getTransaction().begin();
            alumno = entityManager.find(Alumno.class, alumno.getId_alumno());
            entityManager.remove(alumno);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void removeById(String id_alumno) {
        try {
            Alumno alumno = getById(id_alumno);
            remove(alumno);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
