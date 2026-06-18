package duoc.sistema.cine.funciones_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "funcion")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Funcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_hora_inicio")
    private LocalDateTime fechaHoraInicio;

    @Column(name = "fecha_hora_termino")
    private LocalDateTime fechaHoraTermino;

    @Column(name = "id_pelicula")
    private Long idPelicula;

    @Column(name = "id_sala")
    private Long idSala;
}
