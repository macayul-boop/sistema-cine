package duoc.sistema.cine.entrada_service.assemblers;

import duoc.sistema.cine.entrada_service.controller.EntradaController;
import duoc.sistema.cine.entrada_service.model.Entrada;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EntradaModelAssembler implements RepresentationModelAssembler<Entrada, EntityModel<Entrada>> {
    @Override
    public EntityModel<Entrada> toModel(Entrada entrada) {
        return EntityModel.of(entrada,
                linkTo(methodOn(EntradaController.class).listarEntradaPorId(entrada.getId())).withSelfRel(),
                linkTo(methodOn(EntradaController.class).listarEntradas()).withRel("entradas"));
    }
}
