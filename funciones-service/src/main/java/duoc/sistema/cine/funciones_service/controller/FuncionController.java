package duoc.sistema.cine.funciones_service.controller;

import duoc.sistema.cine.funciones_service.assemblers.FuncionModelAssembler;
import duoc.sistema.cine.funciones_service.dto.FuncionRequest;
import duoc.sistema.cine.funciones_service.dto.FuncionResponse;
import duoc.sistema.cine.funciones_service.model.Funcion;
import duoc.sistema.cine.funciones_service.service.FuncionService;
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
@RequestMapping("/api/funciones")
public class FuncionController {

    @Autowired
    private FuncionService funcionService;

    @Autowired
    private FuncionModelAssembler assembler;

    // -------------------------------- METODOS BASICAS --------------------------------

    // Listar todas las funciones
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Funcion>>> listarFunciones(){
        List<Funcion> lista = funcionService.listarFunciones();
        if(lista.isEmpty()) return ResponseEntity.noContent().build();

        List<EntityModel<Funcion>> funciones = lista.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel coleccion = CollectionModel.of(funciones,
                linkTo(methodOn(FuncionController.class).listarFunciones()).withRel("funciones"));

        return ResponseEntity.ok(coleccion);
    }

    // Listar una funcion por su id
    @GetMapping(value = "/{idFuncion}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Funcion>> listarFuncionPorId(@PathVariable Long idFuncion){
        Funcion funcion = funcionService.listarFuncionPorId(idFuncion);
        return ResponseEntity.ok(assembler.toModel(funcion));
    }

    // Agregar una funcion
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Funcion>> crearFuncion(@Valid @RequestBody FuncionRequest request){
        Funcion funcion = funcionService.crearFuncion(request);

        return ResponseEntity
                .created(linkTo(methodOn(FuncionController.class).listarFuncionPorId(funcion.getId())).toUri())
                .body(assembler.toModel(funcion));

    }

    // Editando una funcion
    @PutMapping(value = "/{idFuncion}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Funcion>> editarFuncion(@PathVariable Long idFuncion, @Valid @RequestBody FuncionRequest request){
        Funcion funcion = funcionService.editarFuncion(idFuncion, request);
        return ResponseEntity.ok(assembler.toModel(funcion));
    }

    // Eliminar una funcion
    @DeleteMapping("/{idFuncion}")
    public ResponseEntity<Void> eliminarFuncion(@PathVariable Long idFuncion){
        funcionService.eliminarFuncion(idFuncion);
        return ResponseEntity.noContent().build();
    }

    // -------------------------------- METODOS EXTRAS --------------------------------

    // Buscar infromacion de funcion
    @GetMapping("/informacion/{idFuncion}")
    public ResponseEntity<FuncionResponse> obtenerInformacionFuncion(@PathVariable Long idFuncion){
        FuncionResponse respuesta = funcionService.obtenerInformacionFuncion(idFuncion);
        return ResponseEntity.ok(respuesta);
    }

    // Listar funciones por id de pelicula
    @GetMapping("/pelicula/{idPelicula}")
    public ResponseEntity<List<Funcion>> listarfuncionesPorIdPelicula(@PathVariable Long idPelicula){
        List<Funcion> lista = funcionService.listarFuncionesPorIdPelicula(idPelicula);
        if(lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    // Verificar que una funcion existe
    @GetMapping("verificar/{idFuncion}")
    public ResponseEntity<Boolean> existeFuncion(@PathVariable Long idFuncion){
        Boolean existe = funcionService.existeFuncion(idFuncion);
        return ResponseEntity.ok(existe);
    }

}
