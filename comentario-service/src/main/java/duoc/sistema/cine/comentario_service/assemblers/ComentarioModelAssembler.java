package duoc.sistema.cine.comentario_service.assemblers;

import duoc.sistema.cine.comentario_service.controller.ComentarioController;
import duoc.sistema.cine.comentario_service.model.Comentario;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class ComentarioModelAssembler implements RepresentationModelAssembler<Comentario, EntityModel<Comentario>> {
    @Override
    public EntityModel<Comentario> toModel(Comentario comentario) {
        return EntityModel.of(comentario,
                linkTo(methodOn(ComentarioController.class).listarComentarioId(comentario.getId())).withSelfRel(),
                linkTo(methodOn(ComentarioController.class).listarTodos()).withRel("Comentarios"));
    }
}
