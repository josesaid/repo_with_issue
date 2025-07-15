package pe.edu.galaxy.training.java.ws.api_gestion_productos_v2.service;

import java.util.List;
import java.util.Optional;
import pe.edu.galaxy.training.java.ws.api_gestion_productos_v2.entity.Producto;

public interface ProductoService {
	
	Optional<Producto> findById(Long id);
	
	Optional<Producto> findBySKU(String sku);

	List<Producto> findAll();

	List<Producto> findByNombre(String nombre);
	
	Producto save(Producto producto);
	
	Producto update(Producto producto);
	
	Boolean updatePrecio(Long id, Double precio);
	
	Boolean delete(Long id);
}
