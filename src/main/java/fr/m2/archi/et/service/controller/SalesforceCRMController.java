package fr.m2.archi.et.service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.m2.archi.et.client.CRMSalesforceClient;
import fr.m2.archi.et.model.salesforce.SalesforceLeadDto;

@RestController
public class SalesforceCRMController {

	CRMSalesforceClient crmClient = new CRMSalesforceClient();
	
	@PostMapping("/salesforceLeads")
    public List<SalesforceLeadDto> getLeads(
    		@RequestBody JsonRequestForLeads request) {
    	return crmClient.findLeads(request.getLowAnnualRevenue(), request.getHighAnnualRevenue(), request.getState());
    }
	
	@GetMapping("/allUsers")
    public List<SalesforceLeadDto> getUsers() {
    	return crmClient.getUsers();
    }
}
