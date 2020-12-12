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


}
