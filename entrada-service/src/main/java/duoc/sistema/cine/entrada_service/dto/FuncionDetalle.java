package duoc.sistema.cine.entrada_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionDetalle {
    private Long idSala;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaTermino;
    private String nombrePelicula;
    private Integer duracion;
}
