package duoc.sistema.cine.objetos_perdidos_service.controller;

import duoc.sistema.cine.objetos_perdidos_service.assemblers.ObjetoPerdidoModelAssembler;
import duoc.sistema.cine.objetos_perdidos_service.dto.ObjetoPerdidoRequest;
import duoc.sistema.cine.objetos_perdidos_service.model.ObjetoPerdido;
import duoc.sistema.cine.objetos_perdidos_service.service.ObjetoPerdidoService;
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
@RequestMapping("/api/objetosPerdidos")
public class ObjetoPerdidoController {

    @Autowired
    private ObjetoPerdidoService objetoPerdidoService;

    @Autowired
    private ObjetoPerdidoModelAssembler assembler;


    // -------------------------------- METODOS BASICAS --------------------------------

    // Listar todos los objetos
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<ObjetoPerdido>>> listarTodos(){
        List<ObjetoPerdido> list = objetoPerdidoService.listarTodos();
        if(list.isEmpty()) return ResponseEntity.noContent().build();

        List<EntityModel<ObjetoPerdido>> objetosPerdidos = list.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel coleccion = CollectionModel.of(objetosPerdidos,
                linkTo(methodOn(ObjetoPerdidoController.class).listarTodos()).withRel("ObjetosPerdidos"));

        return ResponseEntity.ok(coleccion);
    }

    // Listar un objetos por su id
    @GetMapping(value = "/{idObjetoPerdido}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ObjetoPerdido>> listarPorId(@PathVariable Long idObjetoPerdido){
        ObjetoPerdido objetoPerdido = objetoPerdidoService.listarPorId(idObjetoPerdido);
        return ResponseEntity.ok(assembler.toModel(objetoPerdido));
    }

    // Agregar un objeto perdido
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ObjetoPerdido>> agregarObjetoPerdido(@Valid @RequestBody ObjetoPerdidoRequest request){
        ObjetoPerdido objetoPerdido = objetoPerdidoService.agregarObjetoPerdido(request);
        return ResponseEntity.ok(assembler.toModel(objetoPerdido));
    }

    // Actualizar un objeto perdido
    @PutMapping(value = "/{idObjetoPerdido}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ObjetoPerdido>> actualizarObjetoPerdido(@PathVariable Long idObjetoPerdido, @Valid @RequestBody ObjetoPerdidoRequest request){
        ObjetoPerdido objetoPerdido = objetoPerdidoService.actualizarObjetoPerdido(idObjetoPerdido, request);
        return ResponseEntity.ok(assembler.toModel(objetoPerdido));
    }

    // Eliminar un objeto
    @DeleteMapping(value = "/{idObjetoPerdido}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<String> eliminarObjetoPerdido(@PathVariable Long idObjetoPerdido){
        objetoPerdidoService.eliminarObjetoPerdido(idObjetoPerdido);
        return ResponseEntity.ok("Objeto perdido eliminado");
    }
}
