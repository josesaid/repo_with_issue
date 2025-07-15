package pe.edu.galaxy.training.java.ms.negocio.gestion.clientes.service;

import java.util.List;
import java.util.Optional;

import pe.edu.galaxy.training.java.ms.negocio.gestion.clientes.entity.Cliente;

public interface ClienteService {
	
	Optional<Cliente> findById(Long id);
	
	List<Cliente> findAll();

	List<Cliente> findByRazonSocial(String razonSocial);
	
	Cliente save(Cliente cliente);
	
	Cliente update(Cliente cliente);	
	
	Boolean delete(Long id);
}
