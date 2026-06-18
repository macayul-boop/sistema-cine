package duoc.sistema.cine.sala_service.assemblers;

import duoc.sistema.cine.sala_service.controller.SalaController;
import duoc.sistema.cine.sala_service.model.Sala;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SalaModelAssembler implements RepresentationModelAssembler<Sala, EntityModel<Sala>> {
    @Override
    public EntityModel<Sala> toModel(Sala sala) {
        return EntityModel.of(sala,
                linkTo(methodOn(SalaController.class).buscarSalaPorId(sala.getId())).withSelfRel(),
                linkTo(methodOn(SalaController.class).listarTodos()).withRel("salas"));
    }
}
