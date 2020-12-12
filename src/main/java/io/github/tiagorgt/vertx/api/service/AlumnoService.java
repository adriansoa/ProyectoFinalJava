package io.github.tiagorgt.vertx.api.service;


import io.github.tiagorgt.vertx.api.entity.Alumno;
import io.github.tiagorgt.vertx.api.repository.AlumnoDao;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import sun.security.krb5.internal.rcache.AuthList;

import java.util.List;


public class AlumnoService {

    private AlumnoDao alumnoDao = AlumnoDao.getInstance();

    public void list(Handler<AsyncResult<List<Alumno>>> handler) {
        Future<List<Alumno>> future = Future.future();
        future.setHandler(handler);

        try {
            List<Alumno> result = alumnoDao.findAll();
            future.complete(result);
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }

    public void getByFilter(JsonObject filter, Handler<AsyncResult<List<Alumno>>> handler){
        Future<List<Alumno>> future = Future.future();
        future.setHandler(handler);

        try {
            List<Alumno> result = alumnoDao.getByFilter(filter);
            future.complete(result);
        } catch (Exception ex){
            future.fail(ex);
        }
    }

    public void getById(String id_alumno, Handler<AsyncResult<Alumno>> handler) {
        Future<Alumno> future = Future.future();
        future.setHandler(handler);

        try {
            Alumno result = alumnoDao.getById(id_alumno);
            future.complete(result);
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }

    public void save(Alumno newAlumno, Handler<AsyncResult<Alumno>> handler) {
        Future<Alumno> future = Future.future();
        future.setHandler(handler);

        try {
            Alumno alumno = alumnoDao.getById(newAlumno.getId_alumno());

            if (alumno != null) {
                future.fail("Alumno ya incluido.");
                return;
            }
            alumnoDao.persist(newAlumno);
            future.complete();
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }

    public void update(Alumno alumno, Handler<AsyncResult<Alumno>> handler) {
        Future<Alumno> future = Future.future();
        future.setHandler(handler);

        try {
            alumnoDao.merge(alumno);
            future.complete();
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }

    public void remove(String id_alumno, Handler<AsyncResult<Alumno>> handler) {
        Future<Alumno> future = Future.future();
        future.setHandler(handler);

        try {
            alumnoDao.removeById(id_alumno);
            future.complete();
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }


}
