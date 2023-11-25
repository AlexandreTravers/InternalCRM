package fr.m2.archi.et.client;

import fr.m2.archi.et.model.thrift.InternalCRMService;
import fr.m2.archi.et.model.thrift.InternalLeadDto;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CRMThriftClient {

    public List<InternalLeadDto> findLeads(double lowAnnualRevenue, double highAnnualRevenue, String state) {
        try {
            TTransport transport = new TSocket("localhost", 9090);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            InternalCRMService.Client client = new InternalCRMService.Client(protocol);

            List<InternalLeadDto> leads = client.findLeads(lowAnnualRevenue, highAnnualRevenue, state);

            transport.close();
            return leads;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
