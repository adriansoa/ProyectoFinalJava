package io.github.tiagorgt.vertx.api.service;

import io.github.tiagorgt.vertx.api.entity.Libro;
import io.github.tiagorgt.vertx.api.entity.Tema;
import io.github.tiagorgt.vertx.api.entity.User;
import io.github.tiagorgt.vertx.api.repository.LibroDao;
import io.github.tiagorgt.vertx.api.repository.TemaDao;
import io.github.tiagorgt.vertx.api.repository.UserDao;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

import java.util.List;

public class TemaService {
    private TemaDao temaDao = TemaDao.getInstance();

    public void list(Handler<AsyncResult<List<Tema>>> handler) {
        Future<List<Tema>> future = Future.future();
        future.setHandler(handler);

        try {
            List<Tema> result = temaDao.findAll();
            future.complete(result);
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }

    public void getByFilter(JsonObject filter, Handler<AsyncResult<List<Tema>>> handler){
        Future<List<Tema>> future = Future.future();
        future.setHandler(handler);

        try {
            List<Tema> result = temaDao.getByFilter(filter);
            future.complete(result);
        } catch (Exception ex){
            future.fail(ex);
        }
    }

    public void getById(String id_tema, Handler<AsyncResult<Tema>> handler) {
        Future<Tema> future = Future.future();
        future.setHandler(handler);

        try {
            Tema result = temaDao.getById(id_tema);
            future.complete(result);
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }

    public void save(Tema newTema, Handler<AsyncResult<Tema>> handler) {
        Future<Tema> future = Future.future();
        future.setHandler(handler);

        try {
            Tema tema = temaDao.getById(newTema.getId_tema());

            if (tema != null) {
                future.fail("Tema ya incluido.");
                return;
            }
            temaDao.persist(newTema);
            future.complete();
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }

    public void update(Tema tema, Handler<AsyncResult<Tema>> handler) {
        Future<Tema> future = Future.future();
        future.setHandler(handler);

        try {
            temaDao.merge(tema);
            future.complete();
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }

    public void remove(String id_tema, Handler<AsyncResult<Tema>> handler) {
        Future<Tema> future = Future.future();
        future.setHandler(handler);

        try {
            temaDao.removeById(id_tema);
            future.complete();
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }

}
