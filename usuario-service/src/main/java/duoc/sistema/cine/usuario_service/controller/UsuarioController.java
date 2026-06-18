package duoc.sistema.cine.usuario_service.controller;

import duoc.sistema.cine.usuario_service.assemblers.UsuarioModelAssembler;
import duoc.sistema.cine.usuario_service.dto.LoginRequest;
import duoc.sistema.cine.usuario_service.dto.UsuarioRequest;
import duoc.sistema.cine.usuario_service.model.Usuario;
import duoc.sistema.cine.usuario_service.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler assembler;

    // -------------------------- METODOS BASICOS ------------------------

    // Llamar todos los usuarios  [LISTO]
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> listarTodos(){
        List<Usuario> list = usuarioService.listarTodo();
        if(list.isEmpty()) return ResponseEntity.noContent().build();

        List<EntityModel<Usuario>> usuarios = list.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel coleccion = CollectionModel.of(usuarios,
                linkTo(methodOn(UsuarioController.class).listarTodos()).withRel("usuarios"));

        return ResponseEntity.ok(coleccion);
    }

    // Lamar a un usuario por su ID [LISTO]
    @GetMapping(value = "/{idUsuario}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> buscarPorId(@PathVariable Long idUsuario){
        Usuario usuario = usuarioService.listarPorId(idUsuario);
        return ResponseEntity.ok(assembler.toModel(usuario));
    }

    // Crear un usuario tipo cliente
    @PostMapping(value = "/publico/registro", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> crearUsuarioCliente(@Valid @RequestBody UsuarioRequest uRequest){
        Usuario usuario = usuarioService.crearUsuarioCliente(uRequest);

        return ResponseEntity
                .created(linkTo(methodOn(UsuarioController.class).buscarPorId(usuario.getId())).toUri())
                .body(assembler.toModel(usuario));
    }

    // Crear un usuario interno (ej: empleado)
    @PostMapping(value = "/privado/crear", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> crearUsuarioEmpleado(@Valid @RequestBody UsuarioRequest uRequest){
        Usuario usuario = usuarioService.crearUsuarioEmpleado(uRequest);

        return ResponseEntity
                .created(linkTo(methodOn(UsuarioController.class).buscarPorId(usuario.getId())).toUri())
                .body(assembler.toModel(usuario));
    }

    // Actualizar un usuario
    @PutMapping(value = "/{idUsuario}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> actualizarUsuario(@PathVariable Long idUsuario, @Valid @RequestBody UsuarioRequest request){
        Usuario usuario = usuarioService.actualizarUsuario(idUsuario,request);
        return ResponseEntity.ok(assembler.toModel(usuario));
    }

    // Eliminar un usuario
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long idUsuario){
        usuarioService.eliminarUsuario(idUsuario);
        return ResponseEntity.noContent().build();
    }

    // -------------------------- METODOS EXTRAS ------------------------

    // Validar los datos que manda el microServicio autenticacion
    @PostMapping("/validar")
    public ResponseEntity<Usuario> validarLogin(@RequestBody LoginRequest request){
        Usuario usuario = usuarioService.BuscarUsuarioLogin(request);
        return ResponseEntity.ok(usuario);
    }

    // Verificar que una usuario existe
    @GetMapping("/verificar/{idUsuario}")
    public ResponseEntity<Boolean> existeUsuario(@PathVariable Long idUsuario){
        Boolean existe = usuarioService.existeUsuario(idUsuario);
        return ResponseEntity.ok(existe);
    }

}
