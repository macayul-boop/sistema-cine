package duoc.sistema.cine.confiteria_service.assemblers;

import duoc.sistema.cine.confiteria_service.controller.ConfiteriaController;
import duoc.sistema.cine.confiteria_service.model.Confiteria;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ConfiteriaModelAssembler implements RepresentationModelAssembler<Confiteria, EntityModel<Confiteria>> {
    @Override
    public EntityModel<Confiteria> toModel(Confiteria confiteria) {
        return EntityModel.of(confiteria,
                linkTo(methodOn(ConfiteriaController.class).listarPorId(confiteria.getId())).withSelfRel(),
                linkTo(methodOn(ConfiteriaController.class).listarTodos()).withRel("confiterias"));
    }
}
