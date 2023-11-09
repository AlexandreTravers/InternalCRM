package fr.m2.archi.et.thrift;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fr.m2.archi.et.model.handler.InternalCRMServiceImpl;
import fr.m2.archi.et.model.thrift.InternalCRMService;

@Configuration
public class ThriftServerConfig {

	@Bean
    TServer thriftServer(InternalCRMServiceImpl handler) {
        try {
            TServerSocket serverTransport = new TServerSocket(9090);
            TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(
                    new InternalCRMService.Processor<>(handler)));

            System.out.println("Starting the CRM server...");
            new Thread(server::serve).start(); // Démarrer dans un thread séparé

            return server;
        } catch (Exception e) {
            throw new IllegalStateException("Unable to start Thrift server", e);
        }
    }
}
