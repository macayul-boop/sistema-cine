package duoc.sistema.cine.pelicula.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="peliculas")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Pelicula {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id_pelicula")
    private Long id;

    @Column(name="nombre_pelicula")
    private String nombre;

    @Column(name="duracion")
    private Integer duracion;

    @Column(name="sinopsis")
    private String sinopsis;

    @Column(name="categoria")
    private String categoria;

}
