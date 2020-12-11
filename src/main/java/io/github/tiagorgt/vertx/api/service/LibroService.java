package io.github.tiagorgt.vertx.api.service;

import io.github.tiagorgt.vertx.api.entity.Libro;
import io.github.tiagorgt.vertx.api.entity.User;
import io.github.tiagorgt.vertx.api.repository.LibroDao;
import io.github.tiagorgt.vertx.api.repository.UserDao;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

import java.util.List;

public class LibroService {
    private LibroDao libroDao = LibroDao.getInstance();

    public void list(Handler<AsyncResult<List<Libro>>> handler) {
        Future<List<Libro>> future = Future.future();
        future.setHandler(handler);

        try {
            List<Libro> result = libroDao.findAll();
            future.complete(result);
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }

    public void getByFilter(JsonObject filter, Handler<AsyncResult<List<Libro>>> handler){
        Future<List<Libro>> future = Future.future();
        future.setHandler(handler);

        try {
            List<Libro> result = libroDao.getByFilter(filter);
            future.complete(result);
        } catch (Exception ex){
            future.fail(ex);
        }
    }

    public void getById(String id_libro, Handler<AsyncResult<Libro>> handler) {
        Future<Libro> future = Future.future();
        future.setHandler(handler);

        try {
            Libro result = libroDao.getById(id_libro);
            future.complete(result);
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }

    public void save(Libro newLibro, Handler<AsyncResult<Libro>> handler) {
        Future<Libro> future = Future.future();
        future.setHandler(handler);

        try {
            Libro libro = libroDao.getById(newLibro.getId_libro());

            if (libro != null) {
                future.fail("Libro ya incluido.");
                return;
            }
            libroDao.persist(newLibro);
            future.complete();
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }

    public void update(Libro libro, Handler<AsyncResult<Libro>> handler) {
        Future<Libro> future = Future.future();
        future.setHandler(handler);

        try {
            libroDao.merge(libro);
            future.complete();
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }

    public void remove(String id_libro, Handler<AsyncResult<Libro>> handler) {
        Future<Libro> future = Future.future();
        future.setHandler(handler);

        try {
            libroDao.removeById(id_libro);
            future.complete();
        } catch (Throwable ex) {
            future.fail(ex);
        }
    }
}
