package duoc.sistema.cine.objetos_perdidos_service.assemblers;

import duoc.sistema.cine.objetos_perdidos_service.controller.ObjetoPerdidoController;
import duoc.sistema.cine.objetos_perdidos_service.model.ObjetoPerdido;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ObjetoPerdidoModelAssembler implements RepresentationModelAssembler<ObjetoPerdido, EntityModel<ObjetoPerdido>> {
    @Override
    public EntityModel<ObjetoPerdido> toModel(ObjetoPerdido objetoPerdido) {
        return EntityModel.of(objetoPerdido,
                linkTo(methodOn(ObjetoPerdidoController.class).listarPorId(objetoPerdido.getId())).withSelfRel(),
                linkTo(methodOn(ObjetoPerdidoController.class).listarTodos()).withRel("ObjetosPerdidos"));
    }
}
