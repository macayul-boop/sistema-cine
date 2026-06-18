package duoc.sistema.cine.sala_service.controller;

import duoc.sistema.cine.sala_service.assemblers.SalaModelAssembler;
import duoc.sistema.cine.sala_service.dto.SalaRequest;
import duoc.sistema.cine.sala_service.model.Sala;
import duoc.sistema.cine.sala_service.service.SalaService;
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
@RequestMapping("/api/salas")
@CrossOrigin(origins = "*")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @Autowired
    private SalaModelAssembler assembler;

    // -------------------------- METODOS BASICOS ------------------------

    // Listar todas las salas
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Sala>>> listarTodos() {
        List<Sala> list = salaService.listarTodos();
        if (list.isEmpty()) return ResponseEntity.noContent().build();

        List<EntityModel<Sala>> salas = list.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel coleccion = CollectionModel.of(salas,
                linkTo(methodOn(SalaController.class).listarTodos()).withRel("salas"));

        return ResponseEntity.ok(coleccion);
    }

    // Listar una sala por su ID
    @GetMapping(value = "/{idSala}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sala>> buscarSalaPorId(@PathVariable Long idSala) {
        Sala sala = salaService.buscarSalaPorId(idSala);
        return ResponseEntity.ok(assembler.toModel(sala));
    }

    // Craer una sala
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sala>> guardar(@Valid @RequestBody SalaRequest request) {
        Sala guardado = salaService.guardarSala(request);

        return ResponseEntity
                .created(linkTo(methodOn(SalaController.class).buscarSalaPorId(guardado.getId())).toUri())
                .body(assembler.toModel(guardado));

    }

    // Actualizar una sala
    @PutMapping(value = "/{idSala}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Sala>> actualizar(@PathVariable Long idSala, @Valid @RequestBody SalaRequest request) {
        Sala sala = salaService.actualizarSala(idSala, request);
        return ResponseEntity.ok(assembler.toModel(sala));
    }

    // Eliminar una sala
    @DeleteMapping("/{idSala}")
    public ResponseEntity<String> eliminar(@PathVariable Long idSala) {
        salaService.eliminarSala(idSala);
        return ResponseEntity.ok("Sala eliminada correctamente");
    }


    // -------------------------- METODOS EXTRAS ------------------------

    // Validar que si existe una sala
    @GetMapping("/verificar/{idSala}")
    public ResponseEntity<Boolean> verificarSala(@PathVariable Long idSala) {
        Boolean existe = salaService.existeSala(idSala);
        return ResponseEntity.ok(existe);
    }

}
