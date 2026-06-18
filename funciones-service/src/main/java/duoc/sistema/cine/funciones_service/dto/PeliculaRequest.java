package duoc.sistema.cine.funciones_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeliculaRequest {
    private String nombre;
    private Integer duracion;
}
