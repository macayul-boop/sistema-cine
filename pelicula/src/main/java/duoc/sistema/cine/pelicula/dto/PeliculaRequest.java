package duoc.sistema.cine.pelicula.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PeliculaRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "La duracion es obligatoria")
    @Min(value = 0,message = "El valor minimo  de la duracion en minutos es de 0 minutos")
    @Max(value=600, message = "El valor maximo de la duracion en minutos es de 600 minutos")
    private Integer duracion;

    @NotBlank(message = "La sinopsis es obligatoria")
    private String sinopsis;

    @NotBlank(message = "La categoria es obligatoria")
    private String categoria;
}
