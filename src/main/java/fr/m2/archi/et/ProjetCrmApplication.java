package fr.m2.archi.et;

import org.apache.thrift.server.TServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjetCrmApplication {

	@Autowired
    private TServer thriftServer;
	
	public static void main(String[] args) {
		SpringApplication.run(ProjetCrmApplication.class, args);
	}
}


