package duoc.sistema.cine.entrada_service.controller;

import duoc.sistema.cine.entrada_service.assemblers.EntradaModelAssembler;
import duoc.sistema.cine.entrada_service.dto.EntradaRequest;
import duoc.sistema.cine.entrada_service.dto.EntradaResponse;
import duoc.sistema.cine.entrada_service.model.Entrada;
import duoc.sistema.cine.entrada_service.service.EntradaService;
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
@RequestMapping("/api/entradas")
public class EntradaController {

    @Autowired
    private EntradaService entradaService;

    @Autowired
    private EntradaModelAssembler assembler;

    // -------------------------------- METODOS BASICAS --------------------------------

    // Listar todas las entradas
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Entrada>>> listarEntradas(){
        List<Entrada> lista = entradaService.listarEntradas();
        if(lista.isEmpty()) return ResponseEntity.noContent().build();

        List<EntityModel<Entrada>> entradas = lista.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel coleccion = CollectionModel.of(entradas,
                linkTo(methodOn(EntradaController.class).listarEntradas()).withRel("entradas"));

        return ResponseEntity.ok(coleccion);
    }

    // Listar una entrada por su id [NO SE PUDO AGREGAR HAETOS POR QUE SE COMPLICARIA MUCHO POR QUE LO HICE MAL DESDE EL PRINCIPIO]
    @GetMapping("/info/{idEntrada}")
    public ResponseEntity<EntradaResponse> listarEntradaInfoPorId(@PathVariable Long idEntrada){
        EntradaResponse respuesta = entradaService.listarEntradaInfoPorId(idEntrada);
        return ResponseEntity.ok(respuesta);
    }

    // Listar una entrada por su ID
    @GetMapping(value = "/{idEntrada}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Entrada>> listarEntradaPorId(@PathVariable Long idEntrada){
        Entrada respuesta = entradaService.listarEntradaPorId(idEntrada);
        return ResponseEntity.ok(assembler.toModel(respuesta));
    }


    // Crear una entrada
    @PostMapping(value = "/comprar", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Entrada>> crearEntrada(@Valid @RequestBody EntradaRequest request){
        Entrada entrada = entradaService.crearEntrada(request);

        return ResponseEntity
                .created(linkTo(methodOn(EntradaController.class).listarEntradaPorId(entrada.getId())).toUri())
                .body(assembler.toModel(entrada));
    }

    // Actualizar una entrada
    @PutMapping("/{idEntrada}")
    public ResponseEntity<EntityModel<Entrada>> editarEntrada(@PathVariable Long idEntrada, @Valid @RequestBody EntradaRequest request){
        Entrada entrada = entradaService.editarEntrada(idEntrada, request);
        return ResponseEntity.ok(assembler.toModel(entrada));
    }

    // Eliminar una entrada
    @DeleteMapping("/{idEntrada}")
    public ResponseEntity<Void> eliminarEntrada(@PathVariable Long idEntrada){
        entradaService.eliminarEntrada(idEntrada);
        return ResponseEntity.noContent().build();
    }
}
