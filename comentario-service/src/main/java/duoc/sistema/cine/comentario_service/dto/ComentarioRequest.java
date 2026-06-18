package duoc.sistema.cine.comentario_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioRequest {

    @NotNull(message = "La fecha y hora es obligatoria")
    private LocalDateTime fechaHora;

    @NotBlank(message = "El comentario es obligatorio")
    private String comentario;

    @NotNull(message = "La ID de la pelicula es obligatorio")
    private Long idPelicula;

    @NotNull(message = "La ID del usuario es obligatorio")
    private Long idusuario;
}
