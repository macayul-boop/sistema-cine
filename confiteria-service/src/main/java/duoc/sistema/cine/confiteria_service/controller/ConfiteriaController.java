package duoc.sistema.cine.confiteria_service.controller;

import duoc.sistema.cine.confiteria_service.assemblers.ConfiteriaModelAssembler;
import duoc.sistema.cine.confiteria_service.dto.ConfiteriaRequest;
import duoc.sistema.cine.confiteria_service.model.Confiteria;
import duoc.sistema.cine.confiteria_service.service.ConfiteriaService;
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
@RequestMapping("/api/confiteria")
public class ConfiteriaController {

    @Autowired
    private ConfiteriaService confiteriaService;

    @Autowired
    private ConfiteriaModelAssembler assembler;

    // -------------------------- METODOS BASICOS ------------------------

    // Listar todos
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Confiteria>>> listarTodos(){
        List<Confiteria> list = confiteriaService.listarTodos();
        if(list.isEmpty()) return ResponseEntity.noContent().build();

        List<EntityModel<Confiteria>> confiterias = list.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());


        CollectionModel coleccion = CollectionModel.of(confiterias,
                linkTo(methodOn(ConfiteriaController.class).listarTodos()).withRel("confiterias"));

        return ResponseEntity.ok(coleccion);
    }

    // Listar por id
    @GetMapping(value = "/{idConfiteria}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Confiteria>> listarPorId(@PathVariable Long idConfiteria){
        Confiteria confiteria = confiteriaService.listarPorId(idConfiteria);
        return ResponseEntity.ok(assembler.toModel(confiteria));
    }

    // agregar
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Confiteria>> agregarConfiteria(@Valid @RequestBody ConfiteriaRequest request){
        Confiteria confiteria = confiteriaService.crearConfiteria(request);
        return ResponseEntity.ok(assembler.toModel(confiteria));
    }

    // eliminar
    @DeleteMapping("/{idConfiteria}")
    public ResponseEntity<String> eliminarConfiteria(@PathVariable Long idConfiteria){
        confiteriaService.eliminarConfiteria(idConfiteria);
        return ResponseEntity.ok("Confiteria eliminada");
    }
}
