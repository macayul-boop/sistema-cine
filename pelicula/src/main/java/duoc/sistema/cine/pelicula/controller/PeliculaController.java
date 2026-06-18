package duoc.sistema.cine.pelicula.controller;

import duoc.sistema.cine.pelicula.assemblers.PeliculaModelAssembler;
import duoc.sistema.cine.pelicula.dto.PeliculaReponse;
import duoc.sistema.cine.pelicula.dto.PeliculaRequest;
import duoc.sistema.cine.pelicula.model.Pelicula;
import duoc.sistema.cine.pelicula.service.PeliculaService;
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
@RequestMapping("/api/peliculas")
@CrossOrigin(origins = "*")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @Autowired
    private PeliculaModelAssembler assembler;

    // -------------------------- METODOS BASICOS ------------------------

    // Listar todas las peliculas
    @GetMapping(value = "/catalogo", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Pelicula>>> listarTodos() {
        List<Pelicula> list = peliculaService.listarTodos();
        if(list.isEmpty()) return ResponseEntity.noContent().build();

        List<EntityModel<Pelicula>> peliculas = list.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel coleccion = CollectionModel.of(peliculas,
                linkTo(methodOn(PeliculaController.class).listarTodos()).withRel("peliculas"));

        return ResponseEntity.ok(coleccion);
    }

    // Listar una pelicula por su ID
    @GetMapping(value = "/{idPelicula}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Pelicula>> buscarPeliculaPorId(@PathVariable Long idPelicula) {
        Pelicula pelicula = peliculaService.buscarPeliculaPorId(idPelicula);
        return ResponseEntity.ok(assembler.toModel(pelicula));
    }

    // Craer una pelicula
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Pelicula>> guardar(@Valid @RequestBody PeliculaRequest request) {
        Pelicula guardado= peliculaService.guardarPelicula(request);

        return ResponseEntity
                .created(linkTo(methodOn(PeliculaController.class).buscarPeliculaPorId(guardado.getId())).toUri())
                .body(assembler.toModel(guardado));
    }

    // Actualizar una pelicula
    @PutMapping(value = "/{idPelicula}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Pelicula>> actualizar(@PathVariable Long idPelicula, @Valid @RequestBody PeliculaRequest request) {
        Pelicula pelicula= peliculaService.actualizarPelicula(idPelicula,request);
        return ResponseEntity.ok(assembler.toModel(pelicula));
    }

    // Eliminar una pelicula
    @DeleteMapping("/{idPelicula}")
    public ResponseEntity<String> eliminar(@PathVariable Long idPelicula) {
        peliculaService.eliminarPelicula(idPelicula);
        return ResponseEntity.ok("Pelicula eliminada correctamente");
    }

    // -------------------------- METODOS EXTRAS ------------------------

    // Validar que si existe una pelicula
    @GetMapping("/verificar/{idPelicula}")
    public ResponseEntity<Boolean> verificarPelicula(@PathVariable Long idPelicula){
        Boolean existe = peliculaService.existePelicula(idPelicula);
        return ResponseEntity.ok(existe);
    }

    // Obtener infromacion de una pelicula
    @GetMapping("/informacion/{idPelicula}")
    public ResponseEntity<PeliculaReponse> buscarInformacionPelicula(@PathVariable Long idPelicula){
        PeliculaReponse pelicula = peliculaService.obtenerInfoPelicula(idPelicula);
        return ResponseEntity.ok(pelicula);
    }

}
