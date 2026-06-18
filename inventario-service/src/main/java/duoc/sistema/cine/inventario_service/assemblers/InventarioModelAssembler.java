package duoc.sistema.cine.inventario_service.assemblers;

import duoc.sistema.cine.inventario_service.controller.InventarioController;
import duoc.sistema.cine.inventario_service.model.Inventario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class InventarioModelAssembler implements RepresentationModelAssembler<Inventario, EntityModel<Inventario>> {
    @Override
    public EntityModel<Inventario> toModel(Inventario inventario) {
        return EntityModel.of(inventario,
                linkTo(methodOn(InventarioController.class).listarPorId(inventario.getId())).withSelfRel(),
                linkTo(methodOn(InventarioController.class).listarTodos()).withRel("inventario"));
    }
}
