package pe.edu.galaxy.training.java.ms.negocio.gestion.clientes.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import pe.edu.galaxy.training.java.ms.negocio.gestion.clientes.entity.Cliente;
import pe.edu.galaxy.training.java.ms.negocio.gestion.clientes.repository.ClienteRepository;

@RequiredArgsConstructor
@Service
public class ClienteServiceImpl implements ClienteService {

	private final ClienteRepository clienteRepository;

	@Override
	public List<Cliente> findAll() {
		return clienteRepository.findAllCustom();
	}

	@Override
	public Optional<Cliente> findById(Long id) {
		return clienteRepository.findById(id);
	}


	@Override
	public Cliente save(Cliente cliente) {
		cliente.setEstado("1");
		return clienteRepository.save(cliente);
	}

	@Override
	public Cliente update(Cliente cliente) {
		
		Optional<Cliente> optCliente = clienteRepository.findById(cliente.getId());

		if (optCliente.isEmpty()) {
			cliente.setId(null);
			cliente.setEstado("1");
			return clienteRepository.save(cliente);
		} else {
			Cliente obtCliente = optCliente.get();
			
			obtCliente.setRazonSocial(cliente.getRazonSocial());
			obtCliente.setRuc(cliente.getRuc());
			
			return clienteRepository.save(obtCliente);
		}

	}


	@Override
	public Boolean delete(Long id) {
		clienteRepository.delete(id);
		return true;
	}

	@Override
	public List<Cliente> findByRazonSocial(String razonSocial) {
		return clienteRepository.findByRazonSocial("%"+razonSocial+"%");
	}

}
