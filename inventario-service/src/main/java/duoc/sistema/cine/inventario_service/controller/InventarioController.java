package duoc.sistema.cine.inventario_service.controller;

import duoc.sistema.cine.inventario_service.assemblers.InventarioModelAssembler;
import duoc.sistema.cine.inventario_service.dto.InventarioRequest;
import duoc.sistema.cine.inventario_service.model.Inventario;
import duoc.sistema.cine.inventario_service.service.InventarioService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.message.LoggerNameAwareMessage;
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
@RequestMapping("/api/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private InventarioModelAssembler assembler;

    // -------------------------- METODOS BASICOS ------------------------

    // Leer todos los productos
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Inventario>>> listarTodos(){
        List<Inventario> lista = inventarioService.listarTodos();
        if(lista.isEmpty()) return ResponseEntity.noContent().build();

        List<EntityModel<Inventario>> inventarios = lista.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel coleccion = CollectionModel.of(inventarios,
                linkTo(methodOn(InventarioController.class).listarTodos()).withRel("inventarios"));

        return ResponseEntity.ok(coleccion);
    }

    // Leer un producto por su id
    @GetMapping(value = "/{idInventario}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Inventario>> listarPorId(@PathVariable Long idInventario){
        Inventario inventario = inventarioService.listarPorId(idInventario);
        return ResponseEntity.ok(assembler.toModel(inventario));
    }

    // Crear un producto
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Inventario>> crearInventario(@Valid @RequestBody InventarioRequest request){
        Inventario inventario = inventarioService.crearInventario(request);
        return ResponseEntity.ok(assembler.toModel(inventario));
    }

    // Actualizar un producto
    @PutMapping(value = "/{idInventario}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Inventario>> actualizarInventario(@PathVariable Long idInventario, @Valid @RequestBody InventarioRequest request){
        Inventario inventario = inventarioService.actualizarInventario(idInventario, request);
        return ResponseEntity.ok(assembler.toModel(inventario));
    }

    // Eliminar un producto
    @DeleteMapping("/{idInventario}")
    public ResponseEntity<String> eliminarInventario(@PathVariable Long idInventario){
        inventarioService.eliminarInventario(idInventario);
        return ResponseEntity.ok("Producto eliminado");
    }


    // -------------------------- METODOS EXTRAS ------------------------

    // Verifica que exista un producto
    @GetMapping("/verificar/{idInventario}")
    public ResponseEntity<Boolean> verificarInventario(@PathVariable Long idInventario){
        Boolean existe = inventarioService.verificarInventario(idInventario);
        return ResponseEntity.ok(existe);
    }

    // Verificar que la cantidad requerida si haya sufuciente
    @GetMapping("/cantidad/{idInventario}")
    public ResponseEntity<Long> obtenerCantidad(@PathVariable Long idInventario){
        Long cantidad = inventarioService.obtenerCantidad(idInventario);
        return ResponseEntity.ok(cantidad);
    }

    // Disminuir cantidad
    @PostMapping("/disminuir/{idInventario}")
    public ResponseEntity<Void> disminuirCantidad(@PathVariable Long idInventario, @RequestBody Long cantidad){
        inventarioService.disminuirCantidad(idInventario, cantidad);
        return ResponseEntity.noContent().build();
    }
}
