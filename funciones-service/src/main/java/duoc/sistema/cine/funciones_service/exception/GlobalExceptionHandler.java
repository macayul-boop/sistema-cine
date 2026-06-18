package duoc.sistema.cine.funciones_service.exception;

import duoc.sistema.cine.funciones_service.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Errore gerenales de las validaciones
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion(MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.warn("Error de validación en la ruta {}", request.getRequestURI());
        Map<String, String> errores = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }
        ErrorResponse respuesta = new ErrorResponse();
        respuesta.setFecha(LocalDateTime.now());
        respuesta.setEstado(HttpStatus.BAD_REQUEST.value());
        respuesta.setError("Bad Request");
        respuesta.setMensaje("Los datos enviados no son válidos");
        respuesta.setRuta(request.getRequestURI());
        respuesta.setErrores(errores);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    // Funcion no encontrado
    @ExceptionHandler(FuncionNoEncontradaException.class)
    public ResponseEntity<ErrorResponse> funcionNoEncontrada(FuncionNoEncontradaException ex, HttpServletRequest request){
        log.warn("Error funcion no encontrado en la ruta {}", request.getRequestURI());
        ErrorResponse respuesta = new ErrorResponse();
        respuesta.setFecha(LocalDateTime.now());
        respuesta.setEstado(HttpStatus.NOT_FOUND.value());
        respuesta.setError("Not Found");
        respuesta.setMensaje(ex.getMessage());
        respuesta.setRuta(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }

    // Fecha invalida
    @ExceptionHandler(FechaInvalidaException.class)
    public ResponseEntity<ErrorResponse> fechaInvalida(FechaInvalidaException ex, HttpServletRequest request){
        log.warn("Error en los datos de la fecha en la ruta {}", request.getRequestURI());
        ErrorResponse respuesta = new ErrorResponse();
        respuesta.setFecha(LocalDateTime.now());
        respuesta.setEstado(HttpStatus.BAD_REQUEST.value());
        respuesta.setError("Bad Request");
        respuesta.setMensaje(ex.getMessage());
        respuesta.setRuta(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    // Algun error de los WebClient
    @ExceptionHandler(RecursoWebClientNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> erroesWebClient(RecursoWebClientNoEncontradoException ex, HttpServletRequest request){
        log.warn("Error en los datos enviado por el WebClient {}", request.getRequestURI());
        ErrorResponse respuesta = new ErrorResponse();
        respuesta.setFecha(LocalDateTime.now());
        respuesta.setEstado(HttpStatus.NOT_FOUND.value());
        respuesta.setError("Not found");
        respuesta.setMensaje(ex.getMessage());
        respuesta.setRuta(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }
}
