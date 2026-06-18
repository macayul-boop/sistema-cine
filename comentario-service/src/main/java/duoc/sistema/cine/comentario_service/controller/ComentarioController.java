package duoc.sistema.cine.comentario_service.controller;

import duoc.sistema.cine.comentario_service.assemblers.ComentarioModelAssembler;
import duoc.sistema.cine.comentario_service.dto.ComentarioRequest;
import duoc.sistema.cine.comentario_service.model.Comentario;
import duoc.sistema.cine.comentario_service.service.ComentarioService;
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
@RequestMapping("/api/comentario")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private ComentarioModelAssembler assembler;

    // ------------------------- METODOS BASICOS ------------------------

    // Listar todos los comentarios
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Comentario>>> listarTodos(){
        List<Comentario> lista = comentarioService.listarComentarios();
        if(lista.isEmpty()) return ResponseEntity.noContent().build();

        List<EntityModel<Comentario>> comentarios = lista.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel coleccion = CollectionModel.of(comentarios,
                linkTo(methodOn(ComentarioController.class).listarTodos()).withRel("comentarios"));

        return ResponseEntity.ok(coleccion);
    }

    // Listar un comentario por su id
    @GetMapping(value = "/{idComentario}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Comentario>> listarComentarioId(@PathVariable Long idComentario){
        Comentario comentario = comentarioService.listarComentarioPorId(idComentario);
        return ResponseEntity.ok(assembler.toModel(comentario));
    }

    // Crear un comentario
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Comentario>> crearComentario(@Valid @RequestBody ComentarioRequest request){
        Comentario comentario = comentarioService.crearComentario(request);
        return ResponseEntity.ok(assembler.toModel(comentario));
    }

    // Actualizar un comentario
    @PutMapping(value = "/{idComentario}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Comentario>> actualizarComentario(@PathVariable Long idComentario, @Valid @RequestBody ComentarioRequest request){
        Comentario comentario = comentarioService.actualizarComentario(idComentario, request);
        return ResponseEntity.ok(assembler.toModel(comentario));
    }


    // Eliminar un comentario
    @DeleteMapping("/{idComentario}")
    public ResponseEntity<String> eliminarComentario(@PathVariable Long idComentario){
        comentarioService.eliminarComentario(idComentario);
        return ResponseEntity.ok("Comentario eliminado");
    }

}
