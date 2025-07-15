package pe.edu.galaxy.training.java.ws.api_gestion_productos_v2.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import pe.edu.galaxy.training.java.ws.api_gestion_productos_v2.entity.Producto;
import pe.edu.galaxy.training.java.ws.api_gestion_productos_v2.service.ProductoService;
import static java.util.Objects.isNull;


@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/productos")
public class ProductoController {
	
	Logger logger = Logger.getLogger(ProductoController.class.getName());
	
	//@Autowired
	private final ProductoService productoService;
	
	@GetMapping
	public ResponseEntity<List<Producto>> findAll(){
	
		try {
			List<Producto> productos= productoService.findAll();
			if (productos.isEmpty()) {
				return ResponseEntity.noContent().build();
			}else {
				return ResponseEntity.ok(productos);
			}
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Producto> findById(@PathVariable("id") Long id){
		try {
			if (id<=0) {
				return ResponseEntity.badRequest().build();
			}
			
			Optional<Producto> optProductos= productoService.findById(id);
			if (optProductos.isEmpty()) {
				return ResponseEntity.noContent().build();
			}else {
				return ResponseEntity.ok(optProductos.get());
			}
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
		
	}
	
	@GetMapping("/sku/{sku}")
	public ResponseEntity<?> findByIdSku(@PathVariable("sku") String sku){
		Map<String, String> res= new HashMap<>();
		try {
			
			if (!validarSku(sku)) {//==false
				//return ResponseEntity.badRequest().build();
				//return ResponseEntity.badRequest().body("El sku "+ sku+ " no es válido, debe estar conformado por 6 dígitos");
				//
				res.put("error","El sku '"+ sku+ "' no es válido, debe estar conformado por 6 dígitos" );
				return ResponseEntity.badRequest().body(res);
			}
			
			Optional<Producto> optProductos=productoService.findBySKU(sku);
			if (optProductos.isEmpty()) {
				return ResponseEntity.noContent().build();
			}else {
				return ResponseEntity.ok(optProductos.get());
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			res.put("error","Ha ocurrido un error interno" );
			//res.put("error",e.getMessage() );
			//return ResponseEntity.internalServerError().build();
			return ResponseEntity.internalServerError().body(res);
		}
	}
	
	@GetMapping("/by-name")
	public ResponseEntity<?> findByNombre(@RequestParam(value =  "nombre", required = true, defaultValue = "") String nombre){
		Map<String, String> res= new HashMap<>();
			
		if (isNull(nombre) || nombre.trim().length()<3) {
			res.put("error","El nombre '"+ nombre+ "' no es válido, debe ingresar como mínimo 3 caracteres" );
			return ResponseEntity.badRequest().body(res);
		}
		try {
			List<Producto> productos= productoService.findByNombre(nombre);
			if (productos.isEmpty()) {
				return ResponseEntity.noContent().build();
			}else {
				return ResponseEntity.ok(productos);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			res.put("error","Ha ocurrido un error interno" );
			return ResponseEntity.internalServerError().body(res);
			//return ResponseEntity.internalServerError().build();
		}
	
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid Producto producto, BindingResult bindingResult){
		
		Map<String, String> res= new HashMap<>();
		/*
		res= validarProducto(producto);
		if (!res.isEmpty()) {
			return ResponseEntity.badRequest().body(res);	
		}*/
		
		if (bindingResult.hasErrors()) {
			
			//bindingResult.getAllErrors().forEach( ob->{ System.out.println(ob.getDefaultMessage());});
			//bindingResult.getFieldErrors().forEach( fe->{ System.out.println(fe.getField() + " "+fe.getDefaultMessage());});
			
			return ResponseEntity.badRequest().body(this.getCustomsErrors(bindingResult.getFieldErrors()));
			
			//.stream().forEach(e->System.out.println(e));
		}
		
		try {
			Producto retProducto= productoService.save(producto);
			if (isNull(retProducto)) {
				return ResponseEntity.badRequest().build();
			}else {
				return ResponseEntity.status(HttpStatus.CREATED).body(retProducto);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			res.put("error","Ha ocurrido un error interno" );
			return ResponseEntity.internalServerError().body(res);
		}
		
	}

	@PutMapping("/{id}")
	public Object update(@PathVariable Long id,@RequestBody Producto producto){
		producto.setId(id);
		return productoService.update(producto);
	}
	
	@PatchMapping("/{id}")
	public Object updatePrecio(@PathVariable Long id,@RequestParam Double precio){
		return productoService.updatePrecio(id, precio);
	}
	
	@DeleteMapping("/{id}")
	public Object delete(@PathVariable Long id){
		return productoService.delete(id);
	}
	
	private Boolean validarSku(String sku) {
		//if (sku==null) {
		if (isNull(sku)) {
			return false;
		}
		if (sku.trim().length()!=6) {
			return false;
		}
		for (int i = 0; i < sku.length(); i++) { // Expresiones regulares
			 if (!Character.isDigit(sku.charAt(i))) {
				 return false;
			 };			
		}
		return true;
	}
	
	
	private Map<String, String> validarProducto(Producto producto) {
		
		Map<String, String> res= new HashMap<>();
		if (isNull(producto)) {
			res.put("error", "El producto no puede ser nulo");
		}
		String  nombre=producto.getNombre();
		if (isNull(nombre) || nombre.trim().length()<3) {
			res.put("error.nombre", "El nombre es requerido y debe tener como minimo 3 caracteres");
		}
		String  sku=producto.getSku();
		if (isNull(sku) || !validarSku(sku)) {
			res.put("error.sku", "El sku es requerido y debe tener 6 digitos");
		}
		//---
		return res;
	}
	
	
	private Map<String, String> getCustomsErrors(List<FieldError> fieldErrors) {
		
		Map<String, String> errors= new HashMap<>();
		
		for (FieldError fieldError : fieldErrors) {
			errors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return errors;
	}
}
