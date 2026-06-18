package duoc.sistema.cine.funciones_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionResponse {
    private Long idSala;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaTermino;
    private String nombrePelicula;
    private Integer duracion;
}
