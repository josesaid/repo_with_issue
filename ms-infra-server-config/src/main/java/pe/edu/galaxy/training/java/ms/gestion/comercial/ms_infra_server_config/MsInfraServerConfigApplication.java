package pe.edu.galaxy.training.java.ms.gestion.comercial.ms_infra_server_config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class MsInfraServerConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsInfraServerConfigApplication.class, args);
	}

}
