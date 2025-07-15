package pe.edu.galaxy.training.java.ws.api_gestion_productos_v2.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pe.edu.galaxy.training.java.ws.api_gestion_productos_v2.entity.Producto;
import pe.edu.galaxy.training.java.ws.api_gestion_productos_v2.repository.ProductoRepository;

@RequiredArgsConstructor
@Service
public class ProductoServiceImpl implements ProductoService {

	//@Autowired
	private final ProductoRepository productoRepository;

	@Override
	public List<Producto> findAll() {
		//return productoRepository.findAll();
		return productoRepository.findAllCustom();
	}

	@Override
	public Optional<Producto> findById(Long id) {
		return productoRepository.findById(id);
	}

	@Override
	public Optional<Producto> findBySKU(String sku) {
		return productoRepository.findBySku(sku);
	}

	@Override
	public Producto save(Producto producto) {
		producto.setFechaRegistro(LocalDate.now());
		producto.setEstado("1");
		return productoRepository.save(producto);
	}

	@Override
	public Producto update(Producto producto) { // Put - Patch
		
		Optional<Producto> optProducto = productoRepository.findById(producto.getId());

		if (optProducto.isEmpty()) {
			producto.setId(null);
			producto.setFechaRegistro(LocalDate.now());
			producto.setEstado("1");
			return productoRepository.save(producto); //Update
		} else {
			Producto obtProducto = optProducto.get();
			
			obtProducto.setNombre(producto.getNombre());
			obtProducto.setSku(producto.getSku());
			obtProducto.setPrecio(producto.getPrecio());
			
			return productoRepository.save(obtProducto);
		}

	}

	@Transactional
	@Override
	public Boolean updatePrecio(Long id, Double precio) {
		/*
		Optional<Producto> optProducto = productoRepository.findById(id);

		if (optProducto.isEmpty()) {			
			return false;
		} else {
			Producto obtProducto = optProducto.get();
			obtProducto.setPrecio(precio);
			productoRepository.save(obtProducto);
			return true;
		}*/
		productoRepository.updatePrecio(id, precio);
		return true;
	}

	@Override
	public Boolean delete(Long id) {
		productoRepository.delete(id);
		return true;
	}

	@Override
	public List<Producto> findByNombre(String nombre) {
		return productoRepository.findByNombre("%"+nombre+"%");
	}

}
