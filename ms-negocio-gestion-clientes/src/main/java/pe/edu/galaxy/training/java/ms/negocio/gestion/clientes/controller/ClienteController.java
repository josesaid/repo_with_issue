package pe.edu.galaxy.training.java.ms.negocio.gestion.clientes.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.galaxy.training.java.ms.negocio.gestion.clientes.entity.Cliente;
import pe.edu.galaxy.training.java.ms.negocio.gestion.clientes.service.ClienteService;

import static java.util.Objects.isNull;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/clientes")
public class ClienteController {
	
	private final ClienteService clienteService;
	
	@GetMapping
	public ResponseEntity<List<Cliente>> findAll(){
	
		try {
			List<Cliente> clientes= clienteService.findAll();
			if (clientes.isEmpty()) {
				return ResponseEntity.noContent().build();
			}else {
				return ResponseEntity.ok(clientes);
			}
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> findById(@PathVariable("id") Long id){
		try {
			if (id<=0) {
				return ResponseEntity.badRequest().build();
			}
			
			Optional<Cliente> optClientes= clienteService.findById(id);
			if (optClientes.isEmpty()) {
				return ResponseEntity.noContent().build();
			}else {
				return ResponseEntity.ok(optClientes.get());
			}
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
		
	}
	
	
	@GetMapping("/by-razon-social")
	public ResponseEntity<?> findByNombre(@RequestParam(value =  "razon-social", required = true, defaultValue = "") String razonSocial){
		Map<String, String> res= new HashMap<>();
			
		if (isNull(razonSocial) || razonSocial.trim().length()<5) {
			res.put("error","La razon-social '"+ razonSocial+ "' no es válida, debe ingresar como mínimo 5 caracteres" );
			return ResponseEntity.badRequest().body(res);
		}
		try {
			List<Cliente> clientes= clienteService.findByRazonSocial(razonSocial);
			if (clientes.isEmpty()) {
				return ResponseEntity.noContent().build();
			}else {
				return ResponseEntity.ok(clientes);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			res.put("error","Ha ocurrido un error interno" );
			return ResponseEntity.internalServerError().body(res);
		}
	
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid Cliente cliente, BindingResult bindingResult){
		
		Map<String, String> res= new HashMap<>();
		
		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body(this.getCustomsErrors(bindingResult.getFieldErrors()));
		}
		
		try {
			Cliente retCliente= clienteService.save(cliente);
			if (isNull(retCliente)) {
				return ResponseEntity.badRequest().build();
			}else {
				return ResponseEntity.status(HttpStatus.CREATED).body(retCliente);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			res.put("error","Ha ocurrido un error interno" );
			return ResponseEntity.internalServerError().body(res);
		}
		
	}

	@PutMapping("/{id}")
	public Object update(@PathVariable Long id,@RequestBody Cliente cliente){
		cliente.setId(id);
		return clienteService.update(cliente);
	}
	
	
	@DeleteMapping("/{id}")
	public Object delete(@PathVariable Long id){
		return clienteService.delete(id);
	}
	

	private Map<String, String> getCustomsErrors(List<FieldError> fieldErrors) {
		
		Map<String, String> errors= new HashMap<>();
		
		for (FieldError fieldError : fieldErrors) {
			errors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return errors;
	}
}
