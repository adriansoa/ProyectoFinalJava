package io.vertx.api.service;


import io.vertx.api.entity.Facultad;
import io.vertx.api.repository.FacultadDao;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

import java.util.List;

public class FacultadService {

    private FacultadDao facultadDao = FacultadDao.getInstance();

    public void list(Handler<AsyncResult<List<Facultad>>> handler) {
        Future<List<Facultad>> future = Future.future();
        future.setHandler(handler);

        try {
            List<Facultad> result = facultadDao.findAll();
            future.complete(result);
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }

    public void getByFilter(JsonObject filter, Handler<AsyncResult<List<Facultad>>> handler){
        Future<List<Facultad>> future = Future.future();
        future.setHandler(handler);

        try {
            List<Facultad> result = facultadDao.getByFilter(filter);
            future.complete(result);
        } catch (Exception ex){
            future.fail(ex);
        }
    }

    public void getById(String id_facultad, Handler<AsyncResult<Facultad>> handler) {
        Future<Facultad> future = Future.future();
        future.setHandler(handler);

        try {
            Facultad result = facultadDao.getById(id_facultad);
            future.complete(result);
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }

    public void save(Facultad newFacultad, Handler<AsyncResult<Facultad>> handler) {
    Future<Facultad> future = Future.future();
        future.setHandler(handler);

        try {
        Facultad facultad = facultadDao.getById(newFacultad.getId_facultad());

        if (facultad != null) {
            future.fail("Facultad ya incluida.");
            return;
        }
        facultadDao.persist(newFacultad);
        future.complete();
    } catch (Throwable ex) {
        future.fail(ex);
    }
    }

    public void update(Facultad facultad, Handler<AsyncResult<Facultad>> handler) {
        Future<Facultad> future = Future.future();
        future.setHandler(handler);

        try {
            facultadDao.merge(facultad);
            future.complete();
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }

    public void remove(String id_facultad, Handler<AsyncResult<Facultad>> handler) {
        Future<Facultad> future = Future.future();
        future.setHandler(handler);

        try {
            facultadDao.removeById(id_facultad);
            future.complete();
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }
}
