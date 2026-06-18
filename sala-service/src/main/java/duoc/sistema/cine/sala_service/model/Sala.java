package duoc.sistema.cine.sala_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "salas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Sala{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nro_sala")
    private Integer nro_sala;

    @Column(name="cantidad_asientos")
    private Integer cantidad_asientos;

}
