package fr.m2.archi.et.client;

import fr.m2.archi.et.model.handler.InternalCRMServiceImpl;
import fr.m2.archi.et.model.thrift.InternalLeadDto;
import fr.m2.archi.et.model.thrift.ModelITO;

import org.apache.thrift.TException;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CRMThriftClient {

    public List<InternalLeadDto> findLeads(double lowAnnualRevenue, double highAnnualRevenue, String state) throws TException {	
    	return InternalCRMServiceImpl.getInstance().findLeads(lowAnnualRevenue, highAnnualRevenue, state);
    }
    
    public List<InternalLeadDto> findLeads(String startDate, String endDate) throws TException {
    	return InternalCRMServiceImpl.getInstance().findLeadsByDate(startDate, endDate);
    }
    
    public List<InternalLeadDto> getUsers() throws TException {
    	return InternalCRMServiceImpl.getInstance().getUsers();
    }
    
    public void deleteUser(String phoneNumber) throws TException {
    	InternalCRMServiceImpl.getInstance().deleteLead(phoneNumber);
    }
    
    public void addUser(String name, double annualRevenue, String phone, String street, String postalCode, String city, String country, String company, String state) throws TException {
    	String creationDate = java.time.LocalDate.now().toString();
    	InternalCRMServiceImpl.getInstance().addLead(new InternalLeadDto(new ModelITO(name, annualRevenue, phone, street, postalCode, city, country, creationDate, company, state)));
    }
}
