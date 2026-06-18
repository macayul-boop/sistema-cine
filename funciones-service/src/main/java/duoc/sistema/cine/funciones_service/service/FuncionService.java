package duoc.sistema.cine.funciones_service.service;

import duoc.sistema.cine.funciones_service.dto.FuncionRequest;
import duoc.sistema.cine.funciones_service.dto.FuncionResponse;
import duoc.sistema.cine.funciones_service.dto.PeliculaRequest;
import duoc.sistema.cine.funciones_service.exception.FechaInvalidaException;
import duoc.sistema.cine.funciones_service.exception.FuncionNoEncontradaException;
import duoc.sistema.cine.funciones_service.exception.RecursoWebClientNoEncontradoException;
import duoc.sistema.cine.funciones_service.model.Funcion;
import duoc.sistema.cine.funciones_service.repository.FuncionRepository;
import lombok.RequiredArgsConstructor;
import net.datafaker.providers.base.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FuncionService {
    private static final Logger log = LoggerFactory.getLogger(FuncionService.class);

    private final WebClient.Builder webClientBuilder;

    @Value("${services.pelicula.url}")
    private String peliculaServiceUrl;

    @Value("${services.salas.url}")
    private String salasServiceUrl;

    @Autowired
    private FuncionRepository funcionRepository;

    // -------------------------------- METODOS BASICAS --------------------------------
    // Listar todas la funciones
    public List<Funcion> listarFunciones(){
        log.info("Listando todo las funciones");
        return funcionRepository.findAll();
    }

    // Listar una funcion por su id
    public Funcion listarFuncionPorId(Long idFuncion){
        log.info("Listando la funcion con el Id: {}", idFuncion);
        Funcion funcion = funcionRepository.findById(idFuncion)
                .orElseThrow(() -> new FuncionNoEncontradaException("Funcion no encontrada"));
        return funcion;
    }

    // Crear una funcion
    public Funcion crearFuncion(FuncionRequest request){
        // Validacion por si acaso
        if(request.getFechaHoraInicio().isBefore(LocalDateTime.now())){
            throw new FechaInvalidaException("Fecha y hora de inicio invalida");
        }

        if(request.getFechaHoraTermino().isBefore(request.getFechaHoraInicio())){
            throw new FechaInvalidaException("Fecha y hora de termino no puede ser antes de la fecha de inicio");
        }

        // valida que la pelicula si exista
        Boolean existePelicula = webClientBuilder.build().get()
                .uri(peliculaServiceUrl + "/api/peliculas/verificar/{idPelicula}", request.getIdPelicula())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorResponse ->
                        Mono.error(new RecursoWebClientNoEncontradoException("Recurso de webClien (pelicula) no encontrado"))
                )
                .bodyToMono(Boolean.class)
                .block();

        // Validar que la sala exista
        Boolean existeSala = webClientBuilder.build().get()
                .uri(salasServiceUrl + "/api/salas/verificar/{idSala}", request.getIdSala())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorResponse ->
                        Mono.error(new RecursoWebClientNoEncontradoException("Recurso de webClien (salas) no encontrado"))
                )
                .bodyToMono(Boolean.class)
                .block();


        log.info("Creando una funcion");
        Funcion funcion = new Funcion();
        funcion.setFechaHoraInicio(request.getFechaHoraInicio());
        funcion.setFechaHoraTermino(request.getFechaHoraTermino());
        funcion.setIdPelicula(request.getIdPelicula());
        funcion.setIdSala(request.getIdSala());
        funcionRepository.save(funcion);
        return funcion;

    }

    // Editar una funcion
    public Funcion editarFuncion(Long idFuncion, FuncionRequest request){
        Funcion funcion = funcionRepository.findById(idFuncion)
                .orElseThrow(() -> new FuncionNoEncontradaException("Funcion no encontrada"));

        // Valida que la pelicula exista
        Boolean existePelicula = webClientBuilder.build().get()
                .uri(peliculaServiceUrl + "/api/peliculas/verificar/{idPelicula}", request.getIdPelicula())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorResponse ->
                        Mono.error(new RecursoWebClientNoEncontradoException("Recurso de webClien no encontrado"))
                )
                .bodyToMono(Boolean.class)
                .block();

        // Validar que la sala exista
        Boolean existeSala = webClientBuilder.build().get()
                .uri(salasServiceUrl + "/api/salas/verificar/{idSala}", request.getIdSala())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorResponse ->
                        Mono.error(new RecursoWebClientNoEncontradoException("Recurso de webClien (salas) no encontrado"))
                )
                .bodyToMono(Boolean.class)
                .block();

        log.info("Actualizando una funcion con ID: {}", idFuncion);
        funcion.setFechaHoraInicio(request.getFechaHoraInicio());
        funcion.setFechaHoraTermino(request.getFechaHoraTermino());
        funcion.setIdPelicula(request.getIdPelicula());
        funcion.setIdSala(request.getIdSala());
        funcionRepository.save(funcion);
        return funcion;
    }

    // Eliminar una funcion
    public void eliminarFuncion(Long idFuncion){
        log.info("Eliminando una funcion con ID: {}", idFuncion);
        if(!funcionRepository.existsById(idFuncion)){
            throw new FuncionNoEncontradaException("Funcion no encontrada");
        }
        funcionRepository.deleteById(idFuncion);
    }

    // Listar funciones por Id de la pelicula
    public List<Funcion> listarFuncionesPorIdPelicula(Long idPelicula){
        log.info("Listando las funcione con el Id pelicula: {}", idPelicula);
        return funcionRepository.findByIdPelicula(idPelicula);
    }

    // -------------------------------- METODOS EXTRAS --------------------------------
    // Verificar que una funcion existe
    public Boolean existeFuncion(Long idFuncion){
        log.info("Verificando que exista una funcion con ID: {}", idFuncion);
        Boolean existe = funcionRepository.existsById(idFuncion);
        if(!existe){
            throw new FuncionNoEncontradaException("Funcion no encontrada");
        }
        return existe;
    }

    // Obtner informacion de funcion y pelicula
    public FuncionResponse obtenerInformacionFuncion(Long idFuncion){
        Funcion funcion = listarFuncionPorId(idFuncion);

        PeliculaRequest pelicula = webClientBuilder.build().get()
                .uri(peliculaServiceUrl + "/api/peliculas/informacion/{idPelicula}", funcion.getIdPelicula())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorResponse ->
                        Mono.error(new RecursoWebClientNoEncontradoException("Recurso de webClien no encontrado"))
                )
                .bodyToMono(PeliculaRequest.class)
                .block();

        FuncionResponse respuesta = new FuncionResponse();
        respuesta.setIdSala(funcion.getIdSala());
        respuesta.setFechaEntrada(funcion.getFechaHoraInicio());
        respuesta.setFechaTermino(funcion.getFechaHoraTermino());
        respuesta.setNombrePelicula(pelicula.getNombre());
        respuesta.setDuracion(pelicula.getDuracion());
        return respuesta;
    }

}
