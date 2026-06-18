package duoc.sistema.cine.sala_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SalaRequest {

    @NotNull(message = "La numero de la sala es obligatorio")
    private Integer nro_sala;

    @NotNull(message = "La cantidad de asientos es obligatorio")
    private Integer cantidad_asientos;


}
