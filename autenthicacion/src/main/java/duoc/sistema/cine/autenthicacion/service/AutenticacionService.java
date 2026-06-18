package duoc.sistema.cine.autenthicacion.service;

import duoc.sistema.cine.autenthicacion.dto.LoginRequest;
import duoc.sistema.cine.autenthicacion.dto.LoginResponse;
import duoc.sistema.cine.autenthicacion.exception.ResourceNotFoundException;
import duoc.sistema.cine.autenthicacion.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AutenticacionService {

    private final WebClient.Builder webClientBuilder;
    private static final Logger log = LoggerFactory.getLogger(AutenticacionService.class);

    @Autowired
    private  JwtService jwtService;

    @Value("${services.usuario.url}")
    private String usuarioServiceUrl;

    public LoginResponse login(LoginRequest request){
        log.info("Login correcto, se ha generado un token");

        Usuario usuario = webClientBuilder.build().post()
                .uri(usuarioServiceUrl + "/api/usuario/validar")
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, errorResponse ->
                    Mono.error(new ResourceNotFoundException("Recurso de webClien no encontrado Hola no se encontro"))
                )
                .bodyToMono(Usuario.class)
                .block();


        String token = jwtService.generarToken(usuario.getUsername(), usuario.getTipoUsuario());
        LoginResponse respuesta = new LoginResponse("Token creado con exito",token, usuario.getUsername(), usuario.getTipoUsuario());
        return respuesta;
    }

}
