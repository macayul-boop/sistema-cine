package duoc.sistema.cine.confiteria_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "confiteria")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Confiteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "cantidad")
    private Long cantidad;
}
