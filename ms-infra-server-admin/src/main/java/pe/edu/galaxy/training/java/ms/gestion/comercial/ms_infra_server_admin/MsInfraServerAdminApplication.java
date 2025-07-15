package pe.edu.galaxy.training.java.ms.gestion.comercial.ms_infra_server_admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@EnableAdminServer
@SpringBootApplication
public class MsInfraServerAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsInfraServerAdminApplication.class, args);
	}

}
