package duoc.sistema.cine.objetos_perdidos_service.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjetoPerdidoRequest {

    @NotBlank(message = "El objeto perdido es obligatorio")
    private String objeto;

    @NotNull(message = "El ID de la funcion es obligatorio")
    private Long idFuncion;
}
