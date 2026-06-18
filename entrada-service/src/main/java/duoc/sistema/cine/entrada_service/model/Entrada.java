package duoc.sistema.cine.entrada_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "venta_entrada")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Entrada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "id_funcion")
    private Long idFuncion;

    @Column(name = "cantidad")
    private Long cantidad;

    @Column(name = "valor")
    private Long valor;
}
