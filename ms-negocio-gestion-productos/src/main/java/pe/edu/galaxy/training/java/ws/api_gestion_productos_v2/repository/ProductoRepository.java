package pe.edu.galaxy.training.java.ws.api_gestion_productos_v2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import pe.edu.galaxy.training.java.ws.api_gestion_productos_v2.entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

	
	//Optional<Producto> findBySku(String sku);

	//Optional<Producto> findByNombreLike(String nombre);
	
	@Query(value = "select p from Producto p where p.sku=:sku and p.estado='1'") // JPQL
	Optional<Producto> findBySku(@Param("sku")  String sku);
	
	@Query(value = "select p from Producto p where p.estado='1'") // JPQL
	List<Producto> findAllCustom();
	
	@Query(value = "select p from Producto p where upper(p.nombre) like upper(:nombre) and p.estado='1'") // JPQL
	List<Producto> findByNombre(@Param("nombre") String nombre);
	
	//List<Producto> findByNombreLikeIgnoreCase(String string);
	
	// SQL Native	
	@Modifying
	@Query(nativeQuery = true, value = "update tbl_productos set precio=:precio where producto_id=:id") // SQL
	void updatePrecio(@Param("id") Long id,@Param("precio") Double precio);
	
	// SQL Native
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "update tbl_productos set estado='0' where producto_id=:id") // SQL
	void delete(@Param("id") Long id);

}
