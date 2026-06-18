package duoc.sistema.cine.confiteria_service.service;

import duoc.sistema.cine.confiteria_service.dto.ConfiteriaRequest;
import duoc.sistema.cine.confiteria_service.exception.ConfiteriaNoEncontradaException;
import duoc.sistema.cine.confiteria_service.exception.RecursoWebClientInvalido;
import duoc.sistema.cine.confiteria_service.exception.RecursoWebClientNoEncontradoException;
import duoc.sistema.cine.confiteria_service.model.Confiteria;
import duoc.sistema.cine.confiteria_service.repository.ConfiteriaRepository;
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
public class ConfiteriaService {
    private static final Logger log = LoggerFactory.getLogger(ConfiteriaService.class);

    private final WebClient.Builder webClientBuilder;

    @Value("${services.inventario.url}")
    private String inventarioServiceUrl;

    @Value("${services.usuario.url}")
    private String usuarioServiceUrl;

    @Autowired
    private ConfiteriaRepository confiteriaRepository;

    // -------------------------------- METODOS BASICAS --------------------------------

    // Buscar todos
    public List<Confiteria> listarTodos(){
        log.info("Listando todo las funciones");
        return confiteriaRepository.findAll();
    }

    // Buscar por un id
    public Confiteria listarPorId(Long idConfiteria){
        log.info("Listando la funcion con el Id: {}", idConfiteria);
        Confiteria confiteria = confiteriaRepository.findById(idConfiteria)
                .orElseThrow(()-> new ConfiteriaNoEncontradaException("La venta de confiteria no fue encontrada"));
        return confiteria;
    }

    // Crear una confiteria
    public Confiteria crearConfiteria(ConfiteriaRequest request){
        validar(request.getIdProducto(),  request.getIdUsuario());

        // validar que la cantidad que queira exista
        Long cantidad = webClientBuilder.build().get()
                .uri(inventarioServiceUrl + "/api/inventario/cantidad/{idInventario}", request.getCantidad())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorResponse ->
                        Mono.error(new RecursoWebClientInvalido("Recurso de webClien (inventario cantidad) fallo"))
                )
                .bodyToMono(Long.class)
                .block();

        if(request.getCantidad() > cantidad){
            throw new RecursoWebClientInvalido("La cantidad de supera la cantidad disponible del producto");
        }

        // Disminuye la cantidad que pidio
        webClientBuilder.build().post()
                .uri(inventarioServiceUrl + "/api/inventario/disminuir/{idInventario}", request.getIdProducto())
                .bodyValue(request.getCantidad())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorResponse ->
                        Mono.error(new RecursoWebClientInvalido("Recurso de webClien (inventario disminucion) fallo"))
                )
                .bodyToMono(Void.class)
                .block();


        Confiteria confiteria = new Confiteria();
        confiteria.setFechaHora(request.getFechaHora());
        confiteria.setIdProducto(request.getIdProducto());
        confiteria.setIdUsuario(request.getIdUsuario());
        confiteria.setCantidad(request.getCantidad());
        confiteriaRepository.save(confiteria);
        return confiteria;
    }

    // Actualizar una confiteria


    // Eliminar una confiteria
    public void eliminarConfiteria(Long idConfiteria){
        log.info("Eliminando confiteria con id: {}", idConfiteria );
        if(!confiteriaRepository.existsById(idConfiteria)){
            throw new ConfiteriaNoEncontradaException("No se encontro la confiteria");
        }
        confiteriaRepository.deleteById(idConfiteria);
    }


    // -------------------------------- METODOS EXTRA --------------------------------

    private void validar(Long idProducto,  Long idUsuario){
        // validar que el producto exista
        Boolean existeProducto = webClientBuilder.build().get()
                .uri(inventarioServiceUrl + "/api/inventario/verificar/{idInventario}", idProducto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorResponse ->
                        Mono.error(new RecursoWebClientNoEncontradoException("Recurso de webClien (inventario) no encontrado"))
                )
                .bodyToMono(Boolean.class)
                .block();


        // validar que el usuario exista
        Boolean existeUsuario = webClientBuilder.build().get()
                .uri(usuarioServiceUrl + "/api/usuario/verificar/{idUsuario}", idUsuario)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorResponse ->
                        Mono.error(new RecursoWebClientNoEncontradoException("Recurso de webClien (usuario) no encontrado"))
                )
                .bodyToMono(Boolean.class)
                .block();
    }

}









