package duoc.sistema.cine.comentario_service.service;

import duoc.sistema.cine.comentario_service.dto.ComentarioRequest;
import duoc.sistema.cine.comentario_service.exception.ComentarioNoEncontrado;
import duoc.sistema.cine.comentario_service.exception.RecursoWebClientNoEncontradoException;
import duoc.sistema.cine.comentario_service.model.Comentario;
import duoc.sistema.cine.comentario_service.repository.ComentarioRepository;
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
public class ComentarioService {
    private static final Logger log = LoggerFactory.getLogger(ComentarioService.class);

    private final WebClient.Builder webClientBuilder;

    @Value("${services.pelicula.url}")
    private String peliculaServiceUrl;

    @Value("${services.usuario.url}")
    private  String usuarioServiceUrl;

    @Autowired
    private ComentarioRepository ComentarioRepository;

    // -------------------------------- METODOS BASICAS --------------------------------

    // Listar todos los comentarios
    public List<Comentario> listarComentarios(){
        log.info("Listando todo las funciones");
        return ComentarioRepository.findAll();
    }

    // Listar un comentario por su ID
    public Comentario listarComentarioPorId(Long idComentario){
        log.info("Listando el comentario con el Id: {}", idComentario);
        Comentario comentario = ComentarioRepository.findById(idComentario)
                .orElseThrow(() -> new ComentarioNoEncontrado("El comentario no existe"));

        return comentario;
    }

    // Crear un comentario
    public Comentario crearComentario(ComentarioRequest request){
        // Verificar que si existe la pelicula y usuario
        verificacion(request.getIdPelicula(), request.getIdusuario());

        Comentario c = new Comentario();
        c.setFechaHora(request.getFechaHora());
        c.setComentario(request.getComentario());
        c.setIdPelicula(request.getIdPelicula());
        c.setIdusuario(request.getIdusuario());
        ComentarioRepository.save(c);
        return c;
    }

    // Actualizar un comentario
    public Comentario actualizarComentario(Long idComentario, ComentarioRequest request){
        Comentario comentario = listarComentarioPorId(idComentario);
        verificacion(comentario.getIdPelicula(), comentario.getIdusuario());

        comentario.setComentario(request.getComentario());
        comentario.setFechaHora(request.getFechaHora());
        comentario.setIdPelicula(request.getIdPelicula());
        comentario.setIdusuario(request.getIdusuario());
        ComentarioRepository.save(comentario);
        return comentario;
    }

    // Eliminar un comentario
    public void eliminarComentario(Long idComentario){
        if(!ComentarioRepository.existsById(idComentario)){
            throw new ComentarioNoEncontrado("Comentario no encontrada");
        }
        ComentarioRepository.deleteById(idComentario);
    }


    // -------------------------------- METODOS EXTRA --------------------------------

    private void verificacion(Long idPelicula, Long idUsuario){
        Boolean existePelicula = webClientBuilder.build().get()
                .uri(peliculaServiceUrl + "/api/peliculas/verificar/{idPelicula}", idPelicula)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorResponse ->
                        Mono.error(new RecursoWebClientNoEncontradoException("Recurso de webClien (pelicula) no encontrado"))
                )
                .bodyToMono(Boolean.class)
                .block();

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
