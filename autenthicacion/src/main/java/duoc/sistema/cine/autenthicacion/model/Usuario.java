package duoc.sistema.cine.autenthicacion.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Usuario {
    private Long id;
    private String username;
    private String password;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String email;
    private String tipoUsuario;
}
