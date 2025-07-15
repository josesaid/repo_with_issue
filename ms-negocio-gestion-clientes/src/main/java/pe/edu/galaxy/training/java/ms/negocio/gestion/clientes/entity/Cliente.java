package pe.edu.galaxy.training.java.ms.negocio.gestion.clientes.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "tbl_clientes")
@Entity(name = "Cliente")
public class Cliente {

	@Id
	@Column(name = "cliente_id",nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	private Long id;
	
	@NotEmpty(message = "La razón social es requerida")
	@Size(min = 5, max =220,  message ="La razón social debe tener como minimo {min} y maximo {max} caracteres")
	@Column(name = "razon_social", nullable = false, unique = true, length = 220)
	private String razonSocial;
	
	//@Sku
	@NotEmpty(message = "El ruc es requerido")
	@Size(min = 11, max =11,  message ="El sku debe tener {min} dígitos exactamente")
	@Column(name = "ruc", nullable = false, unique = true)
	private String ruc;
	
	@Column(name = "estado")
	private String estado;
	
	
}
