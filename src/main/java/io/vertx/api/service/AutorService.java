package io.vertx.api.service;

import io.vertx.api.entity.Autor;
import io.vertx.api.repository.AutorDao;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

import java.util.List;


public class AutorService {

    private AutorDao autorDao = AutorDao.getInstance();

    public void list(Handler<AsyncResult<List<Autor>>> handler) {
        Future<List<Autor>> future = Future.future();
        future.setHandler(handler);

        try {
            List<Autor> result = autorDao.findAll();
            future.complete(result);
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }

    public void getByFilter(JsonObject filter, Handler<AsyncResult<List<Autor>>> handler){
        Future<List<Autor>> future = Future.future();
        future.setHandler(handler);

        try {
            List<Autor> result = autorDao.getByFilter(filter);
            future.complete(result);
        } catch (Exception ex){
            future.fail(ex);
        }
    }

    public void getById(String id_autor, Handler<AsyncResult<Autor>> handler) {
        Future<Autor> future = Future.future();
        future.setHandler(handler);

        try {
            Autor result = autorDao.getById(id_autor);
            future.complete(result);
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }

    public void save(Autor newAutor, Handler<AsyncResult<Autor>> handler) {
        Future<Autor> future = Future.future();
        future.setHandler(handler);

        try {
            Autor autor = autorDao.getById(newAutor.getId_autor());

            if (autor != null) {
                future.fail("Autor ya incluido.");
                return;
            }
            autorDao.persist(newAutor);
            future.complete();
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }

    public void update(Autor autor, Handler<AsyncResult<Autor>> handler) {
        Future<Autor> future = Future.future();
        future.setHandler(handler);

        try {
            autorDao.merge(autor);
            future.complete();
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }

    public void remove(String id_autor, Handler<AsyncResult<Autor>> handler) {
        Future<Autor> future = Future.future();
        future.setHandler(handler);

        try {
            autorDao.removeById(id_autor);
            future.complete();
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }

}
