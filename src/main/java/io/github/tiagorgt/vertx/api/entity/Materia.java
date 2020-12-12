package io.github.tiagorgt.vertx.api.entity;

import org.hibernate.annotations.Columns;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "materia")
public class Materia implements Serializable{
    @Id
    @Column(unique = true, name = "id_materia")
    private int id_materia;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "cant_creditos", nullable = false)
    private String cant_creditos;

    @Column(name = "tipo_materia", nullable = false)
    private String tipo_materia;

    public int getId_materia() {
        return id_materia;
    }

    public void setId_materia(int id_materia) {
        this.id_materia = id_materia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCant_creditos() {
        return cant_creditos;
    }

    public void setCant_creditos(String cant_creditos) {
        this.cant_creditos = cant_creditos;
    }

    public String getTipo_materia() {
        return tipo_materia;
    }

    public void setTipo_materia(String tipo_materia) {
        this.tipo_materia = tipo_materia;
    }
}
