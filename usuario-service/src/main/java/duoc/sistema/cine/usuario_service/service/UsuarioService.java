package duoc.sistema.cine.usuario_service.service;

import duoc.sistema.cine.usuario_service.dto.LoginRequest;
import duoc.sistema.cine.usuario_service.dto.UsuarioRequest;
import duoc.sistema.cine.usuario_service.exception.PasswordNoCoincideException;
import duoc.sistema.cine.usuario_service.exception.TipoUsuarioInvalidoException;
import duoc.sistema.cine.usuario_service.exception.UserNameNoEnontradoException;
import duoc.sistema.cine.usuario_service.exception.UsuarioNoEncontradoException;
import duoc.sistema.cine.usuario_service.model.Usuario;
import duoc.sistema.cine.usuario_service.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    // -------------------------------- METODOS BASICOS --------------------------------

    // Llamar a todos los usuarios
    public List<Usuario> listarTodo(){
        log.info("Listando todo los usuarios");
        return usuarioRepository.findAll();
    }

    // Llamar a un usuario por su ID
    public Usuario listarPorId(Long id){
        log.info("Buscando usuario con id: {}", id );
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado"));
    }

    // Crear un usuario de tipo cliente
    public Usuario crearUsuarioCliente(UsuarioRequest uRequest){
        log.info("Crear usuario cliente con username: {}", uRequest.getUsername());
        Usuario u = new Usuario();
        u.setUsername(uRequest.getUsername());
        u.setPassword(uRequest.getPassword());
        u.setNombre(uRequest.getNombre());
        u.setApellido(uRequest.getApellido());
        u.setFechaNacimiento(uRequest.getFechaNacimiento());
        u.setEmail(uRequest.getEmail());
        u.setTipoUsuario("cliente");
        usuarioRepository.save(u);
        return u;
    }

    // Crear un usuario interno
    public Usuario crearUsuarioEmpleado(UsuarioRequest uRequest){
        log.info("Crear usuario interno con username: {}", uRequest.getUsername());
        Usuario u = new Usuario();
        u.setUsername(uRequest.getUsername());
        u.setPassword(uRequest.getPassword());
        u.setNombre(uRequest.getNombre());
        u.setApellido(uRequest.getApellido());
        u.setFechaNacimiento(uRequest.getFechaNacimiento());
        u.setEmail(uRequest.getEmail());
        u.setTipoUsuario(uRequest.getTipoUsuario());
        usuarioRepository.save(u);
        return u;
    }

    // Actualizar un usuario
    public Usuario actualizarUsuario(Long idUsuario, UsuarioRequest request){
        log.info("Actualizando usuario con id: {}", idUsuario);
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado"));

        usuario.setUsername(request.getUsername());
        usuario.setPassword(request.getPassword());
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());
        usuario.setTipoUsuario(request.getTipoUsuario());
        usuario.setFechaNacimiento(request.getFechaNacimiento());
        usuarioRepository.save(usuario);
        return usuario;
    }

    // Eliminar un usuario
    public void eliminarUsuario(Long idUsuario){
        log.info("Eliminando usuario con id: {}", idUsuario );
        if(!usuarioRepository.existsById(idUsuario)){
            throw new UsuarioNoEncontradoException("No se puede eliminar un usuario que no existe");
        }
        usuarioRepository.deleteById(idUsuario);
    }

    // -------------------------------- METODOS EXTRAS --------------------------------

    // Verificar que una funcion existe
    public Boolean existeUsuario(Long idUsuario){
        log.info("Verificando que exista un usuario con ID: {}", idUsuario);
        boolean existe = usuarioRepository.existsById(idUsuario);
        if(!existe){
            throw new UsuarioNoEncontradoException("Usuario no encontrado");
        }
        return existe;
    }

    // Buscar usuario para validar la informacion para el mciro servicio de autenticacion
    public Usuario BuscarUsuarioLogin(LoginRequest request){
        log.info("Validando usuario con username: {}", request.getUsername());
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNameNoEnontradoException("Usuario no encontrado"));

        if(!usuario.getPassword().equals(request.getPassword())){
            throw new PasswordNoCoincideException("La contraseña no coincide");
        }

        if(!"empleado".equals(request.getTipoUsuario())){
            throw new TipoUsuarioInvalidoException("Tipo de usuario invalido");
        }

        return usuario;
    }

}
