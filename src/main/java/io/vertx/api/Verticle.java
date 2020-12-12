
package io.vertx.api;

import io.vertx.api.entity.*;
import io.vertx.api.service.*;
import io.vertx.api.entity.*;
import io.vertx.api.service.*;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;

import java.util.HashSet;
import java.util.Set;

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

        router.get("/user").handler(this::getUsers);
        router.get("/libro").handler(this::getLibro);
        router.get("/materia").handler(this::getMateria);
        router.get("/autor").handler(this::getAutor);
        router.get("/tema").handler(this::getTema);
        router.get("/facultad").handler(this::getFacultad);
        router.get("/alumno").handler(this::getAlumno);
        router.get("/user/:id").handler(this::getById);
        router.get("/libro/:id").handler(this::getById1);
        router.get("/materia/:id").handler(this::getById2);
        router.get("/autor/:id").handler(this::getById3);
        router.get("/tema/:id").handler(this::getById4);
        router.get("/facultad/:id").handler(this::getById5);
        router.get("/alumno/:id").handler(this::getById6);
        router.post("/user").handler(this::save);
        router.post("/libro").handler(this::save1);
        router.post("/materia").handler(this::save2);
        router.post("/autor").handler(this::save3);
        router.post("/tema").handler(this::save4);
        router.post("/facultad").handler(this::save5);
        router.post("/alumno").handler(this::save6);
        router.put("/user").handler(this::update);
        router.put("/libro").handler(this::update1);
        router.put("/materia").handler(this::update2);
        router.put("/autor").handler(this::update3);
        router.put("/tema").handler(this::update4);
        router.put("/facultad").handler(this::update5);
        router.put("/alumno").handler(this::update6);
        router.delete("/user/:id").handler(this::remove);
        router.delete("/libro/:id").handler(this::remove1);
        router.delete("/materia/:id").handler(this::remove2);
        router.delete("/autor/:id").handler(this::remove3);
        router.delete("/tema/:id").handler(this::remove4);
        router.delete("/facultad/:id").handler(this::remove5);
        router.delete("/alumno/:id").handler(this::remove6);
        router.post("/user/filter").handler(this::getUsersByFilter);
        router.post("/libro/filter").handler(this::getLibrosByFilter1);
        router.post("/materia/filter").handler(this::getMateriasByFilter2);
        router.post("/autor/filter").handler(this::getAutoresByFilter3);
        router.post("/tema/filter").handler(this::getTemasByFilter4);
        router.post("/facultad/filter").handler(this::getFacultadesByFilter5);
        router.post("/alumno/filter").handler(this::getAlumnosByFilter6);

        vertx.createHttpServer() // <4>
                .requestHandler(router::accept)
                .listen(8081, "0.0.0.0", result -> {
                    if (result.succeeded())
                        fut.complete();
                    else
                        fut.fail(result.cause());
                });
    }


    UserService userService = new UserService();
    LibroService libroService = new LibroService();
    MateriaService materiaService = new MateriaService();
    AutorService autorService = new AutorService();
    TemaService temaService = new TemaService();
    FacultadService facultadService = new FacultadService();
    AlumnoService alumnoService = new AlumnoService();



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

    private void getAlumno(RoutingContext context) {
        alumnoService.list(ar -> {
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

    private void getLibrosByFilter1(RoutingContext context){
        libroService.getByFilter(context.getBodyAsJson(), ar -> {
            if (ar.succeeded()) {
                sendSuccess(Json.encodePrettily(ar.result()), context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void getMateriasByFilter2(RoutingContext context){
        materiaService.getByFilter(context.getBodyAsJson(), ar -> {
            if (ar.succeeded()) {
                sendSuccess(Json.encodePrettily(ar.result()), context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void getAutoresByFilter3(RoutingContext context){
        autorService.getByFilter(context.getBodyAsJson(), ar -> {
            if (ar.succeeded()) {
                sendSuccess(Json.encodePrettily(ar.result()), context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void getTemasByFilter4(RoutingContext context){
        temaService.getByFilter(context.getBodyAsJson(), ar -> {
            if (ar.succeeded()) {
                sendSuccess(Json.encodePrettily(ar.result()), context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void getFacultadesByFilter5(RoutingContext context){
        facultadService.getByFilter(context.getBodyAsJson(), ar -> {
            if (ar.succeeded()) {
                sendSuccess(Json.encodePrettily(ar.result()), context.response());
            } else {
                sendError(ar.cause().getMessage(), context.response());
            }
        });
    }

    private void getAlumnosByFilter6(RoutingContext context){
        alumnoService.getByFilter(context.getBodyAsJson(), ar -> {
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

    private void getById1(RoutingContext context) {
        libroService.getById(context.request().getParam("id"), ar -> {
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

    private void getById2(RoutingContext context) {
        materiaService.getById(context.request().getParam("id"), ar -> {
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

    private void getById3(RoutingContext context) {
        autorService.getById(context.request().getParam("id"), ar -> {
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

    private void getById4(RoutingContext context) {
        temaService.getById(context.request().getParam("id"), ar -> {
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

    private void getById5(RoutingContext context) {
        facultadService.getById(context.request().getParam("id"), ar -> {
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

    private void getById6(RoutingContext context) {
        alumnoService.getById(context.request().getParam("id"), ar -> {
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

    private void save6(RoutingContext context) {
        alumnoService.save(Json.decodeValue(context.getBodyAsString(), Alumno.class), ar -> {
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

    private void update6(RoutingContext context) {
        alumnoService.update(Json.decodeValue(context.getBodyAsString(), Alumno.class), ar -> {
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

    private void remove6(RoutingContext context) {
        alumnoService.remove(context.request().getParam("id"), ar -> {
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
