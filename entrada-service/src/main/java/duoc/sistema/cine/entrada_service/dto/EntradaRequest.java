package duoc.sistema.cine.entrada_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntradaRequest {
    @NotNull(message = "El Id del usuario es obligatorio")
    private Long idUsuario;

    @NotNull(message = "El Id de la funcion es obligatorio")
    private Long idFuncion;

    @NotNull(message = "La cantidad de entradas es obligatorio")
    @Min(value = 1, message = "La cantidad mínima de entradas es 1")
    private Long cantidad;

    @NotNull(message = "El valor total es obligatorio")
    @Positive(message = "El valor debe ser un número positivo")
    private Long valor;
}
