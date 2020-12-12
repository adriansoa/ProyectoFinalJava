/**
 * Created by tiago on 07/10/2017.
 */
package io.github.tiagorgt.vertx.api;

import io.github.tiagorgt.vertx.api.entity.*;
import io.github.tiagorgt.vertx.api.service.*;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class Verticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> fut) {
        Router router = Router.router(vertx); // <1>
        // CORS support
        Set<String> allowHeaders = new HashSet<>();
        allowHeaders.add("x-requested-with");
        allowHeaders.add("Access-Control-Allow-Origin");
        allowHeaders.add("origin");
        allowHeaders.add("Content-Type");
        allowHeaders.add("accept");
        Set<HttpMethod> allowMethods = new HashSet<>();
        allowMethods.add(HttpMethod.GET);
        allowMethods.add(HttpMethod.POST);
        allowMethods.add(HttpMethod.DELETE);
        allowMethods.add(HttpMethod.PUT);

        router.route().handler(CorsHandler.create("*") // <2>
                .allowedHeaders(allowHeaders)
                .allowedMethods(allowMethods));
        router.route().handler(BodyHandler.create()); // <3>

        // routes
        router.get("/position").handler(this::getPositions);
        router.get("/user").handler(this::getUsers);
        router.get("/libro").handler(this::getLibro);
        router.get("/materia").handler(this::getMateria);
        router.get("/autor").handler(this::getAutor);
        router.get("/tema").handler(this::getTema);
        router.get("/facultad").handler(this::getFacultad);
        router.get("/user/:id").handler(this::getById);
        router.post("/user").handler(this::save);
        router.post("/libro").handler(this::save1);
        router.post("/materia").handler(this::save2);
        router.post("/autor").handler(this::save3);
        router.post("/tema").handler(this::save4);
        router.post("/facultad").handler(this::save5);
        router.put("/user").handler(this::update);
        router.put("/libro").handler(this::update1);
        router.put("/materia").handler(this::update2);
        router.put("/autor").handler(this::update3);
        router.put("/tema").handler(this::update4);
        router.put("/facultad").handler(this::update5);
        router.delete("/user/:id").handler(this::remove);
        router.delete("/libro/:id").handler(this::remove1);
        router.delete("/materia/:id").handler(this::remove2);
        router.delete("/autor/:id").handler(this::remove3);
        router.delete("/tema/:id").handler(this::remove4);
        router.delete("/facultad/:id").handler(this::remove5);
        router.post("/user/filter").handler(this::getUsersByFilter);

        vertx.createHttpServer() // <4>
                .requestHandler(router::accept)
                .listen(8081, "0.0.0.0", result -> {
                    if (result.succeeded())
                        fut.complete();
                    else
                        fut.fail(result.cause());
                });
    }

    PositionService positionService = new PositionService();
    UserService userService = new UserService();
    LibroService libroService = new LibroService();
    MateriaService materiaService = new MateriaService();
    AutorService autorService = new AutorService();
    TemaService temaService = new TemaService();
    FacultadService facultadService = new FacultadService();

    private void getPositions(RoutingContext context) {
        positionService.list(ar -> {
            if (ar.succeeded()) {
                sendSuccess(Json.encodePrettily(ar.result()), context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void getUsers(RoutingContext context) {
        userService.list(ar -> {
            if (ar.succeeded()) {
                sendSuccess(Json.encodePrettily(ar.result()), context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void getLibro(RoutingContext context) {
        libroService.list(ar -> {
            if (ar.succeeded()) {
                sendSuccess(Json.encodePrettily(ar.result()), context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void getMateria(RoutingContext context) {
        materiaService.list(ar -> {
            if (ar.succeeded()) {
                sendSuccess(Json.encodePrettily(ar.result()), context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void getAutor(RoutingContext context) {
        autorService.list(ar -> {
            if (ar.succeeded()) {
                sendSuccess(Json.encodePrettily(ar.result()), context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void getTema(RoutingContext context) {
        temaService.list(ar -> {
            if (ar.succeeded()) {
                sendSuccess(Json.encodePrettily(ar.result()), context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void getFacultad(RoutingContext context) {
        facultadService.list(ar -> {
            if (ar.succeeded()) {
                sendSuccess(Json.encodePrettily(ar.result()), context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void getUsersByFilter(RoutingContext context){
        userService.getByFilter(context.getBodyAsJson(), ar -> {
            if (ar.succeeded()) {
                sendSuccess(Json.encodePrettily(ar.result()), context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void getById(RoutingContext context) {
        userService.getById(context.request().getParam("id"), ar -> {
            if (ar.succeeded()) {
                if (ar.result() != null){
                    sendSuccess(Json.encodePrettily(ar.result()), context.response());
                } else {
                    sendSuccess(context.response());
                }
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void save(RoutingContext context) {
        userService.save(Json.decodeValue(context.getBodyAsString(), User.class), ar -> {
            if (ar.succeeded()) {
                sendSuccess(context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void save1(RoutingContext context) {
        libroService.save(Json.decodeValue(context.getBodyAsString(), Libro.class), ar -> {
            if (ar.succeeded()) {
                sendSuccess(context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void save2(RoutingContext context) {
        materiaService.save(Json.decodeValue(context.getBodyAsString(), Materia.class), ar -> {
            if (ar.succeeded()) {
                sendSuccess(context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void save3(RoutingContext context) {
        autorService.save(Json.decodeValue(context.getBodyAsString(), Autor.class), ar -> {
            if (ar.succeeded()) {
                sendSuccess(context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void save4(RoutingContext context) {
        temaService.save(Json.decodeValue(context.getBodyAsString(), Tema.class), ar -> {
            if (ar.succeeded()) {
                sendSuccess(context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void save5(RoutingContext context) {
        facultadService.save(Json.decodeValue(context.getBodyAsString(), Facultad.class), ar -> {
            if (ar.succeeded()) {
                sendSuccess(context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void update(RoutingContext context) {
        userService.update(Json.decodeValue(context.getBodyAsString(), User.class), ar -> {
            if (ar.succeeded()) {
                sendSuccess(context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void update1(RoutingContext context) {
        libroService.update(Json.decodeValue(context.getBodyAsString(), Libro.class), ar -> {
            if (ar.succeeded()) {
                sendSuccess(context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void update2(RoutingContext context) {
        materiaService.update(Json.decodeValue(context.getBodyAsString(), Materia.class), ar -> {
            if (ar.succeeded()) {
                sendSuccess(context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void update3(RoutingContext context) {
        autorService.update(Json.decodeValue(context.getBodyAsString(), Autor.class), ar -> {
            if (ar.succeeded()) {
                sendSuccess(context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void update4(RoutingContext context) {
        temaService.update(Json.decodeValue(context.getBodyAsString(), Tema.class), ar -> {
            if (ar.succeeded()) {
                sendSuccess(context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void update5(RoutingContext context) {
        facultadService.update(Json.decodeValue(context.getBodyAsString(), Facultad.class), ar -> {
            if (ar.succeeded()) {
                sendSuccess(context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void remove(RoutingContext context) {
        userService.remove(context.request().getParam("id"), ar -> {
            if (ar.succeeded()) {
                sendSuccess(context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void remove1(RoutingContext context) {
        libroService.remove(context.request().getParam("id"), ar -> {
            if (ar.succeeded()) {
                sendSuccess(context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void remove2(RoutingContext context) {
        materiaService.remove(context.request().getParam("id"), ar -> {
            if (ar.succeeded()) {
                sendSuccess(context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void remove3(RoutingContext context) {
        autorService.remove(context.request().getParam("id"), ar -> {
            if (ar.succeeded()) {
                sendSuccess(context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void remove4(RoutingContext context) {
        temaService.remove(context.request().getParam("id"), ar -> {
            if (ar.succeeded()) {
                sendSuccess(context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void remove5(RoutingContext context) {
        facultadService.remove(context.request().getParam("id"), ar -> {
            if (ar.succeeded()) {
                sendSuccess(context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }


    private void sendError(String errorMessage, HttpServerResponse response) {
        JsonObject jo = new JsonObject();
        jo.put("errorMessage", errorMessage);

        response
                .setStatusCode(500)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(jo));
    }

    private void sendSuccess(HttpServerResponse response) {
        response
                .setStatusCode(200)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end();
    }

    private void sendSuccess(String responseBody, HttpServerResponse response) {
        response
                .setStatusCode(200)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(responseBody);
    }
}
