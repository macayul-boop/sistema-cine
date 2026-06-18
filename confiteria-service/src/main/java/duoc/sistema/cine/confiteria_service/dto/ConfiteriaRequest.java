package duoc.sistema.cine.confiteria_service.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfiteriaRequest {

    @NotNull(message = "La fecha y hora es obligatoria")
    private LocalDateTime fechaHora;

    @NotNull(message = "El Id del producto es obligatoria")
    private Long idProducto;

    @NotNull(message = "El Id del usuario es obligatoria")
    private Long idUsuario;

    @NotNull(message = "La cantidad es obligatoria")
    private Long cantidad;
}
