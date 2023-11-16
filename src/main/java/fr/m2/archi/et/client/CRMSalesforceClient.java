package fr.m2.archi.et.client;

import java.util.ArrayList;
import java.util.List;

import fr.m2.archi.et.model.salesforce.SalesforceLeadDto;
import fr.m2.archi.et.model.salesforce.SalesforceUserService;

public class CRMSalesforceClient {

	public List<SalesforceLeadDto> findLeads(double lowAnnualRevenue, double highAnnualRevenue, String state) {
		List<SalesforceLeadDto> tmp = new ArrayList<SalesforceLeadDto>();
		return tmp;
	}
	
	public List<SalesforceLeadDto> getUsers() {
		List<SalesforceLeadDto> infos = SalesforceUserService.getInstance().getUsersInformations();
		return infos;
	}

}
