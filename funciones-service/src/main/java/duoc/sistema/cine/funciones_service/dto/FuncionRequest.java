package duoc.sistema.cine.funciones_service.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionRequest {
    @Future(message = "La fecha de inicio debe ser una fecha futura")
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDateTime fechaHoraInicio;

    @Future(message = "La fecha de termino debe ser una fecha futura")
    @NotNull(message = "La fecha de termino es obligatoria")
    private LocalDateTime fechaHoraTermino;

    @NotNull(message = "El id de la pelicula es obligatoria")
    private Long idPelicula;

    @NotNull(message = "El id de la sala es obligatoria")
    private Long idSala;
}
