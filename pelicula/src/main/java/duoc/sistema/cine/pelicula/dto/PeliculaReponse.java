package duoc.sistema.cine.pelicula.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeliculaReponse {
    private String nombre;
    private Integer duracion;
}
