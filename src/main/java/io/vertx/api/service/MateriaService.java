package io.vertx.api.service;


import io.vertx.api.entity.Materia;
import io.vertx.api.repository.MateriaDao;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

import java.util.List;

public class MateriaService {
    private MateriaDao materiaDao = MateriaDao.getInstance();

    public void list(Handler<AsyncResult<List<Materia>>> handler) {
        Future<List<Materia>> future = Future.future();
        future.setHandler(handler);

        try {
            List<Materia> result = materiaDao.findAll();
            future.complete(result);
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }

    public void getByFilter(JsonObject filter, Handler<AsyncResult<List<Materia>>> handler){
        Future<List<Materia>> future = Future.future();
        future.setHandler(handler);

        try {
            List<Materia> result = materiaDao.getByFilter(filter);
            future.complete(result);
        } catch (Exception ex){
            future.fail(ex);
        }
    }

    public void getById(String id_materia, Handler<AsyncResult<Materia>> handler) {
        Future<Materia> future = Future.future();
        future.setHandler(handler);

        try {
            Materia result = materiaDao.getById(id_materia);
            future.complete(result);
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }

    public void save(Materia newMateria, Handler<AsyncResult<Materia>> handler) {
        Future<Materia> future = Future.future();
        future.setHandler(handler);

        try {
            Materia materia = materiaDao.getById(newMateria.getId_materia());

            if (materia != null) {
                future.fail("Materia ya incluido.");
                return;
            }
            materiaDao.persist(newMateria);
            future.complete();
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }

    public void update(Materia materia, Handler<AsyncResult<Materia>> handler) {
        Future<Materia> future = Future.future();
        future.setHandler(handler);

        try {
            materiaDao.merge(materia);
            future.complete();
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }

    public void remove(String id_materia, Handler<AsyncResult<Materia>> handler) {
        Future<Materia> future = Future.future();
        future.setHandler(handler);

        try {
            materiaDao.removeById(id_materia);
            future.complete();
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }

}
