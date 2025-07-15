package pe.edu.galaxy.training.java.ws.api_gestion_productos_v2.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "tbl_productos")
@Entity(name = "Producto")
public class Producto {

	@Id
	@Column(name = "producto_id",nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	private Long id;
	
	@NotEmpty(message = "El nombre es requerido")
	@Size(min = 3, max =220,  message ="El nombre debe tener como minimo {min} y maximo {max} caracteres")
	@Column(name = "nombre", nullable = false, unique = true, length = 220)
	private String nombre;
	
	//@Sku
	@NotEmpty(message = "El sku es requerido")
	@Size(min = 6, max =6,  message ="El sku debe tener {min} dígitos") // Regex ( Expresiones regulares)
	@Column(name = "sku", nullable = false, unique = true)
	private String sku;
	
	@NotNull(message = "El precio es requerido")
	@Positive(message ="El precio debe ser mayor que cero")	
	@Column(name = "precio", nullable = false)
	private Double precio;
	
	//private String marca;
	
	@Column(name = "fecha_registro", nullable = false)
	private LocalDate fechaRegistro;
	
	//@Getter
	//@Setter
	@Column(name = "estado")
	private String estado;
	
	
	public String getNombre() {
		if (nombre==null) {
			nombre="";
		}
		return nombre.toUpperCase();
	}
	
}
