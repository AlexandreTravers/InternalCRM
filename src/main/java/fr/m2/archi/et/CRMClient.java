package fr.m2.archi.et;

import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import fr.m2.archi.et.model.thrift.InternalCRMService;
import fr.m2.archi.et.model.thrift.InternalLeadDto;
import fr.m2.archi.et.model.thrift.ModelITO;

/*
 * Classe pour tester le CRM.
 * Pour tester :
 * 	- Allumer le serveur SpringBoost, qui allumera également un serveur CRM.
 *  - Lancer le main de cette classe en tant qu'application java normal
 *  
 *  Penser à delete au rendu final /!\
 */

public class CRMClient {

    public static void main(String[] args) {
        try {
            TTransport transport = new TSocket("localhost", 9090);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            InternalCRMService.Client client = new InternalCRMService.Client(protocol);

            double lowAnnualRevenue = 0.0;
            double highAnnualRevenue = 1000000.0;
            String state = "New York";

            List<InternalLeadDto> leads = client.findLeads(lowAnnualRevenue, highAnnualRevenue, state);
            
            System.out.println("Résultats : ");

            for (InternalLeadDto lead : leads) {
                ModelITO leadInfo = lead.getInformations();
                System.out.println("Lead: " + leadInfo.getFirstName() + " " + leadInfo.getLastName());
            }

            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
