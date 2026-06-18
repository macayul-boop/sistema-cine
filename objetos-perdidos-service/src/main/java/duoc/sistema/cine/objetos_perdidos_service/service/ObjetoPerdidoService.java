package duoc.sistema.cine.objetos_perdidos_service.service;

import duoc.sistema.cine.objetos_perdidos_service.dto.ObjetoPerdidoRequest;
import duoc.sistema.cine.objetos_perdidos_service.exception.ObjetoPerdidoNoEncontradoException;
import duoc.sistema.cine.objetos_perdidos_service.exception.RecursoWebClientNoEncontradoException;
import duoc.sistema.cine.objetos_perdidos_service.model.ObjetoPerdido;
import duoc.sistema.cine.objetos_perdidos_service.repository.ObjetoPerdidoRepository;
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
public class ObjetoPerdidoService {
    private static final Logger log = LoggerFactory.getLogger(ObjetoPerdidoService.class);

    private final WebClient.Builder webClientBuilder;

    @Value("${services.funciones.url}")
    private String funcionServiceUrl;


    @Autowired
    private ObjetoPerdidoRepository objetoPerdidoRepository;


    // -------------------------------- METODOS BASICAS --------------------------------

    // Listar todos los objetos
    public List<ObjetoPerdido> listarTodos(){
        List<ObjetoPerdido> lista = objetoPerdidoRepository.findAll();
        return lista;
    }

    // Listar un objeto por su Id
    public ObjetoPerdido listarPorId(Long idObjeto){
        ObjetoPerdido objetoPerdido = objetoPerdidoRepository.findById(idObjeto)
                .orElseThrow(()->new ObjetoPerdidoNoEncontradoException("Objeto Perdido no encontrado"));
        return objetoPerdido;
    }

    // Agregar un objeto
    public ObjetoPerdido agregarObjetoPerdido(ObjetoPerdidoRequest request){
        // Validacion de funcion
        verificarFuncion(request.getIdFuncion());

        ObjetoPerdido objetoPerdido = new ObjetoPerdido();
        objetoPerdido.setObjeto(request.getObjeto());
        objetoPerdido.setIdFuncion(request.getIdFuncion());
        objetoPerdidoRepository.save(objetoPerdido);
        return objetoPerdido;
    }

    // Actualizar un objeto
    public ObjetoPerdido actualizarObjetoPerdido(Long idObjetoPerdido, ObjetoPerdidoRequest request){
        ObjetoPerdido objetoPerdido = listarPorId(idObjetoPerdido);
        verificarFuncion(request.getIdFuncion());
        objetoPerdido.setObjeto(request.getObjeto());
        objetoPerdido.setIdFuncion(request.getIdFuncion());
        objetoPerdidoRepository.save(objetoPerdido);
        return objetoPerdido;
    }

    // Eliminar un objeto
    public void eliminarObjetoPerdido(Long idObjetoPerdido){
        log.info("Eliminando una funcion con ID: {}", idObjetoPerdido);
        if(!objetoPerdidoRepository.existsById(idObjetoPerdido)){
            throw new ObjetoPerdidoNoEncontradoException("Objeto perdido no encontrado");
        }
        objetoPerdidoRepository.deleteById(idObjetoPerdido);
    }

    // -------------------------------- METODOS EXTRAS --------------------------------

    // Verificar que si exista la funcion
    private void verificarFuncion(Long idFuncion){
        Boolean existeFuncion = webClientBuilder.build().get()
                .uri(funcionServiceUrl + "/api/funciones/verificar/{idFuncion}", idFuncion)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorResponse ->
                        Mono.error(new RecursoWebClientNoEncontradoException("Recurso de webClien (funciones) no encontrado"))
                )
                .bodyToMono(Boolean.class)
                .block();
    }
}
