package io.github.tiagorgt.vertx.api.entity;
import org.hibernate.annotations.Columns;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "facultad")
public class Facultad implements Serializable{

    @Id
    @Column(unique = true, name = "id_facultad")
    private String id_facultad;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;


    public String getId_facultad() {
        return id_facultad;
    }

    public void setId_facultad(String id_facultad) {
        this.id_facultad = id_facultad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
