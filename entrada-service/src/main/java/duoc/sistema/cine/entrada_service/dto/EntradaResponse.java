package duoc.sistema.cine.entrada_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntradaResponse {
    private Long idSala;
    private Long cantidadEntrada;
    private Long valor;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaTermino;
    private String nombrePelicula;
    private Integer duracion;
}
