package pe.edu.galaxy.galaxy.java.arq.ms.ms_infraestructure_server_registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class MsInfraestructureServerRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsInfraestructureServerRegistryApplication.class, args);
	}

}
