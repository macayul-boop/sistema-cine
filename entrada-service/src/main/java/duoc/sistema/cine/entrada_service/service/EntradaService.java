package duoc.sistema.cine.entrada_service.service;

import duoc.sistema.cine.entrada_service.dto.EntradaRequest;
import duoc.sistema.cine.entrada_service.dto.EntradaResponse;
import duoc.sistema.cine.entrada_service.dto.FuncionDetalle;
import duoc.sistema.cine.entrada_service.exception.EntradaNoEncontradaException;
import duoc.sistema.cine.entrada_service.exception.RecursoNoEncontradoException;
import duoc.sistema.cine.entrada_service.model.Entrada;
import duoc.sistema.cine.entrada_service.repository.EntradaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntradaService {

    private static final Logger log = LoggerFactory.getLogger(EntradaService.class);

    private final WebClient.Builder webClientBuilder;

    @Value("${services.funciones.url}")
    private String funcionServiceUrl;

    @Value("${services.usuario.url}")
    private String usuarioServiceUrl;

    @Autowired
    private EntradaRepository entradaRepository;

    // -------------------------------- METODOS BASICAS --------------------------------

    // Listar todas las entradas relaizadas
    public List<Entrada> listarEntradas(){
        log.info("Listando todas las entradas realizadas");
        return entradaRepository.findAll();
    }

    // Listar entrada por su Id
    public Entrada listarEntradaPorId(Long idEntrada){
        Entrada entrada = entradaRepository.findById(idEntrada)
                .orElseThrow(()-> new EntradaNoEncontradaException("Entrada no encontrada"));
        return entrada;
    }

    // Listar la infromacion de una entrada por su Id
    public EntradaResponse listarEntradaInfoPorId(Long idEntrada){
        log.info("Listando la infromacion de una entrada con el Id: {}", idEntrada);
        Entrada entrada = entradaRepository.findById(idEntrada)
                .orElseThrow(()-> new EntradaNoEncontradaException("Entrada no encontrada"));


        FuncionDetalle detalles = webClientBuilder.build().get()
                .uri(funcionServiceUrl + "/api/funciones/informacion/{idFuncion}", entrada.getIdFuncion())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorResponse ->
                        Mono.error(new RecursoNoEncontradoException("Recurso de webClien (funcion) no encontrado"))
                )
                .bodyToMono(FuncionDetalle.class)
                .block();


        EntradaResponse respuesta = new EntradaResponse();

        respuesta.setIdSala(detalles.getIdSala());
        respuesta.setCantidadEntrada(entrada.getCantidad());
        respuesta.setValor(entrada.getValor());
        respuesta.setFechaEntrada(detalles.getFechaEntrada());
        respuesta.setFechaTermino(detalles.getFechaTermino());
        respuesta.setNombrePelicula(detalles.getNombrePelicula());
        respuesta.setDuracion(detalles.getDuracion());
        return respuesta;
    }

    // Crear una entrada
    public Entrada crearEntrada(EntradaRequest request){
        log.info("Creando una entrada");

        // Validaciones de Usuario y Funcion
        // Que el usuario que ingreso existe
        // Que la funcion que ingreso exista
        validarExisteFuncionUsuario(request.getIdFuncion(), request.getIdUsuario());

        Entrada entrada = new Entrada();
        entrada.setIdUsuario(request.getIdUsuario());
        entrada.setIdFuncion(request.getIdFuncion());
        entrada.setCantidad(request.getCantidad());
        entrada.setValor(request.getValor());
        entradaRepository.save(entrada);
        return entrada;
    }

    // Actulizar una entrada
    public Entrada editarEntrada(Long idEntrada, EntradaRequest request){
        log.info("Actualizando la entrada con el Id: {}", idEntrada);
        Entrada entrada = entradaRepository.findById(idEntrada)
                .orElseThrow(()-> new EntradaNoEncontradaException("Entrada no encontrada"));

        // Validaciones de Usuario y Funcion
        // Que el usuario que ingreso existe
        // Que la funcion que ingreso exista
        validarExisteFuncionUsuario(request.getIdFuncion(), request.getIdUsuario());

        entrada.setIdUsuario(request.getIdUsuario());
        entrada.setIdFuncion(request.getIdFuncion());
        entrada.setCantidad(request.getCantidad());
        entrada.setValor(request.getValor());
        entradaRepository.save(entrada);
        return entrada;
    }

    // Eliminar una entrada
    public void eliminarEntrada(Long idEntrada){
        log.info("Eliminando la entrada con el Id: {}", idEntrada);
        if(!entradaRepository.existsById(idEntrada)){
            throw new EntradaNoEncontradaException("Entrada no encontrada");
        }
        entradaRepository.deleteById(idEntrada);
    }

    // Funcion interna que valide que si exista un usuario o funcion
    private void validarExisteFuncionUsuario(Long idFuncion, Long idUsuario){
        // valida que si existe una funcion
        boolean existeFuncion = webClientBuilder.build().get()
                .uri(funcionServiceUrl + "/api/funciones/verificar/{idFuncion}", idFuncion)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorResponse ->
                        Mono.error(new RecursoNoEncontradoException("Recurso de webClien (funcion) no encontrado"))
                )
                .bodyToMono(Boolean.class)
                .block();


        // valida que si exista un usuario
        boolean existeUsuario = webClientBuilder.build().get()
                .uri(usuarioServiceUrl + "/api/usuario/verificar/{idUsuario}", idUsuario)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorResponse ->
                        Mono.error(new RecursoNoEncontradoException("Recurso de webClien (usuario) no encontrado"))
                )
                .bodyToMono(Boolean.class)
                .block();
    }

}
