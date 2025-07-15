package pe.edu.galaxy.training.java.ms.negocio.gestion.clientes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import pe.edu.galaxy.training.java.ms.negocio.gestion.clientes.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	
	
	@Query(value = "select p from Cliente p where p.estado='1'") // JPQL
	List<Cliente> findAllCustom();
	
	@Query(value = "select p from Cliente p where upper(p.razonSocial) like upper(:razonSocial) and p.estado='1'") // JPQL
	List<Cliente> findByRazonSocial(@Param("razonSocial") String razonSocial);
	
	
	// SQL Native
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "update tbl_clientes set estado='0' where producto_id=:id") // SQL
	void delete(@Param("id") Long id);

}
