package io.vertx.api.entity;

import org.hibernate.annotations.Columns;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tema")
public class Tema implements Serializable {

    @Id
    @Column(unique = true, name = "id_tema")
    private String id_tema;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    public String getId_tema() {
        return id_tema;
    }

    public void setId_tema(String id_tema) {
        this.id_tema = id_tema;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
