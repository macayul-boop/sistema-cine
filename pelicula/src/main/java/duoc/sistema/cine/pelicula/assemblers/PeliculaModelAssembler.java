package duoc.sistema.cine.pelicula.assemblers;

import duoc.sistema.cine.pelicula.controller.PeliculaController;
import duoc.sistema.cine.pelicula.model.Pelicula;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PeliculaModelAssembler implements RepresentationModelAssembler<Pelicula, EntityModel<Pelicula>> {
    @Override
    public EntityModel<Pelicula> toModel(Pelicula pelicula) {
        return EntityModel.of(pelicula,
                linkTo(methodOn(PeliculaController.class).buscarPeliculaPorId(pelicula.getId())).withSelfRel(),
                linkTo(methodOn(PeliculaController.class).listarTodos()).withRel("peliculas"));
    }
}
