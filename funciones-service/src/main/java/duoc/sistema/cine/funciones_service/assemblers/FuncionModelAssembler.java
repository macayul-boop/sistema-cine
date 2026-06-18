package duoc.sistema.cine.funciones_service.assemblers;

import duoc.sistema.cine.funciones_service.controller.FuncionController;
import duoc.sistema.cine.funciones_service.model.Funcion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FuncionModelAssembler implements RepresentationModelAssembler<Funcion, EntityModel<Funcion>> {
    @Override
    public EntityModel<Funcion> toModel(Funcion funcion) {
        return EntityModel.of(funcion,
                linkTo(methodOn(FuncionController.class).listarFuncionPorId(funcion.getId())).withSelfRel(),
                linkTo(methodOn(FuncionController.class).listarFunciones()).withRel("funciones"));
    }
}
