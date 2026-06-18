package duoc.sistema.cine.objetos_perdidos_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "objetos_perdidos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ObjetoPerdido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "objeto")
    private String objeto;

    @Column(name = "id_funcion")
    private Long idFuncion;
}
