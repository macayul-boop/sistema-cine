package duoc.sistema.cine.autenthicacion.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse {
    private LocalDateTime fecha;
    private int estado;
    private String mensaje;
    private String ruta;
    private Map<String, String> errores;

    public ErrorResponse() {}

    public LocalDateTime getFecha() {return fecha;}
    public void setFecha(LocalDateTime fecha) {this.fecha = fecha;}

    public int getEstado() {return estado;}
    public void setEstado(int estado) {this.estado = estado;}

    public String getMensaje() {return mensaje;}
    public void setMensaje(String mensaje) {this.mensaje = mensaje;}

    public String getRuta() {return ruta;}
    public void setRuta(String ruta) {this.ruta = ruta;}

    public Map<String, String> getErrores() {return errores;}
    public void setErrores(Map<String, String> errores) {this.errores = errores;}
}