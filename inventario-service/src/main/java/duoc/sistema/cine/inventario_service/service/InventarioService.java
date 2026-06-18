package duoc.sistema.cine.inventario_service.service;

import duoc.sistema.cine.inventario_service.dto.InventarioRequest;
import duoc.sistema.cine.inventario_service.exception.InventarioNoEncontradoException;
import duoc.sistema.cine.inventario_service.exception.RecursoWebClientInvalidoException;
import duoc.sistema.cine.inventario_service.model.Inventario;
import duoc.sistema.cine.inventario_service.repository.InventarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioService {
    private static final Logger log = LoggerFactory.getLogger(InventarioService.class);

    @Autowired
    private InventarioRepository inventarioRepository;

    // -------------------------------- METODOS BASICAS --------------------------------

    // Listar todos los productos
    public List<Inventario> listarTodos(){
        log.info("Listando todo las funciones");
        return inventarioRepository.findAll();
    }

    // Listar un producto por su id
    public Inventario listarPorId(Long idInventario){
        Inventario i = inventarioRepository.findById(idInventario)
                .orElseThrow(()-> new InventarioNoEncontradoException("Producto del inventario no enocntrado"));
        return i;
    }

    // Crear un producto
    public Inventario crearInventario(InventarioRequest request){
        log.info("Creando una funcion");
        Inventario inventario = new Inventario();
        inventario.setNombreProducto(request.getNombreProducto());
        inventario.setCantidad(request.getCantidad());
        inventario.setValor(request.getValor());
        inventarioRepository.save(inventario);
        return inventario;
    }

    // Actualizar un producto
    public Inventario actualizarInventario(Long idInventario, InventarioRequest request){
        log.info("Actualizando una funcion");
        Inventario resultado = listarPorId(idInventario);

        resultado.setNombreProducto(request.getNombreProducto());
        resultado.setCantidad(request.getCantidad());
        resultado.setValor(request.getValor());
        inventarioRepository.save(resultado);
        return resultado;
    }

    // Eliminar un producto
    public void eliminarInventario(Long idInventario){
        log.info("Actualizando una funcion");
        if(!inventarioRepository.existsById(idInventario)){
            throw new InventarioNoEncontradoException("Producto no encontrada");
        }
        inventarioRepository.deleteById(idInventario);
    }

    // -------------------------------- METODOS EXTRAS --------------------------------

    // Verificar qu si existe un producto
    public boolean verificarInventario(Long idInventario){
        log.info("Buscando si existe producto con id: {}", idInventario);
        Boolean existe = inventarioRepository.existsById(idInventario);
        if(!existe){
            throw new InventarioNoEncontradoException("Producto no encontrado");
        }
        return existe;
    }

    // Verificar que no compre mas de lo quie hay en el inventario
    public Long obtenerCantidad(Long idInventario){
        Inventario inventario = listarPorId(idInventario);
        return inventario.getCantidad();
    }

    // Disminuir cantidad
    public void  disminuirCantidad(Long idInventario, Long cantidad){
        Inventario inventario = listarPorId(idInventario);
        Long nuevoValor = inventario.getCantidad() - cantidad;
        inventario.setCantidad(nuevoValor);
        inventarioRepository.save(inventario);
    }

}
